/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedido;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import persona.Cliente;
import persona.Vendedor;
import repositorio.Conexion;
/**
 *
 * @author facundo.cuffia
 */
public class Pedido {
    //Atributos
    private int id;
    private Date fecha;
    private Cliente cliente; 
    private Vendedor vendedor;
    private double total;
    
    //Constructores
    public Pedido(){
        super();
    }

    public Pedido(int id, Date fecha, Cliente cliente, Vendedor vendedor, double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.total = total;
    }
    public Pedido(Date fecha, Cliente cliente, Vendedor vendedor, double total) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.total = total;
    }

    //Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente Cliente) {
        this.cliente = Cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor Vendedor) {
        this.vendedor = Vendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    // Método para obtener los datos de pedidos
    public static List<Pedido> buscarTodosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        try {
            String sql = "SELECT p.id, p.fecha, " +
             "CONCAT(v.nombre, ' ', v.apellido) AS vendedor, " +
             "CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
             "p.total " +
             "FROM Pedido AS p " +
             "INNER JOIN Vendedor AS v ON p.vendedor_id = v.id " +
             "INNER JOIN Cliente AS c ON p.cliente_id = c.id";
            ResultSet resultados = Conexion.getInstance().executeQuery(sql);

            while (resultados.next()) {
                // Obtener los valores del ResultSet
                int id = resultados.getInt("id");
                Date fecha = resultados.getDate("fecha");
                String vendedorNombre = resultados.getString("vendedor");
                String clienteNombre = resultados.getString("cliente");
                double total = resultados.getDouble("total");

                // Crear instancias de Cliente y Vendedor con los nombres
                Cliente cliente = new Cliente(); // Ajusta según la implementación de Cliente
                cliente.setNombre(clienteNombre);

                Vendedor vendedor = new Vendedor(); // Ajusta según la implementación de Vendedor
                vendedor.setNombre(vendedorNombre);

                // Crear el objeto Pedido
                Pedido pedido = new Pedido(id, fecha, cliente, vendedor, total);
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }
    
    //Listado con filtros
    public static List<Pedido> buscarPorFiltros(String clienteNombre, String clienteApellido, String vendedorNombre, String vendedorApellido, String fechaDesde, String fechaHasta) {
        List<Pedido> pedidos = new ArrayList<>();
        try {
            // Consulta base
            String sqlFiltro = "SELECT p.id, p.fecha, " +
                               "CONCAT(v.nombre, ' ', v.apellido) AS vendedor, " +
                               "CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
                               "p.total " +
                               "FROM Pedido AS p " +
                               "INNER JOIN Vendedor AS v ON p.vendedor_id = v.id " +
                               "INNER JOIN Cliente AS c ON p.cliente_id = c.id WHERE 1=1";

            // Condiciones dinámicas
            if (clienteNombre != null && !clienteNombre.isEmpty()) {
                sqlFiltro += " AND c.nombre LIKE '%" + clienteNombre + "%'";
            }
            if (clienteApellido != null && !clienteApellido.isEmpty()) {
                sqlFiltro += " AND c.apellido LIKE '%" + clienteApellido + "%'";
            }
            if (vendedorNombre != null && !vendedorNombre.isEmpty()) {
                sqlFiltro += " AND v.nombre LIKE '%" + vendedorNombre + "%'";
            }
            if (vendedorApellido != null && !vendedorApellido.isEmpty()) {
                sqlFiltro += " AND v.apellido LIKE '%" + vendedorApellido + "%'";
            }
            if (fechaDesde != null && !fechaDesde.isEmpty()) {
                sqlFiltro += " AND p.fecha >= '" + fechaDesde + "'";
            }
            if (fechaHasta != null && !fechaHasta.isEmpty()) {
                sqlFiltro += " AND p.fecha <= '" + fechaHasta + "'";
            }

            // Ejecutar la consulta
            ResultSet resultados = Conexion.getInstance().executeQuery(sqlFiltro);

            while (resultados.next()) {
                // Obtener los valores del ResultSet
                int id = resultados.getInt("id");
                Date fecha = resultados.getDate("fecha");
                String vendedor = resultados.getString("vendedor");
                String cliente = resultados.getString("cliente");
                double total = resultados.getDouble("total");

                // Crear instancias de Cliente y Vendedor
                Cliente clienteObj = new Cliente();
                clienteObj.setNombre(cliente);

                Vendedor vendedorObj = new Vendedor();
                vendedorObj.setNombre(vendedor);

                // Crear el objeto Pedido
                Pedido pedido = new Pedido(id, fecha, clienteObj, vendedorObj, total);
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    
   
    public boolean crearPedido() {
        try {
            String sql = "INSERT INTO Pedido (fecha, vendedor_id, cliente_id, total) VALUES (?, ?, ?, ?)";
            HashMap<Integer, Object> pedido = new HashMap<>();
            pedido.put(1, getFecha());
            pedido.put(2, getVendedor().getId());
            pedido.put(3, getCliente().getId());
            pedido.put(4, getTotal());
            Conexion.getInstance().executeQueryWithParams(sql, pedido);
            // get id from last insert
            setId(Conexion.getInstance().getIdFromLastInsert());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPedido() {
        try {
            String sql = "DELETE FROM Pedido WHERE id = ?";
            HashMap<Integer, Object> pedido = new HashMap<>();
            pedido.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(sql, pedido);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarPedido() {
        try {
            String sql = "UPDATE Pedido SET fecha = ?, vendedor_id = ?, cliente_id = ?, total = ? WHERE id = ?";
            HashMap<Integer, Object> pedido = new HashMap<>();
            pedido.put(1, getFecha());
            pedido.put(2, getVendedor().getId());
            pedido.put(3, getCliente().getId());
            pedido.put(4, getTotal());
            pedido.put(5, getId());
            Conexion.getInstance().executeQueryWithParams(sql, pedido);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Pedido> filtrarPorVendedor(Vendedor vendedor) {
        try {
            String sql = "SELECT * FROM Pedido WHERE vendedor_id = ?";
            HashMap<Integer, Object> pedido = new HashMap<>();
            pedido.put(1, vendedor.getId());
            return fromResultSet(Conexion.getInstance().executeQueryWithParams(sql, pedido));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /*/Auxiliar para convertir resultsets**/
    public static List<Pedido> fromResultSet(ResultSet rs) {
        List<Pedido> pedidos = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                Date fecha = rs.getDate("fecha");
                Cliente cliente = Cliente.buscarPorId(rs.getInt("cliente"));
                Vendedor vendedor = Vendedor.buscarPorId(rs.getInt("vendedor"));
                double total = rs.getDouble("total");
                Pedido pedido = new Pedido(id, fecha, cliente, vendedor, total);
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public static Pedido buscarPorId(int id) {
        try {
            String sql = "SELECT * FROM Pedido WHERE id = ?";
            HashMap<Integer, Object> pedido = new HashMap<>();
            pedido.put(1, id);
            return fromResultSet(Conexion.getInstance().executeQueryWithParams(sql, pedido)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
