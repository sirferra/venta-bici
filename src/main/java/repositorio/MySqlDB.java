package repositorio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class MySqlDB {
    private Connection connection;
    private final String DATABASE = "db_bicicleteria";

    public MySqlDB(Connection connection) {
        this.connection = connection;
        try {
            // revisar si la base de datos existe
            revisarDB();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            connection.setCatalog(DATABASE);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void revisarDB() throws SQLException {
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet rs = metaData.getCatalogs();
        boolean databaseExists = false;
        while (rs.next()) {
            String databaseName = rs.getString("TABLE_CAT");
            if (databaseName.equals("DATABASE")) {
                databaseExists = true;
                break;
            }
        }
        if(!databaseExists){
            try {
                System.out.println("Creando Base De datos y tablas...");
                Statement stmt = connection.createStatement();
                stmt.execute("CREATE DATABASE IF NOT EXISTS "+DATABASE);
            } catch (SQLException e) {
                System.err.println("Error al crear base de datos y tablas: " + e.getMessage());
            } 
        }
        revisarSiExistenTablas();
    }
    private void revisarSiExistenTablas() throws SQLException {
         try{
            Statement stmt = connection.createStatement();
            stmt.execute("USE " + DATABASE);
            String[] sqlCommands = Files.readString(Paths.get("src/main/resources/dump.sql")).split(";");
            for (String command : sqlCommands) {
                try{
                    stmt.execute(command);
                }catch(SQLException e){
                    continue;
                }
            }
        }catch (IOException e) {
            System.err.println("Error al leer archivo SQL: " + e.getMessage());
        }
    }

    public void migrarDB() throws SQLException {
        try{
            Statement stmt = connection.createStatement();
            stmt.execute("USE " + DATABASE);
            String[] sqlCommands = Files.readString(Paths.get("src/main/resources/migration.sql")).split(";");
            for (String command : sqlCommands) {
                try{
                    stmt.execute(command);
                }catch(SQLException e){
                    continue;
                }
            }
        }catch (IOException e) {
            System.err.println("Error al leer archivo SQL: " + e.getMessage());
        }
    }

   public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }
}
