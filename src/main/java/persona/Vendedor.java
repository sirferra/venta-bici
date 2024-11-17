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

    public Vendedor(String cuit, String sucursal, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
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
     **/
    
    
    // Obtiene todos los vendedores y lo meto a la lista
    public static List<Vendedor> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Vendedor");
            return Vendedor.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    // Filtrado por cuit
    public static Vendedor buscarPorCuit(String cuit) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Vendedor WHERE cuit = '" + cuit + "'");
            List<Vendedor> vendedores = Vendedor.fromResultSet(resultados);
            return vendedores.isEmpty() ? null : vendedores.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //Filtrado por DNI

    public static Vendedor buscarPorDni(int dni) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Vendedor WHERE dni = '" + dni + "'");
            List<Vendedor> vendedores = Vendedor.fromResultSet(resultados);
            return vendedores.isEmpty() ? null : vendedores.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            Vendedor vendedorExistente = buscarPorDni(getDni());
            if (vendedorExistente == null) {
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
    
    // Convierto el resultado de las consultas en una lista objeto

    public static List<Vendedor> fromResultSet(ResultSet resultados) {
        try {
            List<Vendedor> vendedores = new java.util.ArrayList<>();
            while (resultados.next()) {
                String cuit = resultados.getString("cuit");
                String sucursal = resultados.getString("sucursal");
                String nombre = resultados.getString("nombre");
                String apellido = resultados.getString("apellido");
                int dni = resultados.getInt("dni");
                String telefono = resultados.getString("telefono");
                String email = resultados.getString("email");
                vendedores.add(new Vendedor(cuit, sucursal, nombre, apellido, dni, telefono, email));
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
