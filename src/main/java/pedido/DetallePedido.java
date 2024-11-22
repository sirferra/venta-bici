/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedido;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
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

    public DetallePedido(Producto producto, int cantidad, double precio, Pedido pedido) {
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
    
    
    public static List<DetallePedido> fromResultSet(ResultSet rs) throws Exception {
        List<DetallePedido> detalles = new ArrayList<>(); 
        while (rs.next()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setId(rs.getInt("id"));
            detalle.setProducto(Producto.buscarPorId(rs.getInt("producto_id"))); // Aquí debes buscar el producto por su ID
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setPrecio(rs.getDouble("precio"));
            detalle.setPedido(Pedido.buscarPorId(rs.getInt("pedido_id"))); // Aquí debes buscar el pedido por su ID
            detalles.add(detalle);
        }
        return detalles;
    }

    public boolean eliminarDetallePedido() {
        try {
            String sql = "DELETE FROM DetallePedido WHERE id = ?;";
            HashMap<Integer, Object> detalle = new HashMap<>();
            detalle.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(sql, detalle);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarDetallePedido() {
        try {
            String sql = "UPDATE DetallePedido SET cantidad = ?, precio = ? WHERE id = ?;";
            HashMap<Integer, Object> detalle = new HashMap<>();
            detalle.put(1, getCantidad());
            detalle.put(2, getPrecio());
            detalle.put(3, getId());
            Conexion.getInstance().executeQueryWithParams(sql, detalle);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crearDetallePedido() {
        try {
            String sql = "INSERT INTO DetallePedido (cantidad, precio, pedido_id, producto_id) VALUES (?, ?, ?, ?);";
            HashMap<Integer, Object> detalle = new HashMap<>();
            detalle.put(1, getCantidad());
            detalle.put(2, getPrecio());
            detalle.put(3, getPedido().getId());
            detalle.put(4, getProducto().getId());
            Conexion.getInstance().executeQueryWithParams(sql, detalle);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<DetallePedido> buscarPorPedido(Pedido pedido) {
        try {
            String sql = "SELECT * FROM DetallePedido WHERE pedido_id = ?;";
            HashMap<Integer, Object> detalle = new HashMap<>();
            detalle.put(1, pedido.getId());
            return fromResultSet(Conexion.getInstance().executeQueryWithParams(sql, detalle));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
    
    public static DetallePedido buscarPorId(int id) {
        try {
            String sql = "SELECT * FROM DetallePedido WHERE id = ?;";
            HashMap<Integer, Object> detalle = new HashMap<>();
            detalle.put(1, id);
            return fromResultSet(Conexion.getInstance().executeQueryWithParams(sql, detalle)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<DetallePedido> getAll() {
        try {
            String sql = "SELECT * FROM DetallePedido;";
            return fromResultSet(Conexion.getInstance().executeQuery(sql));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}    


