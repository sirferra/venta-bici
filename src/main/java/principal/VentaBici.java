package principal;
import GUI.Main;

public class VentaBici { 
          
    public static void main(String[] args) {
        System.out.println("Venta de bicicletas");
        try{
            //Conexion.getInstance().migrarDB();
            Main main = new Main();
            main.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
