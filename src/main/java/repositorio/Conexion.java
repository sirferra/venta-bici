package repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String CONNECTION_STRING =  "jdbc:mysql://root@localhost:3004";
    private static final Connection connection;
    private static final MySqlDB db;

    private Conexion(){}

    static{
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            db = new MySqlDB(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static MySqlDB getInstance(){
        return db;
    }
}
