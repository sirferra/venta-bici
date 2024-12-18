package venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import repositorio.Conexion;
import repositorio.MySqlDB;

public class VentasHistorico {


public static ResultSet obtenerVentasHistoricas(
    String nombreVendedor, String apellidoVendedor,
    String nombreCliente, String apellidoCliente,
    String fechaDesde, String fechaHasta,
    String montoMinimo, String montoMaximo) {
    
    String sql = """
        SELECT
            YEAR(p.fecha) AS anio,
            MONTH(p.fecha) AS mes,
            SUM(p.total) AS total_mes,
            COALESCE((
                SELECT pr.nombre
                FROM DetallePedido dp
                INNER JOIN Producto pr ON dp.producto_id = pr.id
                WHERE dp.pedido_id = p.id
                GROUP BY pr.id
                ORDER BY SUM(dp.cantidad) DESC
                LIMIT 1
            ), 'Sin producto') AS producto_mas_vendido
        FROM Pedido p
        INNER JOIN Cliente c ON p.cliente_id = c.id
        INNER JOIN Vendedor v ON p.vendedor_id = v.id
        WHERE (? IS NULL OR v.nombre LIKE ?)
          AND (? IS NULL OR v.apellido LIKE ?)
          AND (? IS NULL OR c.nombre LIKE ?)
          AND (? IS NULL OR c.apellido LIKE ?)
          AND (? IS NULL OR p.fecha >= ?)
          AND (? IS NULL OR p.fecha <= ?)
          AND (? IS NULL OR p.total >= ?)
          AND (? IS NULL OR p.total <= ?)
        GROUP BY YEAR(p.fecha), MONTH(p.fecha)
        ORDER BY YEAR(p.fecha), MONTH(p.fecha);
    """;

    try {
        fechaDesde = fechaDesde == null || fechaDesde.isEmpty() ? null : convertirFecha(fechaDesde);
        fechaHasta = fechaHasta == null || fechaHasta.isEmpty() ? null : convertirFecha(fechaHasta);

        HashMap<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, nombreVendedor);
        parametros.put(2, nombreVendedor == null ? null : "%" + nombreVendedor + "%");
        parametros.put(3, apellidoVendedor);
        parametros.put(4, apellidoVendedor == null ? null : "%" + apellidoVendedor + "%");
        parametros.put(5, nombreCliente);
        parametros.put(6, nombreCliente == null ? null : "%" + nombreCliente + "%");
        parametros.put(7, apellidoCliente);
        parametros.put(8, apellidoCliente == null ? null : "%" + apellidoCliente + "%");
        parametros.put(9, fechaDesde);
        parametros.put(10, fechaDesde);
        parametros.put(11, fechaHasta);
        parametros.put(12, fechaHasta);
        parametros.put(13, (montoMinimo == null || montoMinimo.isEmpty()) ? null : Double.parseDouble(montoMinimo));
        parametros.put(14, (montoMinimo == null || montoMinimo.isEmpty()) ? null : Double.parseDouble(montoMinimo));
        parametros.put(15, (montoMaximo == null || montoMaximo.isEmpty()) ? null : Double.parseDouble(montoMaximo));
        parametros.put(16, (montoMaximo == null || montoMaximo.isEmpty()) ? null : Double.parseDouble(montoMaximo));

        MySqlDB db = Conexion.getInstance();
        return db.executeQueryWithParams(sql, parametros);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

   public static void cargarDatosHistoricos(ResultSet rs, JTable dgvHistorico, 
                                         JTextField txtTotalHistorico, JTextField txtMontoMaxHistorico, 
                                         JTextField txtMontoMinHistorico, JTextField txtMesMaximoHistorico, 
                                         JTextField txtMesMinimoHistorico, JTextField txtMasVendidoHistorico) {
    try {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Año");
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : meses) {
            modelo.addColumn(mes);
        }

        HashMap<Integer, Double[]> datosPorAño = new HashMap<>();
        double totalGeneral = 0;
        double montoMaximo = Double.MIN_VALUE;
        double montoMinimo = Double.MAX_VALUE;
        int mesMaximo = 0, mesMinimo = 0, anioMaximo = 0, anioMinimo = 0;
        String productoMasVendido = "";

        while (rs.next()) {
            int anio = rs.getInt("anio");
            int mes = rs.getInt("mes");
            double totalMes = rs.getDouble("total_mes");

            // Actualizar totales generales
            totalGeneral += totalMes;
            if (totalMes > montoMaximo) {
                montoMaximo = totalMes;
                mesMaximo = mes;
                anioMaximo = anio;
            }
            if (totalMes < montoMinimo) {
                montoMinimo = totalMes;
                mesMinimo = mes;
                anioMinimo = anio;
            }

            // Guardar producto más vendido (solo la primera vez, ya que es el mismo para todo el histórico)
            if (productoMasVendido.isEmpty()) {
                productoMasVendido = rs.getString("producto_mas_vendido");
            }

            // Guardar totales por año y mes
            datosPorAño.putIfAbsent(anio, new Double[12]);
            datosPorAño.get(anio)[mes - 1] = totalMes;
        }

        // Rellenar la tabla
        for (var entry : datosPorAño.entrySet()) {
            Object[] fila = new Object[13];
            fila[0] = entry.getKey(); // Año
            Double[] totalesMeses = entry.getValue();
            for (int i = 0; i < 12; i++) {
                fila[i + 1] = (totalesMeses[i] != null) ? String.format("%.2f", totalesMeses[i]) : "0.00";
            }
            modelo.addRow(fila);
        }

        // Asignar el modelo a la tabla
        dgvHistorico.setModel(modelo);

        // Mostrar datos generales en los JTextField
        txtTotalHistorico.setText(String.format("%.2f", totalGeneral));
        txtMontoMaxHistorico.setText(String.format("%.2f", montoMaximo));
        txtMontoMinHistorico.setText(String.format("%.2f", montoMinimo));
        txtMesMaximoHistorico.setText(String.format("%s %d", meses[mesMaximo - 1], anioMaximo));
        txtMesMinimoHistorico.setText(String.format("%s %d", meses[mesMinimo - 1], anioMinimo));
        txtMasVendidoHistorico.setText(productoMasVendido);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar los datos históricos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static String convertirFecha(String fechaTexto) throws Exception {
        if (fechaTexto.isEmpty()) {
            return null;
        }
        String[] formatos = {
            "dd-MM-yyyy", "dd/MM/yyyy", "dd-MM-yy", "dd/MM/yy", "yyyy/MM/dd", "yyyy-MM-dd"
        };
        for (String formato : formatos) {
            try {
                SimpleDateFormat sdfEntrada = new SimpleDateFormat(formato);
                sdfEntrada.setLenient(false);
                Date fecha = sdfEntrada.parse(fechaTexto);
                SimpleDateFormat sdfSalida = new SimpleDateFormat("yyyy-MM-dd");
                return sdfSalida.format(fecha);
            } catch (ParseException ignored) {
            }
        }
        throw new Exception("Formato de fecha inválido: " + fechaTexto);
    }
}