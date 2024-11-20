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
 * @author facundo.cuffia / P4rzival
 */
public class Vendedor extends Persona {
    //Atributos
    private String cuit;
    private String sucursal;
    
    //Constructores
    public Vendedor() {
        super();
    }
    
    public Vendedor(String cuit, String sucursal) {
        this.cuit = cuit;
        this.sucursal = sucursal;
    }

    public Vendedor(String cuit, String sucursal, int id, String nombre, String apellido, int dni, String telefono, String email) {
        super(id, nombre, apellido, dni, telefono, email);
        this.cuit = cuit;
        this.sucursal = sucursal;
    }
    
    
    //Getters and Setters
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    
    /**
     * Metodos estaticos CRUD
     * Verificar en consultas los nombres de las tablas
     * @return 
     **/
    
    
    // Obtiene todos los vendedores y lo meto a la lista
    public static List<Vendedor> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Vendedor");
            return Vendedor.fromResultSet(resultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Filtrado por cuit
    public static List<Vendedor> buscarPorFiltros(String dni, String nombre, String apellido) {
        try {
            String sqlFiltro = "SELECT * FROM Vendedor WHERE 1 = 1";
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
            return Vendedor.fromResultSet(resultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Busca por nombre, apellido o ambos
    public static List<Vendedor> buscarPorNombreApellido(String nombre, String apellido) {
    try {
        // Consulta base
        String query = "SELECT * FROM Vendedor WHERE 1=1";
        HashMap<Integer, Object> params = new HashMap<>();
        int index = 1;
        // Si el nombre esta vacio o nulo
        if (nombre != null && !nombre.isEmpty()) {
            query += " AND nombre LIKE ?";
            params.put(index++, "%" + nombre + "%");
        }
        // Si el apellido esta vacio o nulo
        if (apellido != null && !apellido.isEmpty()) {
            query += " AND apellido LIKE ?";
            params.put(index++, "%" + apellido + "%");
        }
        // Arma la consulta con la base y las condiciones
        ResultSet resultados = Conexion.getInstance().executeQueryWithParams(query, params);
        return Vendedor.fromResultSet(resultados);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
    // Crea un vendedor usando el objeto actual
    public boolean crearVendedor() {
        try {
            String query = "INSERT INTO Vendedor(cuit, sucursal, nombre, apellido, dni, telefono, email) VALUES(?, ?, ?, ?, ?, ?, ?)";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuit());
            params.put(2, getSucursal());
            params.put(3, getNombre());
            params.put(4, getApellido());
            params.put(5, getDni());
            params.put(6, getTelefono());
            params.put(7, getEmail());

            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //  Filtra el vendedor por dni y trae los datos correspondientes al mismo para reasignarle los valores (podria ser cuil o codigo ?) 
    public boolean modificarVendedor() {
        try {
            Vendedor vendedorExistente = buscarPorDni(getDni());
            if (vendedorExistente == null) {
                throw new Exception("El vendedor no existe");
            }
            String query = "UPDATE Vendedor SET cuit = ?, sucursal = ?, nombre = ?, apellido = ?, telefono = ?, email = ? WHERE dni = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuit());
            params.put(2, getSucursal());
            params.put(3, getNombre());
            params.put(4, getApellido());
            params.put(5, getTelefono());
            params.put(6, getEmail());
            params.put(7, getDni());

            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Trae el vendedor por dni ( Como el anterior podria ser por cuit?) y formula el DELETE
    public boolean eliminarVendedor() {
        try {
            //Ejecuto la funcion de busqueda auxiliar y guardo el resultado en id (clave principal de la tabla)
            int id = buscarPorDni(getDni()).getId();
            if (id == 0){
                throw new Exception("El vendedor no existe");
            }
            String query = "DELETE FROM Vendedor WHERE dni = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getDni());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public boolean eliminarVendedorPorCuit() {
        try {
            Vendedor vendedorExistente = buscarPorCuit(getCuit());
            if (vendedorExistente == null) {
                throw new Exception("El vendedor no existe");
            }
            String query = "DELETE FROM Vendedor WHERE cuit = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuit());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    //Buscar por dni auxiliar para eliminar
    public static Vendedor buscarPorDni(int dni) {
        try {
            // Realizamos la consulta para obtener el cliente por DNI 
            String query = "SELECT * FROM Vendedor WHERE dni = " + dni;
            // Ejecutamos la consulta y devolvemos el resultado
            ResultSet resultados = Conexion.getInstance().executeQuery(query);
            // Devolvemos el cliente encontrado (si existe)
            return Vendedor.fromResultSet(resultados).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
    
    //Buscar por dni auxiliar para modificar
    public static int buscarPorDni(String dni) {
        try {
            // Realizamos la consulta para obtener el cliente por DNI 
            String query = "SELECT id FROM vendedor WHERE dni = ?";
            // Utilizamos un parámetro para prevenir SQL Injection
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, dni);
            // Ejecutamos la consulta y obtenemos el resultado
            ResultSet resultados = Conexion.getInstance().executeQueryWithParams(query, params);
            // Verificamos si existe algún resultado
            if (resultados.next()) {
                // Retornamos el ID del cliente
                return resultados.getInt("id");
            } else {
                System.out.println("No se encontró un vendedor con el DNI proporcionado.");
                return 0; // O algún valor que indique que no se encontró
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Devuelve 0 en caso de error para manejarlo adecuadamente
        }
    }

    // Convierto el resultado de las consultas en una lista objeto
    public static List<Vendedor> fromResultSet(ResultSet resultados) {
        try {
            List<Vendedor> vendedores = new java.util.ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("id");
                String cuit = resultados.getString("cuit");
                String sucursal = resultados.getString("sucursal");
                String nombre = resultados.getString("nombre");
                String apellido = resultados.getString("apellido");
                int dni = resultados.getInt("dni");
                String telefono = resultados.getString("telefono");
                String email = resultados.getString("email");
                vendedores.add(new Vendedor(cuit, sucursal, id, nombre, apellido, dni, telefono, email));
            }
            return vendedores;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Vendedor{" + "cuit=" + cuit + ", sucursal=" + sucursal + ", nombre=" + getNombre() + ", apellido=" + getApellido() + ", dni=" + getDni() + ", telefono=" + getTelefono() + ", email=" + getEmail() + '}';
    }
    
}
