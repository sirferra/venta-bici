/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

/**
 *
 * @author facundo.cuffia
 */
public class Cliente extends Persona{
    //Atributos
    String codigo;
    String cuil;
    
    //Constructores
    public Cliente() {
        super();
    }
    
    public Cliente(String codigo, String cuil) {
        this.codigo = codigo;
        this.cuil = cuil;
    }

    public Cliente(String codigo, String cuil, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.codigo = codigo;
        this.cuil = cuil;
    }

    //Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

}
