package centralserver;

import protocol.PeticionControl;
import protocol.RespuestaControl;
import protocol.Peticion;
import java.io.*;
import java.net.*;
import java.util.ArrayList;



public class Main {
   
    /* ----------------------------- ATRIBUTOS ----------------------------- */
    private ServerSocket s;
    private int puerto = 3339;
    private int numberofusers=4;// NUMERO REDES QUE SE CONECTAN AL SERVIDOR CENTRAL
    
    
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public int clientesActivos;
   

    /* ---------------------------- CONSTRUCTOR ---------------------------- */
    
    public  Main() {
        System.out.println("Arrancando el servidor...");
        Socket sServicio=null;
        this.init();
        this.clientesActivos=0;
        System.out.println("Abriendo canal de comunicaciones...");
        try {
            this.s = new ServerSocket(puerto,numberofusers);
            Thread hilo;
            int i=0;
            while( true ) {
                String cadena1=  "hilo"+ i;
                sServicio = s.accept();
                System.out.println( "Aceptada conexion de " + sServicio.getInetAddress().toString() );
                HiloCliente hc = new HiloCliente(sServicio);                
                hilo= new Thread (hc, cadena1);
                hilo.start();
                System.out.println("Número de clientes: "+ this.clientesActivos);
                i=i+1;
            }
        } catch (IOException ex) {
        } finally{
            try{
                sServicio.close();
                s.close();
            }catch(IOException ex){
                
            }
        }
    }

    
    /* ----------------------------- METODOS ------------------------------- */

     public static void main(String[] args) {
        new Main();
    }
     
    //--------------------------------------------------------------------------
    
     /* Método de inicialización del servidor: Comprueba si existe el fichero de log.
        En caso contrario lo crea vacío
        Carga el número de redes soportadas y el puerto del servidor de fichero.
     */ 
    public void init() {
        this.numberofusers = 0;
        //Completar código
    }

    //--------------------------------------------------------------------------

    /* Método encargado de gestionar las peticiones que le llegan. Se debe implementar en el PROXY */
    
    public void procesaCliente (Socket sServicio) {
        try {
            
            this.is = new ObjectInputStream(sServicio.getInputStream());
            this.os = new ObjectOutputStream(sServicio.getOutputStream());

            boolean end = false;

            Peticion p = (Peticion)this.is.readObject();
            while( !end )
            {
                if( p.getTipo().compareTo("PETICION_CONTROL")==0 ) {
                    PeticionControl pc = (PeticionControl)p;
                    if( pc.getSubtipo().compareTo("OP_LOGIN")==0 )
                        //this.doLogin(pc);
                    if( pc.getSubtipo().compareTo("OP_LOGOUT")==0 ) {
                        //this.doLogout();
                        end = true;
                    }                    
                    if( pc.getSubtipo().compareTo("OP_INICIAR")==0 ){
                        //COMPLETAR
                    }                    
                }
                p = (Peticion)this.is.readObject();
            }
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                os.close();
                is.close();
                sServicio.close();
            } catch (IOException ex) {
            }
        }
    }

     
    //--------------------------------------------------------------------------
    /* Método Implementa el hilo cliente que atenderá peticiones*/ 
    public class HiloCliente implements Runnable{
        Usuarios usuarios=new Usuarios();
        private Socket socketCliente;
        private DataInput inBufferCliente;
        private DataOutput outBufferCliente;
        
        //Constructor para generar el flujo de entrada/salida del canal de comunicación
        public HiloCliente (Socket socketCliente){
            this.socketCliente=socketCliente;
        }
        @Override
        public void run(){
            try{
                inBufferCliente= new DataInputStream(socketCliente.getInputStream());
                outBufferCliente= new DataOutputStream(socketCliente.getOutputStream());
                synchronized(this){
                    this.usuarios.setUsuarios(this.usuarios.getUsuarios()+1);                
                    System.out.println("Número de clientes en el hilo: "+ usuarios.getUsuarios());
                }
                //procesa cliente: evita ataques externos
                //centraliza el mensaje: filtra mensajes duplicados...
            }catch(IOException ex){
                
            }
            
        }
    }
}

