package producto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persona.Proveedor;
import repositorio.Conexion;

public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private Proveedor proveedor;
    private Categoria categoria;
    private int stock;
    private Modelo modelo;

    // Nombres descriptivos adicionales
    private String nombreProveedor;
    private String nombreCategoria;
    private String nombreModelo;

    public Producto() {
        super();
    }

    // Constructor completo (incluye nombres descriptivos)
    public Producto(int id, String codigo, String nombre, Proveedor proveedor, Categoria categoria, int stock, Modelo modelo, 
                    String nombreProveedor, String nombreCategoria, String nombreModelo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.stock = stock;
        this.modelo = modelo;
        this.nombreProveedor = nombreProveedor;
        this.nombreCategoria = nombreCategoria;
        this.nombreModelo = nombreModelo;
    }

    // Constructor simplificado
    public Producto(int id, String codigo, String nombre, int proveedorId, int categoriaId, int stock, int modeloId) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = Proveedor.buscarPorId(proveedorId);
        this.categoria = Categoria.buscarPorId(categoriaId);
        this.stock = stock;
        this.modelo = Modelo.buscarPorId(modeloId);
    }

    // Getters y setters
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

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    // Métodos CRUD
    public boolean crearProducto() {
        try {
        String query = "INSERT INTO Producto (codigo, nombre, proveedor_id, modelo_id, categoria_id) VALUES (?, ?, ?, ?, ?)";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, getCodigo());
        params.put(2, getNombre());
        params.put(3, getProveedor().getId());
        params.put(4, getModelo().getId());
        params.put(5, getCategoria().getId());
        Conexion.getInstance().executeQueryWithParams(query, params);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
         }
    }

