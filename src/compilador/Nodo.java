package compilador;

/**
 *
 * @author fajri
 */
public class Nodo {

    String lexema;
    int token, renglon;
    Nodo sig = null;

    public Nodo(String lexema, int token, int renglon) {
        this.lexema = lexema;
        this.token = token;
        this.renglon = renglon;
    }
}
