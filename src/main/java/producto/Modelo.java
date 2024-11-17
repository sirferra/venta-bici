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
public class Modelo {
    
    private int id;
    private String nombre;
    private Marca marca;

    public Modelo(int id, String nombre, Marca marca) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
    }

    public Modelo(int id, String nombre, int marca_id) {
        this.id = id;
        this.nombre = nombre;
        this.marca = Marca.buscarPorId(marca_id);
    }

    // crud 

    public boolean crearModelo() {
        try {
            String sql = "INSERT INTO Modelo (nombre, marca_id) VALUES (?, ?)";
            HashMap<Integer, Object> modelo = new HashMap<>();
            modelo.put(1, getNombre());
            modelo.put(2, getMarca().getId());
            Conexion.getInstance().executeQueryWithParams(sql, modelo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Modelo> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Modelo");
            return Modelo.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Modelo buscarPorId(int modelo_id) {
        try{
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Modelo WHERE modelo_id = '" + modelo_id + "'");
            List<Modelo> modelos = Modelo.fromResultSet(resultados);
            return modelos.isEmpty() ? null : modelos.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<Modelo> buscarPorMarca(int marca_id) {
        try{
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Modelo WHERE marca_id = '" + marca_id + "'");
            return Modelo.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Modelo> buscarPorNombre(String nombre) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Modelo WHERE nombre = '" + nombre + "'");
            return Modelo.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizarModelo() {
        try {
            String sql = "UPDATE Modelo SET nombre = ?, marca_id = ? WHERE modelo_id = ?";
            HashMap<Integer, Object> modelo = new HashMap<>();
            modelo.put(1, getNombre());
            modelo.put(2, getMarca().getId());
            modelo.put(3, getId());
            Conexion.getInstance().executeQueryWithParams(sql, modelo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarModelo() {
        try {
            String sql = "DELETE FROM Modelo WHERE modelo_id = ?";
            HashMap<Integer, Object> modelo = new HashMap<>();
            modelo.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(sql, modelo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Modelo> fromResultSet(ResultSet resultados) {
        try {
            List<Modelo> modelos = new java.util.ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("modelo_id");
                String nombre = resultados.getString("nombre");
                int marca_id = resultados.getInt("marca_id");
                modelos.add(new Modelo(id, nombre, marca_id));
            }
            return modelos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    
}
