package compilador;

import java.util.LinkedList;

/**
 *
 * @author fajri
 */
public class Cuadruplos {
    
    NodoPolish nPolish;
    NodoCuadruplo cabeza, nCuadruplo;
    String[] operadores = {"write", "read", "*", "/", "+", "-", "<", ">" , "<=", ">=", "==", ":="};
    int iteradorResultado = 1;
    LinkedList operandos = new LinkedList();
    LinkedList<String[]> apuntadores = new LinkedList<>();

    public Cuadruplos(NodoPolish cabeza) {
        this.nPolish = cabeza;
        
        while (nPolish != null) {
            String op1, op2, resultado;
            NodoCuadruplo nodo;
            int brinco = verificarBrinco();
            if (brinco != 0) {
                String[] detallesBrinco = nPolish.lexema.split("-");
                String[] insertarApuntadores = null;
                if (!apuntadores.isEmpty()) {
                    insertarApuntadores = apuntadores.pop();
                }
                if (brinco == 1) {
                    nodo = new NodoCuadruplo(detallesBrinco[0], "", "", detallesBrinco[1], insertarApuntadores);
                } else {
                    op1 = operandos.pop().toString();
                    nodo = new NodoCuadruplo(detallesBrinco[0], op1, "", detallesBrinco[1], insertarApuntadores);
                }
                nCuadruplo.sig = nodo;
                nCuadruplo = nodo;
            } else if (verificarOperador()) {
                int modo = verificarUnitario();
                if (modo != 0) {
                    if (modo == 1) {
                        op1 = operandos.pop().toString();
                        resultado = operandos.pop().toString();
                        insertarNodo(op1, "", resultado);
                    } else if (modo == 2) {
                        op1 = operandos.pop().toString();
                        insertarNodo(op1, "", "");
                    } else {
                        resultado = operandos.pop().toString();
                        insertarNodo("", "", resultado);
                    }
                } else {
                    op2 = operandos.pop().toString();
                    op1 = operandos.pop().toString();
                    resultado = "T" + iteradorResultado;
                    operandos.push(resultado);
                    iteradorResultado++;
                    insertarNodo(op1, op2, resultado);
                }
            } else {
                if (nPolish.brinco != null) {
                    apuntadores.push(nPolish.brinco);
                }
                operandos.push(nPolish.lexema);
            }
            
            nPolish = nPolish.sig;
        }
    }
    
    private boolean verificarOperador () {
        for (String operador : operadores) {
            if (nPolish.lexema.equals(operador)) {
                return true;
            }
        }
        return false;
    }
    
    private int verificarUnitario () {
        switch (nPolish.lexema) {
            case ":=":
                return 1;
                
            case "write":
                return 2;
                
            case "read":
                return 3;
                
            default:
                return 0;
        }
    }
    
    private int verificarBrinco () {
        if (nPolish.lexema.startsWith("BRI")) {
            return 1;
        } else if (nPolish.lexema.startsWith("BRF")) {
            return 2;
        }
        return 0;
    }
    
    private void insertarNodo(String op1, String op2, String resultado) {
        String[] insertarApuntadores = null;
        if (!apuntadores.isEmpty()) {
            insertarApuntadores = apuntadores.pop();
        }
        NodoCuadruplo nodo = new NodoCuadruplo(nPolish.lexema, op1, op2, resultado, insertarApuntadores);

        if (cabeza == null) {
            cabeza = nodo;
            nCuadruplo = cabeza;
        } else {
            nCuadruplo.sig = nodo;
            nCuadruplo = nodo;
        }
    }
}
