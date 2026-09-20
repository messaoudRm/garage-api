import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Le proxy de make dev : relaie le navigateur vers l'application de l'élève et
 * recharge la page à chaque passe de compilation.
 *
 * java infra/ReloadProxy.java <port public> <port de l'application> <fichier version> <fichier erreurs>
 */
public class ReloadProxy {

    // Ces en-têtes décrivent la connexion, pas le contenu, et HttpClient refuse d'en poser certains.
    // accept-encoding : on veut un corps non compressé pour pouvoir y injecter le script.
    private static final Set<String> IGNORED_HEADERS = Set.of(
            "connection", "content-length", "expect", "host", "upgrade", "keep-alive",
            "transfer-encoding", "te", "trailer", "proxy-connection", "accept-encoding");

    private static final String RELOAD_SCRIPT = "<script>(() => { let v = null; setInterval(async () => { try { "
            + "const n = await (await fetch('/__reload')).text(); "
            + "if (v !== null && n !== v) location.reload(); v = n; } catch (e) {} }, 500); })();</script>";

    private static final Pattern BODY_END = Pattern.compile("(?i)</body>");

    public static void main(String[] args) throws IOException {
        int publicPort = Integer.parseInt(args[0]);
        int appPort = Integer.parseInt(args[1]);
        Path versionFile = Path.of(args[2]);
        Path errorsFile = Path.of(args[3]);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(2))
                .build();

        HttpServer server = HttpServer.create(new InetSocketAddress(publicPort), 0);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.createContext("/__reload", exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            exchange.getResponseHeaders().set("Cache-Control", "no-store");
            send(exchange, 200, read(versionFile).getBytes(StandardCharsets.UTF_8));
        });
        server.createContext("/", exchange -> relay(exchange, client, appPort, errorsFile));
        server.start();
    }

    private static void relay(HttpExchange exchange, HttpClient client, int appPort, Path errorsFile)
            throws IOException {
        String compileErrors = read(errorsFile);
        if (!compileErrors.isBlank()) {
            sendPage(exchange, 500, "Le code ne compile pas", compileErrors);
            return;
        }
        HttpResponse<byte[]> response;
        try {
            response = client.send(forward(exchange, appPort), HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException e) {
            sendPage(exchange, 503, "Aucune application web",
                    "Rien ne répond sur le port " + appPort + ".\n"
                            + "Si ton programme est un serveur web, il doit écouter sur ce port.\n"
                            + "Sinon, c'est normal : sa sortie s'affiche dans le terminal de make dev.");
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        response.headers().map().forEach((name, values) -> {
            if (!IGNORED_HEADERS.contains(name.toLowerCase(Locale.ROOT))) {
                exchange.getResponseHeaders().put(name, values);
            }
        });
        byte[] body = response.body();
        if (isHtml(response, body)) {
            body = injectReloadScript(new String(body, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        }
        send(exchange, response.statusCode(), body);
    }

    private static HttpRequest forward(HttpExchange exchange, int appPort) throws IOException {
        byte[] body = exchange.getRequestBody().readAllBytes();
        HttpRequest.Builder request = HttpRequest
                .newBuilder(URI.create("http://127.0.0.1:" + appPort + exchange.getRequestURI()))
                .method(exchange.getRequestMethod(), body.length == 0
                        ? HttpRequest.BodyPublishers.noBody()
                        : HttpRequest.BodyPublishers.ofByteArray(body));
        exchange.getRequestHeaders().forEach((name, values) -> {
            if (!IGNORED_HEADERS.contains(name.toLowerCase(Locale.ROOT))) {
                values.forEach(value -> request.header(name, value));
            }
        });
        return request.build();
    }

    // Un HttpServer sans Content-Type est lu comme du HTML par le navigateur : on fait pareil.
    private static boolean isHtml(HttpResponse<byte[]> response, byte[] body) {
        return response.headers().firstValue("Content-Type")
                .map(type -> type.toLowerCase(Locale.ROOT).startsWith("text/html"))
                .orElseGet(() -> new String(body, StandardCharsets.UTF_8).stripLeading().startsWith("<"));
    }

    private static String injectReloadScript(String html) {
        Matcher end = BODY_END.matcher(html);
        int position = -1;
        while (end.find()) {
            position = end.start();
        }
        return position < 0
                ? html + RELOAD_SCRIPT
                : html.substring(0, position) + RELOAD_SCRIPT + html.substring(position);
    }

    private static void sendPage(HttpExchange exchange, int status, String title, String detail)
            throws IOException {
        String html = """
                <!doctype html>
                <html lang="fr"><head><meta charset="utf-8"><title>%1$s</title>
                <style>:root{color-scheme:light}
                body{font:16px system-ui,sans-serif;margin:0;padding:3rem;background:#fafafa;color:#1a1a1a}
                h1{font-size:1.4rem;color:#b3261e}
                pre{background:#fff;border:1px solid #ddd;border-radius:6px;padding:1rem;white-space:pre-wrap}
                p{color:#555}</style>
                </head><body><h1>%1$s</h1><pre>%2$s</pre>
                <p>La page se recharge toute seule à la prochaine sauvegarde.</p>%3$s</body></html>
                """.formatted(escape(title), escape(detail), RELOAD_SCRIPT);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        send(exchange, status, html.getBytes(StandardCharsets.UTF_8));
    }

    private static void send(HttpExchange exchange, int status, byte[] body) throws IOException {
        boolean noBody = body.length == 0 || status == 204 || status == 304
                || exchange.getRequestMethod().equalsIgnoreCase("HEAD");
        exchange.sendResponseHeaders(status, noBody ? -1 : body.length);
        if (!noBody) {
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        }
        exchange.close();
    }

    private static String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String read(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            return "";
        }
    }
}
