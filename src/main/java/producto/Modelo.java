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
    
    
}
