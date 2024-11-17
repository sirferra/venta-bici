package principal;
import GUI.Main;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

public class VentaBici { 
          
    public static void main(String[] args) {
        System.out.println("Venta de bicicletas");
        try{
            //Conexion.getInstance().migrarDB();
            UIManager.setLookAndFeel(new FlatDarkLaf());

            Main main = new Main();
            main.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
