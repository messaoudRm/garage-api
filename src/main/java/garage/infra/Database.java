package garage.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {


    private static final String URL = "jdbc:postgresql://postgres:5432/garage";
    private static final String USER = "garage";
    private static final String PASSWORD = "garage";

    private Database() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
