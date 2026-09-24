package garage.infra;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {}

    public static void initialize() throws Exception {
        String sql = Files.readString(Path.of("src/main/java/garage/infra/schema.sql"));

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}

