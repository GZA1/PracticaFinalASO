package finalclient;

import protocol.PeticionControl;
import protocol.PeticionDatos;
import protocol.RespuestaControl;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    /* ----------------------------- ATRIBUTOS ----------------------------- */
    public static final String version = "1.0";
    private Consola console;
    private Socket s;
    private ObjectOutputStream os;
    private ObjectInputStream is;
   

    private String username;
    private String password;
    

    /* ---------------------------- CONSTRUCTOR ---------------------------- */
        
    /* Método principal: en función de la solicitud que haga el usuario deriva a otros métodos. */
    public Main() {
        
        this.init();
        String cmd = this.console.getCommand();
        while( cmd.compareTo("close")!=0 ) {
            if( cmd.compareTo("login")==0 ) {
                String[] credenciales = this.console.getCommandLOGIN();
                System.out.println( credenciales[0] + " " + credenciales[1] );
                this.doLogin(credenciales);
            }else if(cmd.compareTo("logout") == 0) {
                this.doDisconnect();
            }else if(cmd.compareTo("registro")== 0){
                String[] credenciales = this.console.getCommandRegister();
                System.out.println( credenciales[0] + " " + credenciales[1] );
                this.doConnect();
                this.doResgister(credenciales);
            }else if(cmd.compareTo("run") == 0) {
                
            }else{
                System.out.println("OPCIÓN NO VÁLIDA");
            }            
            cmd = this.console.getCommand();
        }
        if( this.s!=null )
            this.doDisconnect();
        this.console.writeMessage("Saliendo de la aplicacion");
    }

    /* ----------------------------- METODOS ------------------------------- */
    
     public static void main(String[] args) {
        new Main();
    }

    //--------------------------------------------------------------------------

    /* Método de inicialización del cliente: comprueba si existe el fichero de log y sino lo crea. 
        Ejecuta la sincronización con el proxy */
    private void init() {
        this.console = new Consola();
        
        
    }
    
    //--------------------------------------------------------------------------

    /* Método que utilizamos para conectarnos al proxy */
    
    private void doConnect() {
        try {
            if(this.s == null){
                this.s = new Socket("localhost", 3339);
                this.os = new ObjectOutputStream( s.getOutputStream() );
                this.is = new ObjectInputStream( s.getInputStream() );
            }
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }        
    }
    
    //--------------------------------------------------------------------------

    
    private void doConnect(int port) {
        try {
            if(this.s == null){
                this.s = new Socket("localhost", port);
                this.os = new ObjectOutputStream( s.getOutputStream() );
                this.is = new ObjectInputStream( s.getInputStream() );
            }
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }        
    }
    //--------------------------------------------------------------------------

    /* Método que nos desconecta de los servidores */
    private void doDisconnect() {
        if (this.s!=null){
            try {
                PeticionControl p = new PeticionControl("OP_LOGOUT");
                
                String log="PETICION=LOGOUT, USERNAME="+username+" ";
                
            
                this.os.writeObject(p);
                this.is.close();
                this.is = null;
                this.os.close();
                this.os = null;
                this.s.close();
                this.s = null;
            } catch (IOException ex) {
            }
        }else{
            System.out.println("Ya estabas desconectado");
        }
    }
    
    //--------------------------------------------------------------------------
    
    /*  Método que utilizamos para loguearnos en el sistema
        Le enviamos la peticion OP_LOGIN junto con el nombre de usuario y la contraseña. 
        Podemos recibir 3 respuestas diferentes:
            - OP_LOGIN_OK: login correcto
            - OP_LOGIN_BAD_PASSWORD: contraseña incorrecta
            - OP_LOGIN_NO_USER: usuario no registrado.
        Si el login es correcto, guardamos el nombre de usuario y la contraseña
        en las variables username y password. 
    */
    
    private void doLogin(String[] credenciales) {
        try {
            this.doConnect();
            PeticionControl p = new PeticionControl("OP_LOGIN");
            p.getArgs().add(credenciales[0]);
            p.getArgs().add(credenciales[1]);
            
            String log="PETICION=LOGIN, USERNAME="+credenciales[0]+" ";
            
            
            this.os.writeObject(p);

            RespuestaControl rc = (RespuestaControl)this.is.readObject();
            Long Tfin=System.nanoTime();
            if( rc.getSubtipo().compareTo("OP_LOGIN_OK")==0 ) {
                log="RESPUESTA=LOGIN_OK, USERNAME="+credenciales[0]+" ";
                
                this.console.writeMessage("Login correcto");
                if (username==null && password==null){
                    username = credenciales[0];
                    password = credenciales[1];
                }
            }
            else if(rc.getSubtipo().compareTo("OP_LOGIN_BAD_PASSWORD") == 0) {
                log="RESPUESTA=LOGIN_BAD_PASSWORD, USERNAME="+credenciales[0]+" ";
                
                this.console.writeMessage("La contraseña especificada no es correcta");
                this.doDisconnect();
            }
            else if(rc.getSubtipo().compareTo("OP_LOGIN_NO_USER") == 0) {
                log="RESPUESTA=LOGIN_NO_USER, USERNAME="+credenciales[0]+" ";
                
                this.console.writeMessage("El usuario no existe");
                this.doDisconnect();
            }
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
    //--------------------------------------------------------------------------
    
    /*  Método que utilizamos para registrarnos en el sistema
        Le enviamos la peticion OP_REGISTRO junto con el nombre de usuario y la contraseña. 
        Podemos recibir 2 respuestas diferentes:
            - OP_REGISTRO_OK: registro correcto
            - OP_REGISTRO_INCORRECTO: usuario ya registrado.   
    */
    
    private void doResgister(String[] credenciales) {
         try {
            PeticionControl p = new PeticionControl("OP_REGISTRO");
            p.getArgs().add(credenciales[0]);
            p.getArgs().add(credenciales[1]);

            String log="PETICION=REGISTER, USERNAME="+credenciales[0]+" ";
            
             
            this.os.writeObject(p);

            RespuestaControl rc = (RespuestaControl)this.is.readObject();
            if( rc.getSubtipo().compareTo("OP_REGISTRO_INCORRECTO")==0 ) {
                log="RESPUESTA=REGISTRO_INCORRECTO, USERNAME="+credenciales[0]+" ";
                this.console.writeMessage("Ya existe un usuario con ese nombre");
                this.doDisconnect();
            }
            else if(rc.getSubtipo().compareTo("OP_REGISTRO_OK") == 0) {
                log="RESPUESTA=REGISTRO_OK, USERNAME="+credenciales[0]+" ";
                this.console.writeMessage("Registro Correcto");
                this.doDisconnect();
            }     
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
   
    //--------------------------------------------------------------------------

 

}
