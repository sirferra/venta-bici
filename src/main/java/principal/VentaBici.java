package principal;
import GUI.Main;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.UIManager;

public class VentaBici { 
          
    public static void main(String[] args) {
        System.out.println("Venta de bicicletas");
        try{
            //Conexion.getInstance().migrarDB();
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            FlatDarculaLaf.setup();
            
            Main main = new Main();
            main.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
