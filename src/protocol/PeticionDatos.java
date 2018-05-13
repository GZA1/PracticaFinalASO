package protocol;

import java.util.ArrayList;


public class PeticionDatos extends Peticion {
    
    /* ----------------------------- ATRIBUTOS ----------------------------- */
    private ArrayList args;

    
    /* ---------------------------- CONSTRUCTOR ---------------------------- */
    
    public PeticionDatos()
    {
        this.tipo = "PETICION_DATOS";
        this.args = new ArrayList();
    }

    //--------------------------------------------------------------------------

    /* Utilizamos este constructor si queremos darle un subtipo a la petición */
    public PeticionDatos(String subtipo)
    {
        this.tipo = "PETICION_DATOS";
        this.subtipo = subtipo;
        this.args = new ArrayList();
    }

    /* ----------------------------- METODOS ------------------------------- */

    /* Método para obtener los argumentos de la petición */
    public ArrayList getArgs() {
        return args;
    }

    //--------------------------------------------------------------------------
    
    /* Método para establecer los argumentos de la petición */
    public void setArgs(ArrayList args) {
        this.args = args;
    }  
}