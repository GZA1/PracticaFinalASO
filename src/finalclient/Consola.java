package finalclient;
import java.io.*;
import java.util.Scanner;

public class Consola {
    
    /* ----------------------------- ATRIBUTOS ----------------------------- */
    public static String prompt = "Cliente Test v " + Main.version + "> ";
    private InputStreamReader isr;
    private BufferedReader br;

    /* ---------------------------- CONSTRUCTOR ---------------------------- */
    public Consola(){
        this.isr = new InputStreamReader(System.in);
        this.br = new BufferedReader(isr);
    }

    
    /* ----------------------------- METODOS ------------------------------- */
    /* Método que saca por la pantalla del cliente los mensajes */
    public void writeMessage(String msg){
        System.out.println( "> " + msg );
    }

    //--------------------------------------------------------------------------
    
    /* Método que recoge las peticiones que hace el usuario */
    public String getCommand(){
        String line = null;
        try {
            System.out.print(Consola.prompt);
            line = this.br.readLine();
           
        } catch (IOException ex) {}
        return line;
    }

    //--------------------------------------------------------------------------

    /* Método que recoge el login y password del usuario cuando se está logueando en el sistema*/
    public String[] getCommandLOGIN(){
        String[] credenciales = new String[2];
        try {
            System.out.print("Login: ");
            credenciales[0] = this.br.readLine();
            System.out.print("Password: ");
            credenciales[1] = this.br.readLine();
        } catch (IOException ex) {
        }
        return credenciales;
    }
    
    //--------------------------------------------------------------------------
    
    /* Método que recoge el nombre de usuario y password de un usuario que está registrandose en el sistema*/
    public String[] getCommandRegister(){
        String[] credenciales = new String[2];
        try {
            System.out.print("Username: ");
            credenciales[0] = this.br.readLine();
            System.out.print("Password: ");
            credenciales[1] = this.br.readLine();
        } catch (IOException ex) {}
        return credenciales;
    }
    

}

