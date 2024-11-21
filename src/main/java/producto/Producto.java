/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package producto;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import persona.Proveedor;
import repositorio.Conexion;

/**
 *
 * @author P4rzival
 */
public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private Proveedor proveedor;
    private Categoria categoria;
    private int stock;
    private Modelo modelo;

    public Producto() {
        super();
    }

    // Constructor en base a relaciones por código
    public Producto(int id, String codigo, String nombre, Proveedor proveedor, Categoria categoria, int stock, Modelo modelo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.stock = stock;
        this.modelo = modelo;
    }

    // Constructor sin ID
    public Producto(String codigo, String nombre, Proveedor proveedor, Categoria categoria, int stock, Modelo modelo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.stock = stock;
        this.modelo = modelo;
    }
    

    // Constructor en base a relaciones por base de datos
    public Producto(int id, String codigo, String nombre, int proveedor_id, int categoria_id, int stock, int modelo_id) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = Proveedor.buscarPorId(proveedor_id); 
        this.categoria = Categoria.buscarPorId(categoria_id);
        this.stock = stock;
        this.modelo = Modelo.buscarPorId(modelo_id);
    }
    
    // Getter and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    //CRUD 
    public boolean crearProducto() {
        try {
            String query = "INSERT INTO Producto (codigo, nombre, proveedor_id, categoria_id, stock, modelo_id) VALUES (?, ?, ?, ?, ?, ?)";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCodigo());
            params.put(2, getNombre());
            params.put(3, getProveedor().getId());
            params.put(4, getCategoria().getId());
            params.put(5, getStock());
            params.put(6, getModelo().getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Producto buscarPorId(int id) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE id = '" + id + "'");
            List<Producto> productos = Producto.fromResultSet(resultados);
            return productos.isEmpty() ? null : productos.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Producto> getAll() {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarProducto() {
        try {
            String query = "UPDATE Producto SET codigo = ?, nombre = ?, proveedor_id = ?, categoria_id = ?, stock = ?, modelo_id = ? WHERE id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getCodigo());
            params.put(2, getNombre());
            params.put(3, getProveedor().getId());
            params.put(4, getCategoria().getId());
            params.put(5, getStock());
            params.put(6, getModelo().getId());
            params.put(7, getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProducto() {
        try {
            String query = "DELETE FROM Producto WHERE id = ?";
            HashMap<Integer, Object> params = new HashMap<>();
            params.put(1, getId());
            Conexion.getInstance().executeQueryWithParams(query, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // filtros
    public static List<Producto> buscarPorProveedor(Proveedor proveedor) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE proveedor_id = '" + proveedor.getId() + "'");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Producto> buscarPorCategoria(Categoria categoria) {
        try {    
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE categoria_id = '" + categoria.getId() + "'");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return null;
    }

    public static List<Producto> buscarPorModelo(Modelo modelo) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE modelo_id = '" + modelo.getId() + "'");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Producto> buscarPorNombre(String nombre) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE nombre = '" + nombre + "'");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Producto> buscarPorStock(int stock) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE stock = '" + stock + "'");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Producto> buscarPorCodigo(String codigo) {
        try {
            ResultSet resultados = Conexion.getInstance().executeQuery("SELECT * FROM Producto WHERE codigo = '" + codigo + "'");
            return Producto.fromResultSet(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Data transformer
    public static List<Producto> fromResultSet(ResultSet resultados) {
        try {
            List<Producto> productos = new java.util.ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("id");
                String codigo = resultados.getString("codigo");
                String nombre = resultados.getString("nombre");
                int proveedor_id = resultados.getInt("proveedor_id");
                int categoria_id = resultados.getInt("categoria_id");
                int stock = resultados.getInt("stock");
                int modelo_id = resultados.getInt("modelo_id");
                productos.add(new Producto(id, codigo, nombre, proveedor_id, categoria_id, stock, modelo_id));
            }
            return productos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
