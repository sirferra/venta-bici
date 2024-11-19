/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import repositorio.Conexion;

/**
 *
 * @author facundo.cuffia
 */
public class Cliente extends Persona{
    //Atributos
    private String cuil;
    
    //Constructores
    public Cliente() {
        super();
    }
    
    public Cliente(String cuil) {
        this.cuil = cuil;
    }

    public Cliente(String cuil, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuil = cuil;
    }

    public Cliente(String cuil, int id, String nombre, String apellido, int dni, String telefono, String email) {
        super(id, nombre, apellido, dni, telefono, email);
        this.cuil = cuil;
    }
    

    //Getters y Setters
    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }
    
    //Buscar todos los clientes
    public static List<Cliente> getAll(){
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Cliente");
            return Cliente.fromResultSet(resultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Buscar clientes por filtros
    public static List<Cliente> buscarPorFiltros(String dni, String nombre, String apellido){
        try {
            String sqlFiltro = "SELECT * FROM Cliente WHERE 1 = 1";
            if (dni != null && !dni.isEmpty()) {
                sqlFiltro = sqlFiltro + " AND dni = '" + dni + "'";
            }
            if (nombre != null && !nombre.isEmpty()) {
                sqlFiltro = sqlFiltro + " AND nombre = '" + nombre + "'"; 
            }
            if (apellido != null && !apellido.isEmpty()) {
                sqlFiltro = sqlFiltro + " AND apellido = '" + apellido + "'";
            }
            ResultSet resultados = Conexion.getInstance().executeQuery(sqlFiltro);
            return Cliente.fromResultSet(resultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //Función para crear un cliente
    public boolean crearCliente() {
    try {
        // Crear un HashMap para los parámetros
        HashMap<Integer, Object> params = new HashMap<>();

        // Verificar si ya existe un cliente con el mismo DNI
        String sqlVerificar = "SELECT COUNT(*) AS total FROM Cliente WHERE dni = ?";
        params.put(1, getDni()); // Usar el DNI para la verificación

        // Ejecutar la consulta de verificación
        ResultSet rs = Conexion.getInstance().executeQueryWithParams(sqlVerificar, params);
        if (rs.next() && rs.getInt("total") > 0) {
            System.out.println("El cliente con DNI " + getDni() + " ya existe.");
            return false; // Cliente ya existe, no continuar
        }

        // Reutilizar el HashMap para la inserción
        params.clear(); // Limpiar el HashMap
        String insertQuery = "INSERT INTO Cliente(cuil, nombre, apellido, dni, telefono, email) VALUES(?, ?, ?, ?, ?, ?)";
        params.put(1, getCuil());
        params.put(2, getNombre());
        params.put(3, getApellido());
        params.put(4, getDni());
        params.put(5, getTelefono());
        params.put(6, getEmail());

        // Ejecutar la consulta de inserción
        Conexion.getInstance().executeQueryWithParams(insertQuery, params);
        return true; // Operación exitosa
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Error durante la operación
    }


    public boolean modificarCliente(){
        try {
            int id = buscarPorDni(getDni()).getId();
            if(id!=0){
                throw new Error("El id del cliente no existe");
            }
            String query = "UPDATE Cliente SET cuil = ?, nombre = ?, apellido = ?, dni = ?, telefono = ?, email = ? WHERE id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuil());
            params.put(2, getNombre());
            params.put(3, getApellido());
            params.put(4, getDni());
            params.put(5, getTelefono());
            params.put(6, getEmail());
            params.put(7, id);

            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Función para eliminar un cliente
    public boolean eliminarCliente(){
        try {
            //Ejecuto la funcion de busqueda auxiliar y guardo el resultado en id (clave principal de la tabla)
            int id = buscarPorDni(getDni()).getId();
            if(id==0){
                throw new Error("El id del cliente no existe");
            }
            String query = "DELETE FROM Cliente WHERE id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, id);
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Buscar por dni auxiliar
    public static Cliente buscarPorDni(int dni) {
        try {
            // Realizamos la consulta para obtener el cliente por DNI 
            String query = "SELECT * FROM Cliente WHERE dni = '" + dni + "'";

            // Ejecutamos la consulta y devolvemos el resultado
            ResultSet resultados = Conexion.getInstance().executeQuery(query);

            // Devolvemos el cliente encontrado (si existe)
            return Cliente.fromResultSet(resultados).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /*public static Cliente buscarPorDni(int dni){
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Cliente WHERE dni = '" + dni + "'");
            return Cliente.fromResultSet(resultados).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
    
    //Convertir los resultados en una lista de objetos
    public static List<Cliente> fromResultSet(ResultSet resultados) {
        try {
            List<Cliente> clientes = new java.util.ArrayList<>();
            while (resultados.next()) {
                String cuil = resultados.getString("cuil");
                String nombre = resultados.getString("nombre");
                String apellido = resultados.getString("apellido");
                int dni = resultados.getInt("dni");
                String telefono = resultados.getString("telefono");
                String email = resultados.getString("email");
                clientes.add(new Cliente(cuil, nombre, apellido, dni, telefono, email));
            }
            return clientes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "Cliente{" + "cuil=" + cuil + ", nombre=" + getNombre() + ", apellido=" + getApellido() + ", dni=" + getDni() + ", telefono=" + getTelefono() + ", email=" + getEmail() + '}';
    }

    public Object[] tObject() {
        return new Object[]{cuil, getNombre(), getApellido(), getDni(), getTelefono(), getEmail()};
    }
}
