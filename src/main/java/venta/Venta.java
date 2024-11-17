/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package venta;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import repositorio.Conexion;

/**
 *
 * @author P4rzival
 */
public class Venta {
    
    private int id;
    private String cliente;
    private String producto;
    private int cantidad;
    private double total;

    public Venta() {
        super();
    }

    public Venta(int id, String cliente, String producto, int cantidad, double total) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public Venta(String cliente, String producto, int cantidad, double total) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
   public boolean crearVenta() {
        try {
            String query = "INSERT INTO Venta (cliente, producto, cantidad, total) VALUES (?, ?, ?, ?)";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCliente());
            params.put(2, getProducto());
            params.put(3, getCantidad());
            params.put(4, getTotal());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Venta buscarPorId(int id) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Venta WHERE id = '" + id + "'");
            List<Venta> ventas = Venta.fromResultSet(resultados);
            return ventas.isEmpty() ? null : ventas.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Venta> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Venta");
            return Venta.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarVenta() {
        try {
            String query = "UPDATE Venta SET cliente = ?, producto = ?, cantidad = ?, total = ? WHERE id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCliente());
            params.put(2, getProducto());
            params.put(3, getCantidad());
            params.put(4, getTotal());
            params.put(5, getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVenta() {
        try {
            String query = "DELETE FROM Venta WHERE id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Métodos auxiliares

    public static List<Venta> fromResultSet(ResultSet resultados) {
        try {
            List<Venta> ventas = new java.util.ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("id");
                String cliente = resultados.getString("cliente");
                String producto = resultados.getString("producto");
                int cantidad = resultados.getInt("cantidad");
                double total = resultados.getDouble("total");
                ventas.add(new Venta(id, cliente, producto, cantidad, total));
            }
            return ventas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 /*
    Posible tabla ventas, la imagine para este crud
  * CREATE TABLE Venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(100),
    producto VARCHAR(100),
    cantidad INT,
    total DECIMAL(10, 2)
);
  */
    
    

    
}
