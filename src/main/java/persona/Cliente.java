/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

import java.sql.ResultSet;
import java.sql.SQLException;
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

}
