/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedido;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import producto.Producto;
import repositorio.Conexion;

/**
 *
 * @author facundo.cuffia
 */
public class DetallePedido {
    //Atributos
    private int id;
    private Producto producto; 
    private int cantidad;
    private double precio;
    private Pedido pedido;
    
    //Constructores
    public DetallePedido(){
        super();
    }
    
    public DetallePedido(int id, Producto producto, int cantidad, double precio, Pedido pedido) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.pedido = pedido;
    }
    
    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    
    
    String sql = "SELECT pr.nombre AS producto, d.cantidad, d.precio, de.total FROM"
            + "detallepedido AS d INNER JOIN"
            + "Producto AS pr ON pedido_id = pr.id INNER JOIN "
            + "";
    
}    


