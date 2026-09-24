package garage.adapters.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import garage.app.stock.ConsommerPieceService;

public class ConsommerPieceHandler implements HttpHandler {
  
  private final ConsommerPieceService service;

  public ConsommerPieceHandler(ConsommerPieceService s) {
    service = s;
  }

  @Override
  public void handle(HttpExchange ex) throws IOException {
    try {
      service.executer(0);
      response(ex, 200, "{\"status\": \"ok\"}");
      
    } catch (Exception e) { 
      response(ex, 400, "{\"erreur\":\"" + e.getMessage() + "\"}");
    }
  }
  
  private static void response(HttpExchange ex, int statut, String json) throws IOException {
    byte[] corps = json.getBytes(StandardCharsets.UTF_8);
    ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
    ex.sendResponseHeaders(statut, corps.length);
    try (OutputStream os = ex.getResponseBody()) {
      os.write(corps);
    }
  }
}
