package compilador;

/**
 *
 * @author fajri
 */
public class NodoPolish {
    
    String lexema;
    String[] brinco;
    NodoPolish sig;

    public NodoPolish(String lexema, String[] brinco) {
        this.lexema = lexema;
        this.brinco = brinco;
    }
}
