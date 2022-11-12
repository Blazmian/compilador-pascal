package compilador;

import java.io.RandomAccessFile;

/**
 *
 * @author fajri
 */
class Lexico {

    Nodo cabeza = null, p;
    int estado = 0, columna, valorMT, numRenglon = 1, caracter = 0;
    String lexema = "";
    boolean errorEncontrado = false;
    RandomAccessFile file = null;

    String archivo = getClass().getResource("/compilador/Compilar.txt").toString().replace("file:", "");

    int matriz[][] = {
        //   1    d    +    -    *    /    >    <    =    !    &    |    .    ,    ;    ?    #    $    "    :   eb   rt   nl  tab  eof   oc    (    )
        //   0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18   19   20   21   22   23   24   25   26   27
        /*0*/{1, 2, 103, 104, 105, 5, 8, 9, 10, 11, 12, 13, 116, 117, 118, 119, 126, 127, 14, 15, 0, 0, 0, 0, 0, 506, 132, 133},
        /*1*/ {1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100},
        /*2*/ {101, 2, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 3, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101},
        /*3*/ {500, 4, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500},
        /*4*/ {102, 4, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102},
        /*5*/ {106, 106, 106, 106, 6, 16, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106},
        /*6*/ {6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 501, 501, 6, 6},
        /*7*/ {6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 501, 501, 6, 6},
        /*8*/ {107, 107, 107, 107, 107, 107, 107, 107, 110, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107, 107},
        /*9*/ {108, 108, 108, 108, 108, 108, 134, 108, 111, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108},
        /*10*/ {502, 502, 502, 502, 502, 502, 502, 502, 109, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502},
        /*11*/ {115, 115, 115, 115, 115, 115, 115, 115, 112, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115},
        /*12*/ {503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 113, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503},
        /*13*/ {504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 114, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504, 504},
        /*14*/ {14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 128, 14, 14, 507, 507, 14, 501, 14, 14, 14},
        /*15*/ {131, 131, 131, 131, 131, 131, 131, 131, 130, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131, 131},
        /*16*/ {16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0, 16, 16, 16, 16, 16}
    };

    String palabrasReservadas[][] = {
        //   0            1
        /*0*/{"write", "201"},
        /*1*/ {"while", "202"},
        /*2*/ {"do", "203"},
        /*3*/ {"program", "204"},
        /*4*/ {"var", "205"},
        /*5*/ {"int", "206"},
        /*6*/ {"real", "207"},
        /*7*/ {"string", "208"},
        /*8*/ {"false", "209"},
        /*9*/ {"true", "210"},
        /*10*/ {"if", "211"},
        /*11*/ {"then", "212"},
        /*12*/ {"else", "213"},
        /*13*/ {"begin", "214"},
        /*14*/ {"end", "215"},
        /*15*/ {"print", "216"},
        /*16*/ {"boolean", "217"}   
    };

    String errores[][] = {
        //           0                            1
        /*0*/{"Se espera digito", "500"},
        /*1*/ {"eof inesperado", "501"},
        /*2*/ {"Se espera '='", "502"},
        /*3*/ {"Se espera '&'", "503"},
        /*4*/ {"Se espera '|'", "504"},
        /*5*/ {"eol inesperado", "505"},
        /*6*/ {"Simbolo no valido", "506"},
        /*7*/ {"Se espera cierre de la cadena", "507"},
        /*8*/ {"No se encuentra la entrada de (", "508" }
    };

    public Lexico() {
        try {
            file = new RandomAccessFile(archivo, "r");
            while (caracter != -1) {
                caracter = file.read();

                if (Character.isLetter((char) caracter)) {
                    columna = 0;
                } else if (Character.isDigit((char) caracter)) {
                    columna = 1;
                } else {
                    switch ((char) caracter) {
                        case '+':
                            columna = 2;
                            break;
                        case '-':
                            columna = 3;
                            break;
                        case '*':
                            columna = 4;
                            break;
                        case '/':
                            columna = 5;
                            break;
                        case '>':
                            columna = 6;
                            break;
                        case '<':
                            columna = 7;
                            break;
                        case '=':
                            columna = 8;
                            break;
                        case '!':
                            columna = 9;
                            break;
                        case '&':
                            columna = 10;
                            break;
                        case '|':
                            columna = 11;
                            break;
                        case '.':
                            columna = 12;
                            break;
                        case ',':
                            columna = 13;
                            break;
                        case ';':
                            columna = 14;
                            break;
                        case '?':
                            columna = 15;
                            break;
                        case '#':
                            columna = 16;
                            break;
                        case '$':
                            columna = 17;
                            break;
                        case '"':
                            columna = 18;
                            break;
                        case ':':
                            columna = 19;
                            break;
                        case ' ':
                            columna = 20;
                            break;
                        case '(':
                            columna = 26;
                            break; 
                        case ')':
                            columna = 27;
                            break;
                        case 10: {
                            columna = 22;
                            numRenglon++;
                        }
                        ; // Nueva linea
                        break;
                        case 13:
                            columna = 21; // Retorno de carro
                            break;
                        case 9:
                            columna = 23; // Tabulador horizontal
                            break;
                        default:
                            columna = 25;
                            break;
                    }
                }

                valorMT = matriz[estado][columna];

                if (valorMT < 100) {
                    estado = valorMT;

                    if (estado == 0) {
                        lexema = "";
                    } else {
                        lexema += (char) caracter;
                    }
                } else if (valorMT >= 100 && valorMT < 500) {
                    if (valorMT == 100) {
                        validarSiEsPalabraReservada();
                    }

                    if (valorMT == 100 || valorMT == 101 || valorMT == 102
                            || valorMT == 106 || valorMT == 107 || valorMT == 108
                            || valorMT == 115 || valorMT == 131 || valorMT >= 200) {
                        file.seek(file.getFilePointer() - 1);
                    } else {
                        lexema += (char) caracter;
                    }

                    insertarNodo();
                    estado = 0;
                    lexema = "";

                } else {
                    imprimirMensajeError();
                    break;
                }
            }
            imprimirNodos();
            p = cabeza;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void insertarNodo() {
        Nodo nodo = new Nodo(lexema, valorMT, numRenglon);

        if (cabeza == null) {
            cabeza = nodo;
            p = cabeza;
        } else {
            p.sig = nodo;
            p = nodo;
        }
    }

    private void imprimirNodos() {
        p = cabeza;
        while (p != null) {
            System.out.println(p.lexema + " " + p.token + " " + p.renglon);
            p = p.sig;
        }
    }

    private void validarSiEsPalabraReservada() {
        for (String[] palReservada : palabrasReservadas) {
            if (lexema.equals(palReservada[0])) {
                valorMT = Integer.valueOf(palReservada[1]);
            }
        }
    }

    private void imprimirMensajeError() {
        if (caracter != -1 && valorMT >= 500) {
            for (String[] error : errores) {
                if (valorMT == Integer.valueOf(error[1])) {
                    System.out.println("El error encontrado es: " + error[0]
                            + " error " + valorMT + " caracter " + caracter
                            + " en el renglon " + numRenglon);
                }
            }
            errorEncontrado = true;
        }
    }
}
