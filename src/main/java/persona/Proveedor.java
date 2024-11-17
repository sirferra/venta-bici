/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import repositorio.Conexion;

/**
 *
 * @author facundo.cuffia
 */
public class Proveedor extends Persona {
    //Atributos
    private String cuit;
    private String nombreFantasia;
    
    //Constructores
    public Proveedor(){
        super();
    }

    public Proveedor(String cuit, String nombreFantasia) {
        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
    }

    public Proveedor(String cuit, String nombreFantasia, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
    }
    
    //Getters y Setters
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }
    
    public static List<Proveedor> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Proveedor");
            return Proveedor.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Proveedor buscarPorCuit(String cuit) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Proveedor WHERE cuit = '" + cuit + "'");
            List<Proveedor> proveedores = Proveedor.fromResultSet(resultados);
            return proveedores.isEmpty() ? null : proveedores.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Proveedor buscarPorDni(int dni) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Proveedor WHERE dni = '" + dni + "'");
            List<Proveedor> proveedores = Proveedor.fromResultSet(resultados);
            return proveedores.isEmpty() ? null : proveedores.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarProveedor() {
        try {
            String query = "DELETE FROM Proveedor WHERE cuit = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuit());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crearProveedor() {
        try {
            String query = "INSERT INTO Proveedor(cuit, nombreFantasia, nombre, apellido, dni, telefono, email) VALUES(?, ?, ?, ?, ?, ?, ?)";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuit());
            params.put(2, getNombreFantasia());
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
    public boolean modificarProveedor() {
        try {
            String query = "UPDATE Proveedor SET cuit = ?, nombreFantasia = ?, nombre = ?, apellido = ?, dni = ?, telefono = ?, email = ? WHERE cuit = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuit());
            params.put(2, getNombreFantasia());
            params.put(3, getNombre());
            params.put(4, getApellido());
            params.put(5, getDni());
            params.put(6, getTelefono());
            params.put(7, getEmail());
            params.put(8, getCuit());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


        
    public static List<Proveedor> fromResultSet(ResultSet resultados) {
        try {
            List<Proveedor> proveedores = new java.util.ArrayList<>();
            while (resultados.next()) {
                String cuit = resultados.getString("cuit");
                String nombreFantasia = resultados.getString("nombreFantasia");
                String nombre = resultados.getString("nombre");
                String apellido = resultados.getString("apellido");
                int dni = resultados.getInt("dni");
                String telefono = resultados.getString("telefono");
                String email = resultados.getString("email");
                proveedores.add(new Proveedor(cuit, nombreFantasia, nombre, apellido, dni, telefono, email));
            }
            return proveedores;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Proveedor buscarPorId(int proveedor_id) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Proveedor WHERE proveedor_id = '" + proveedor_id + "'");
            List<Proveedor> proveedores = Proveedor.fromResultSet(resultados);
            return proveedores.isEmpty() ? null : proveedores.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
