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
public class Categoria {
    private int id;
    private String nombre;

    public Categoria(int id, String nombre) {
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

    public static List<Categoria> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Categoria");
            return Categoria.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<Categoria> buscarPorNombre(String nombre) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Categoria WHERE nombre = '" + nombre + "'");
            return Categoria.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
    
    public static Categoria buscarPorId(int categoria_id) {
        try{
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Categoria WHERE categoria_id = '" + categoria_id + "'");
            List<Categoria> categorias = Categoria.fromResultSet(resultados);
            return categorias.isEmpty() ? null : categorias.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminarCategoria() {
        try {
            String query = "DELETE FROM Categoria WHERE categoria_id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crearCategoria() {
        try {
            String query = "INSERT INTO Categoria(categoria_id, nombre) VALUES(?, ?)";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getId());
            params.put(2, getNombre());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean modificarCategoria() {
        try {
            String query = "UPDATE Categoria SET categoria_id = ?, nombre = ? WHERE categoria_id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getId());
            params.put(2, getNombre());
            params.put(3, getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Categoria> fromResultSet(ResultSet resultados) {
        try {
            List<Categoria> categorias = new java.util.ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("categoria_id");
                String nombre = resultados.getString("nombre");
                categorias.add(new Categoria(id, nombre));
            }
            return categorias;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
