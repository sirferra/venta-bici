/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package producto;

import java.sql.ResultSet;
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
