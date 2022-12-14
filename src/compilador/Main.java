package compilador;

/**
 *
 * @author fajri
 */
public class Main {

    Nodo p;
    
    public static void main(String[] args) {
        Lexico lexico = new Lexico();
        
        if(!lexico.errorEncontrado) {
            System.out.println("Analisis lexico terminado.");
            Sintaxis sintaxis = new Sintaxis(lexico.p);
            if (!sintaxis.errorEncontrado) {
                System.out.println("Analisis sintactico terminado.");
                NodoPolish nPolish = sintaxis.cabezaPolish;
                System.out.println("\nLISTA DE POLISH\n");
                while (nPolish != null) {
                    System.out.println("   LEXEMA: " + nPolish.lexema);
                    System.out.print("APUNTADOR: ");
                    String[] apuntadores = nPolish.brinco;
                    if (apuntadores != null) {
                        for (int i = 0; i < apuntadores.length; i++) {
                            System.out.print(apuntadores[i]);
                            if ((i + 1) < apuntadores.length) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println("\n");
                    } else {
                        System.out.println("\n");
                    }
                    nPolish = nPolish.sig;
                }
                
                Cuadruplos cuadruplo = new Cuadruplos(sintaxis.cabezaPolish);
                System.out.println("\nCUADRUPLOS\n");
                NodoCuadruplo nCuadruplo = cuadruplo.cabeza;
                
                while (nCuadruplo != null) {
                    System.out.println("  OPERADOR: " + nCuadruplo.op);
                    System.out.println("OPERANDO 1: " + nCuadruplo.op1);
                    System.out.println("OPERANDO 2: " + nCuadruplo.op2);
                    System.out.println(" RESULTADO: " + nCuadruplo.resultado);
                    System.out.print(" APUNTADOR: ");
                    String[] apuntadores = nCuadruplo.apuntadores;
                    if (apuntadores != null) {
                        for (int i = 0; i < apuntadores.length; i++) {
                            System.out.print(apuntadores[i]);
                            if ((i + 1) < apuntadores.length) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println("\n");
                    } else {
                        System.out.println("\n");
                    }
                    nCuadruplo = nCuadruplo.sig;
                }
            }
        }
    }
    
}
