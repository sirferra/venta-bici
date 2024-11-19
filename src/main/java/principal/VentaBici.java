package principal;
import GUI.Main;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.UIManager;
import repositorio.Conexion;

public class VentaBici { 
          
    public static void main(String[] args) {
        System.out.println("Venta de bicicletas");
        try{
            //Recordar descomentar si inicio el programa por primera vez en una pc
            //Conexion.getInstance().migrarDB();
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            FlatDarculaLaf.setup();
            
            Main main = new Main();
            main.setLocationRelativeTo(null);
            main.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
