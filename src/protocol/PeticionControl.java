package protocol;

import java.util.ArrayList;

public class PeticionControl extends Peticion {

    /* ----------------------------- ATRIBUTOS ----------------------------- */
    private ArrayList args;


    /* ---------------------------- CONSTRUCTOR ---------------------------- */
    
    public PeticionControl()
    {
        this.tipo = "PETICION_CONTROL";
        this.args = new ArrayList();
    }

    //--------------------------------------------------------------------------

    /* Utilizamos este constructor si queremos darle un subtipo a la petición */
    public PeticionControl(String subtipo)
    {
        this.tipo = "PETICION_CONTROL";
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
