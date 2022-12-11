package compilador;

/**
 *
 * @author fajri
 */
public class NodoCuadruplo {
    
    String op, op1, op2, resultado;
    String[] apuntadores;
    NodoCuadruplo sig;

    public NodoCuadruplo(String op, String op1, String op2, String resultado, String[] apuntadores) {
        this.op = op;
        this.op1 = op1;
        this.op2 = op2;
        this.resultado = resultado;
        this.apuntadores = apuntadores;
    }
}
