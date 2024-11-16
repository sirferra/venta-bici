/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

/**
 *
 * @author facundo.cuffia
 */
public class Vendedor extends Persona {
    //Atributos
    private String cuit;
    private String sucursal;
    
    //Constructores
    public Vendedor() {
        super();
    }
    
    public Vendedor(String cuit, String sucursal) {
        this.cuit = cuit;
        this.sucursal = sucursal;
    }

    public Vendedor(String cuit, String sucursal, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuit = cuit;
        this.sucursal = sucursal;
    }
    
    //Getters and Setters
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    
    
}
