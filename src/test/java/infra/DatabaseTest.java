package infra;

import garage.infra.Database;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DatabaseTest {

    @Test
    void connexion_postgresql() throws Exception {
        try (Connection connection = Database.getConnection()) {
            assertThat(connection.isValid(2)).isTrue();
        }
    }

}