public boolean actualizarProducto() {
    if (proveedor == null || categoria == null || modelo == null) {
        System.err.println("Error: Faltan datos necesarios para actualizar el producto.");
        return false;
    }

    try {
        System.out.println("Actualizando producto con los siguientes datos:");
        System.out.println("ID Producto: " + getId());
        System.out.println("Proveedor ID: " + (proveedor != null ? proveedor.getId() : "null"));
        System.out.println("Categoría ID: " + (categoria != null ? categoria.getId() : "null"));
        System.out.println("Modelo ID: " + (modelo != null ? modelo.getId() : "null"));

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

    // Métodos para obtener productos (filtros y consulta general)
    public static List<Producto> getAll() {
    String query = "SELECT Producto.id, Producto.codigo, Producto.nombre, Producto.stock, " +
                   "Proveedor.id AS proveedor_id, " + // Incluye el ID del proveedor
                   "CONCAT(Proveedor.nombre, ' ', Proveedor.apellido) AS nombre_proveedor, " +
                   "Categoria.nombre AS nombre_categoria, " +
                   "Modelo.nombre AS nombre_modelo " +
                   "FROM Producto " +
                   "INNER JOIN Proveedor ON Producto.proveedor_id = Proveedor.id " +
                   "INNER JOIN Categoria ON Producto.categoria_id = Categoria.id " +
                   "INNER JOIN Modelo ON Producto.modelo_id = Modelo.id";
    try {
        ResultSet resultados = Conexion.getInstance().executeQuery(query);
        return Producto.fromResultSet(resultados);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
    public static Producto buscarProductoPorCodigo(String codigo) {
    try {
        String query = "SELECT Producto.id, Producto.codigo, Producto.nombre, Producto.stock, " +
                       "CONCAT(Proveedor.nombre, ' ', Proveedor.apellido) AS nombre_proveedor, " +
                       "Categoria.nombre AS nombre_categoria, " +
                       "Modelo.nombre AS nombre_modelo " +
                       "FROM Producto " +
                       "INNER JOIN Proveedor ON Producto.proveedor_id = Proveedor.id " +
                       "INNER JOIN Categoria ON Producto.categoria_id = Categoria.id " +
                       "INNER JOIN Modelo ON Producto.modelo_id = Modelo.id " +
                       "WHERE Producto.codigo = ?";
        HashMap<Integer, Object> params = new HashMap<>();
        params.put(1, codigo);

        ResultSet resultados = Conexion.getInstance().executeQueryWithParams(query, params);

        if (resultados.next()) {
            int id = resultados.getInt("id");
            String nombre = resultados.getString("nombre");
            int stock = resultados.getInt("stock");
            String nombreProveedor = resultados.getString("nombre_proveedor");
            String nombreCategoria = resultados.getString("nombre_categoria");
            String nombreModelo = resultados.getString("nombre_modelo");

            // Crear y retornar producto
            Producto producto = new Producto();
            producto.setId(id);
            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setStock(stock);
            producto.setNombreProveedor(nombreProveedor);
            producto.setNombreCategoria(nombreCategoria);
            producto.setNombreModelo(nombreModelo);

            return producto;
        } else {
            System.out.println("No se encontró un producto con el código especificado.");
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
    
    public static List<Producto> buscarPorFiltros(String codigo, String categoria, String modelo) {
        try {
            String query = "SELECT Producto.id, Producto.codigo, Producto.nombre, " +
                           "CONCAT(Proveedor.nombre, ' ', Proveedor.apellido) AS nombre_proveedor, " +
                           "Categoria.nombre AS nombre_categoria, " +
                           "Modelo.nombre AS nombre_modelo " +
                           "FROM Producto " +
                           "INNER JOIN Proveedor ON Producto.proveedor_id = Proveedor.id " +
                           "INNER JOIN Categoria ON Producto.categoria_id = Categoria.id " +
                           "INNER JOIN Modelo ON Producto.modelo_id = Modelo.id " +
                           "WHERE 1=1 ";

            if (codigo != null && !codigo.isEmpty()) {
                query += "AND Producto.codigo LIKE '%" + codigo + "%' ";
            }
            if (categoria != null && !categoria.isEmpty()) {
                query += "AND Categoria.nombre LIKE '%" + categoria + "%' ";
            }
            if (modelo != null && !modelo.isEmpty()) {
                query += "AND Modelo.nombre LIKE '%" + modelo + "%' ";
            }

            ResultSet resultados = Conexion.getInstance().executeQuery(query);
            return Producto.fromResultSetSinStock(resultados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Producto> fromResultSet(ResultSet resultados) {
    try {
        List<Producto> productos = new ArrayList<>();
        while (resultados.next()) {
            int id = resultados.getInt("id");
            String codigo = resultados.getString("codigo");
            String nombre = resultados.getString("nombre");
            int stock = resultados.getInt("stock");

            // Agregar el ID del proveedor
            int proveedorId = resultados.getInt("proveedor_id");
            String nombreProveedor = resultados.getString("nombre_proveedor");
            String nombreCategoria = resultados.getString("nombre_categoria");
            String nombreModelo = resultados.getString("nombre_modelo");

            Producto producto = new Producto();
            producto.setId(id);
            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setStock(stock);
            producto.setNombreProveedor(nombreProveedor); // Mostrar concatenado en la grilla
            producto.setNombreCategoria(nombreCategoria);
            producto.setNombreModelo(nombreModelo);

            // Asigna un objeto proveedor con solo el ID
            Proveedor proveedor = new Proveedor();
            proveedor.setId(proveedorId);
            producto.setProveedor(proveedor);

            productos.add(producto);
        }
        return productos;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
    public static List<Producto> fromResultSetSinStock(ResultSet resultados) {
        try {
            List<Producto> productos = new ArrayList<>();
            while (resultados.next()) {
                int id = resultados.getInt("id");
                String codigo = resultados.getString("codigo");
                String nombre = resultados.getString("nombre");
                String nombreProveedor = resultados.getString("nombre_proveedor");
                String nombreCategoria = resultados.getString("nombre_categoria");
                String nombreModelo = resultados.getString("nombre_modelo");

                Producto producto = new Producto();
                producto.setId(id);
                producto.setCodigo(codigo);
                producto.setNombre(nombre);
                producto.setNombreProveedor(nombreProveedor);
                producto.setNombreCategoria(nombreCategoria);
                producto.setNombreModelo(nombreModelo);

                productos.add(producto);
            }
            return productos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}