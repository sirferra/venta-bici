/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package producto;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import repositorio.Conexion;

/**
 *
 * @author P4rzival
 */
public class Marca {
    private int id;
    private String nombre;

    public Marca(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // crud marca

    public static boolean crearMarca(String nombre) {
        try {
            String sql = "INSERT INTO marca (nombre) VALUES (?)";
            HashMap<Integer, Object> marca = new HashMap<>();
            marca.put(1, nombre);
            Conexion.getInstance().executeQueryWithParams(sql, marca);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Marca> getAll() {        
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Marca");
            return Marca.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Marca buscarPorId(int marca_id) {
        try {
            String sql = "SELECT * FROM marca WHERE marca_id = ?";
            HashMap<Integer, Object> marca = new HashMap<>();
            marca.put(1, marca_id);
            ResultSet resultados = Conexion.getInstance().executeQueryWithParams(sql, marca);
            return Marca.fromResultSet(resultados).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Marca> buscarPorNombre(String nombre) {
        try {
            String sql = "SELECT * FROM marca WHERE nombre = ?";
            HashMap<Integer, Object> marca = new HashMap<>();
            marca.put(1, nombre);
            ResultSet resultados = Conexion.getInstance().executeQueryWithParams(sql, marca);
            return Marca.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean modificarMarca() {
        try {
            String sql = "UPDATE marca SET marca_id = ?, nombre = ? WHERE marca_id = ?";
            HashMap<Integer, Object> marca = new HashMap<>();
            marca.put(1, getId());
            marca.put(2, getNombre());
            marca.put(3, getId());
            Conexion.getInstance().executeQueryWithParams(sql, marca);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarMarca() {
        try {
            String sql = "DELETE FROM marca WHERE marca_id = ?";
            HashMap<Integer, Object> marca = new HashMap<>();
            marca.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(sql, marca);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Marca> fromResultSet(ResultSet resultados) {
        try {
            List<Marca> marcas = new java.util.ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("marca_id");
                String nombre = resultados.getString("nombre");
                marcas.add(new Marca(id, nombre));
            }
            return marcas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }   



    
}
