package compilador;

/**
 *
 * @author fajri
 */
public class NodoVar {
    
    String lexema;
    int tipo;
    NodoVar sig = null;

    public NodoVar(String lexema, int tipo) {
        this.lexema = lexema;
        this.tipo = tipo;
    }
}
