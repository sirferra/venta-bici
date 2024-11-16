/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

/**
 *
 * @author facundo.cuffia
 */
public class Proveedor extends Persona {
    //Atributos
    private String cuit;
    private String nombreFantasia;
    
    //Constructores
    public Proveedor(){
        super();
    }

    public Proveedor(String cuit, String nombreFantasia) {
        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
    }

    public Proveedor(String cuit, String nombreFantasia, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
    }
    
    //Getters y Setters
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }
    
      
}
