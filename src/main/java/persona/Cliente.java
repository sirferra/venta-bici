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

    //Getters y Setters
    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public static List<Cliente> getAll(){
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Cliente");
            return Cliente.fromResultSet(resultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cliente buscarPorCuil(String cuil){
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Cliente WHERE cuil = '" + cuil + "'");
            return Cliente.fromResultSet(resultados).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cliente buscarPorDni(int dni){
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Cliente WHERE dni = '" + dni + "'");
            return Cliente.fromResultSet(resultados).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean crearCliente(){
        try {
            String query = "INSERT INTO Cliente(cuil, nombre, apellido, dni, telefono, email) VALUES(?, ?, ?, ?, ?, ?)";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCuil());
            params.put(2, getNombre());
            params.put(3, getApellido());
            params.put(4, getDni());
            params.put(5, getTelefono());
            params.put(6, getEmail());

            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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


    public boolean eliminarCliente(){
        try {
            int id = buscarPorDni(getDni()).getId();
            if(id!=0){
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
