/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedido;
import java.sql.ResultSet;
import java.sql.SQLException;
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
   
    // Método para obtener los detalles desde la base de datos
    public static List<DetallePedido> obtenerDetalles(int id_pedido){
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT dp.id, p.nombre, dp.cantidad, dp.precio, pe.total "
                   + "FROM detallepedido AS dp "
                   + "INNER JOIN producto AS p ON dp.producto_id = p.id "
                   + "INNER JOIN pedido AS pe ON dp.pedido_id = pe.id "
                   + "WHERE dp.pedido_id = " + id_pedido;    
        try{
            ResultSet resultados = Conexion.getInstance().executeQuery(sql);
            while (resultados.next()) {
                //Creo los objetos a agregar
                DetallePedido detalle = new DetallePedido();
                Producto producto = new Producto();
                Pedido pedido = new Pedido();
                //Recupero los valores de la consulta y los asigno a los atributos
                producto.setNombre(resultados.getString("p.nombre"));
                pedido.setTotal(resultados.getDouble("pe.total"));
                detalle.setId(resultados.getInt("dp.id"));
                detalle.setPedido(pedido);
                detalle.setProducto(producto);
                detalle.setCantidad(resultados.getInt("cantidad"));
                detalle.setPrecio(resultados.getDouble("precio"));
                //Añado el objeto detalle a la lista.
                detalles.add(detalle);
            }
        } 
        catch (SQLException e){
            e.printStackTrace();
        }
        return detalles;
    }
    //Obtener el producto más vendido de u
    public static String obtenerProductoMasVendido() {
        String productoMasVendido = null;
        String sql = "SELECT p.nombre, SUM(dp.cantidad) AS total_vendido " +
                     "FROM DetallePedido dp " +
                     "INNER JOIN Producto p ON dp.producto_id = p.id " +
                     "GROUP BY p.nombre " +
                     "ORDER BY total_vendido DESC " +
                     "LIMIT 1";
        try {
            ResultSet rs = Conexion.getInstance().executeQuery(sql);
            if (rs.next()) {
                productoMasVendido = rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el producto más vendido: " + e.getMessage());
        }
        return productoMasVendido;
    }
}    
