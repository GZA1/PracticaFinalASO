package protocol;


import java.util.ArrayList;


public class RespuestaDatos extends Respuesta {
   
    /* ----------------------------- ATRIBUTOS ----------------------------- */
    private ArrayList args;

    
    /* ---------------------------- CONSTRUCTOR ---------------------------- */
    
    public RespuestaDatos()
    {
        this.tipo = "RESPUESTA_CONTROL";
        this.args = new ArrayList();
    }

    //--------------------------------------------------------------------------

    /* Utilizamos este constructor si queremos darle un subtipo a la respuesta */
    public RespuestaDatos(String subtipo)
    {
        this.tipo = "RESPUESTA_CONTROL";
        this.subtipo = subtipo;
        this.args = new ArrayList();
    }

    /* ----------------------------- METODOS ------------------------------- */

    /* Método para obtener los argumentos de la respuesta */
    public ArrayList getArgs() {
        return args;
    }

    //--------------------------------------------------------------------------

    /* Método para establecer los argumentos de la respuesta */
    public void setArgs(ArrayList args) {
        this.args = args;
    }  
}

