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
                sintaxis.nodoPolish = sintaxis.cabezaPolish;
                while (sintaxis.nodoPolish != null) {
                    System.out.print(sintaxis.nodoPolish.lexema + " ");
                    sintaxis.nodoPolish = sintaxis.nodoPolish.sig;
                }
            }
        }
    }
    
}
