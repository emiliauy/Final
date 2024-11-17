/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfazUsuario.CU_Login;

/**
 *
 * @author Emilia
 */
public abstract class ControladorLogin {
    //codigo que se va a repetir en los dos controladores 

    private VistaLogin vista;

    public ControladorLogin(VistaLogin vista) {
        this.vista = vista;
    }

    //evento usuario
    public void login(String usuario, String pwd) {
        //llamo a la fachada con login admini o login jugador
        Object retorno = llamarLogin(usuario, pwd); //en el caso del jugador es una sesion y en el caso del administrador va a ser un usuario
        if (retorno == null) {
            vista.mostrarError("Usuario y/o pwd invalidos");
        } else {
            vista.cerrar();
            vista.llamarProximoCasoUso(retorno);
        }

    }
    
    public abstract Object llamarLogin(String usuario, String pwd);
}
