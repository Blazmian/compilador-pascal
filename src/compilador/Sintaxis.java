package compilador;

import java.util.LinkedList;

/**
 *
 * @author fajri
 */
class Sintaxis {

    Nodo p;
    NodoVar cabeza = null, nodoV;
    NodoPolish cabezaPolish = null, nodoPolish;
    boolean errorEncontrado = false;
    int brincoIfFalso = 1, brincoIfIncondicional = 1,
            brincoWhileFalso = 1, brincoWhileIncondicional = 1;
    LinkedList apuntadoresEntrantes = new LinkedList();
    
    String erroresSintacticos[][] = {
        {"Se espera la palabra program",                            "505"},
        {"Se espera un identificador",                              "506"},
        {"Se espera un ;",                                          "507"},
        {"Se espera un .",                                          "508"},
        {"Se esperaba un tipo de variable",                         "509"},
        {"Se espera la palabra Var",                                "510"},
        {"Se espera la palabra begin",                              "511"},
        {"Se esperan los :",                                        "512"},
        {"se espera un end",                                        "513"},
        {"se esperaba asignacion (:=)",                             "514"},
        {"se esperaba operador relacional",                         "515"},
        {"se esperaba un (",                                        "516"},
        {"se esperaba un )",                                        "517"},
        {"se esperaba un then",                                     "518"},
        {"se esperaba un do",                                       "519"},
        {"se esperaba un . despues del end",                        "520"},
        {"un term no puede ser parte de la expresion relacional",   "521"},
        {"se esperaba un read o write statement",                   "522"},
        {"se espera una variable",                                  "523"},
        {"Variables duplicadas",                                    "524"},
        {"Variable No Declarada",                                   "525"},
        {"Incopatibilidad de Tipos",                                "526"},
        {"Mala Sintaxis de la expresion",                           "527"}};


    /*      --SISTEMA DE TIPOS--      */
    // -Operacionales
    int[][] suma = {
        //               Int Real String Boolean
        //               206  207    208     217
        /*    Int 206*/ {206, 207,  208,     -1},
        /*   Real 207*/ {207, 207,  208,     -1},
        /* String 208*/ {208, 208,  208,     -1},
        /*Boolean 217*/ { -1,  -1,   -1,     -1}
    };

    int[][] restaMultiplicacion = {
        //               Int Real String Boolean
        //               206  207    208     217
        /*    Int 206*/ {206, 207,   -1,     -1},
        /*   Real 207*/ {207, 207,   -1,     -1},
        /* String 208*/ { -1,  -1,  208,     -1},
        /*Boolean 217*/ { -1,  -1,   -1,     -1}
    };

    int[][] division = {
        //               Int Real String Boolean
        //               206  207    208     217
        /*    Int 206*/ {207, 207,   -1,     -1},
        /*   Real 207*/ {207, 207,   -1,     -1},
        /* String 208*/ { -1,  -1,   -1,     -1},
        /*Boolean 217*/ { -1,  -1,   -1,     -1}
    };

    //  -Relacionales
    int[][] menorMayorYOIgualQue = {
        //               Int Real String Boolean
        //               206  207    208     217
        /*    Int 206*/ {217, 217,   -1,     -1},
        /*   Real 207*/ {217, 217,   -1,     -1},
        /* String 208*/ { -1,  -1,   -1,     -1},
        /*Boolean 217*/ { -1,  -1,   -1,     -1}
    };

    int[][] igualODiferente = {
        //               Int Real String Boolean
        //               206  207    208     217
        /*    Int 206*/ {217, 217,   -1,     -1},
        /*   Real 207*/ {217, 217,   -1,     -1},
        /* String 208*/ { -1,  -1,  217,     -1},
        /*Boolean 217*/ { -1,  -1,   -1,    217}
    };

    // -Asignacion
    int[][] asignacion = {
        //               Int Real String Boolean
        //               206  207    208     217
        /*    Int 206*/ {206,  -1,   -1,     -1},
        /*   Real 207*/ { -1, 207,   -1,     -1},
        /* String 208*/ { -1,  -1,  208,     -1},
        /*Boolean 217*/ { -1,  -1,   -1,    217}
    };

    public Sintaxis(Nodo p) {
        this.p = p;
        while (this.p != null) {
            verificarInicio();
            if (errorEncontrado) {
                break;
            }
            verificarDeclaracionVariables();
            if (errorEncontrado) {
                break;
            }
            verificarBloque();
            if (errorEncontrado) {
                break;
            }
            this.p = this.p.sig;
            if (this.p.token != 116) {
                imprimirErrores(508);
            }
            break;
        }
    }

    private void verificarInicio() {
        if (p.token == 204) {
            p = p.sig;
            if (p.token == 100) {
                p = p.sig;
                if (p.token == 118) {
                    p = p.sig;
                } else {
                    imprimirErrores(507);
                }
            } else {
                imprimirErrores(506);
            }
        } else {
            imprimirErrores(505);
        }
    }

    private void verificarDeclaracionVariables() {
        // Pila auxiliar para introducir las variables
        LinkedList pilaAux = new LinkedList();
        if (p.token == 205) {
            p = p.sig;
            if (p.token != 214) { // VERIFICA SI TIENE VARIABLES DECLARADAS
                while (true) {
                    if (p.token == 100) {
                        // Introducimos a la pila
                        pilaAux.push(p.lexema);
                        p = p.sig;
                        switch (p.token) {
                            case 117: // ,
                                p = p.sig;
                                break;
                            case 131: // :
                                p = p.sig;
                                if (p.token >= 206 && p.token <= 208) { // Verifica si tiene un tipo de variable
                                    // Se verifica para meter en la lista de variables
                                    introducirVariablesLista(pilaAux, p.token);
                                    p = p.sig;
                                    if (errorEncontrado) {
                                        return;
                                    }
                                    if (p.token == 118) {
                                        p = p.sig;
                                        if (p.token == 214) {
                                            return;
                                        }
                                    } else {
                                        imprimirErrores(507);
                                        return;
                                    }
                                } else {
                                    imprimirErrores(509);
                                    return;
                                }
                                break;
                            default: // oc
                                imprimirErrores(512);
                                return;
                        }
                    } else {
                        imprimirErrores(523);
                        return;
                    }
                }
            }
        } else {
            imprimirErrores(510);
        }
    }

    private void introducirVariablesLista(LinkedList pila, int tipo) {
        while (!pila.isEmpty()) {
            String lexema = pila.pop().toString();
            NodoVar var = new NodoVar(lexema, tipo);
            if (cabeza == null) {
                cabeza = var;
                nodoV = cabeza;
            } else {
                NodoVar aux = cabeza;
                while (aux != null) {
                    if (aux.lexema.equals(lexema)) {
                        imprimirErrores(524);
                        return;
                    } else {
                        aux = aux.sig;
                    }
                }
                nodoV.sig = var;
                nodoV = var;
            }
        }
    }

    private void verificarBloque() {
        while (true) { // REPITE HASTA ENCONTRARSE CON END
            p = p.sig;
            switch (p.token) {
                case 100:
                    // ASIGNACION A VARIABLE
                    asignacionVariable();
                    break;

                case 201:
                    // WRITE
                    verificarWrite();
                    break;

                case 211:
                    // IF
                    verificarIf();
                    break;

                case 202:
                    // WHILE
                    verificarWhile();
                    break;
                    
                case 218:
                    verificarRead();
                    break;

                case 215:
                    return;
            }
            if (errorEncontrado) {
                break;
            }
        }
    }

    private void asignacionVariable() {
        Postfijo listaPostfijo = pasarAPostfijo(1);

        if (errorEncontrado) {
            return;
        }
        
        Postfijo aux = listaPostfijo;

        System.out.println("\nASIGNACION");
        while (aux != null) {
            System.out.print(aux.tipo + " ");
            aux = aux.sig;
        }
        System.out.println("");
        evaluarListaPostfijo(listaPostfijo);
    }

    private Postfijo pasarAPostfijo(int tipoCondicion) {
        Postfijo cabezaP = null;
        Postfijo nodoP = null;
        LinkedList pilaAux = new LinkedList(), pilaAuxPolish = new LinkedList();

        int stopToken = 0;
        switch (tipoCondicion) {
            // Asignacion de Variables
            case 1 ->
                stopToken = 118; // ;
            // If
            case 2 ->
                stopToken = 212; // then
            // While
            case 3 ->
                stopToken = 203; // do
        }
        
        String[] apuntadores = null;
        if (!apuntadoresEntrantes.isEmpty()) {
            apuntadores = new String[apuntadoresEntrantes.size()];
            for (int i = 0; i < apuntadores.length; i++) {
                apuntadores[i] = apuntadoresEntrantes.pop().toString();
            }
        }

        while (p.token != stopToken) {
            Postfijo nPostfijo = null;
            NodoPolish nPolish = null;
            switch (p.token) {
                // Variables    
                case 100:
                    int tipoVar = verificarExistenciaVar();
                    if (tipoVar != -1) {
                        nPostfijo = new Postfijo(tipoVar);
                        nPolish = new NodoPolish(p.lexema, apuntadores);
                        if (apuntadores != null) {
                            apuntadores = null;
                        }
                    } else {
                        return null;
                    }
                    break;

                // Numeros Enteros
                case 101:
                    nPostfijo = new Postfijo(206);
                    nPolish = new NodoPolish(p.lexema, apuntadores);
                    if (apuntadores != null) {
                            apuntadores = null;
                        }
                    break;

                // Numeros Reales
                case 102:
                    nPostfijo = new Postfijo(207);
                    nPolish = new NodoPolish(p.lexema, apuntadores);
                    if (apuntadores != null) {
                            apuntadores = null;
                        }
                    break;

                // Operador Suma
                case 103:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Resta
                case 104:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Multiplicacion    
                case 105:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Division    
                case 106:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Mayor Que    
                case 107:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Menor Que    
                case 108:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Igual Que    
                case 109:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Mayor Igual Que    
                case 110:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Menor Igual Que    
                case 111:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Operador Asignacion
                case 130:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                case 134:
                    nPostfijo = meterOperadores(pilaAux, nodoP, pilaAuxPolish);
                    if (nPostfijo != null) {
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                    }
                    continue;

                // Cadena de Caracteres
                case 128:
                    nPostfijo = new Postfijo(208);
                    nPolish = new NodoPolish(p.lexema, apuntadores);
                    if (apuntadores != null) {
                        apuntadores = null;
                    }
                    break;

                // True o False    
                case 209:
                case 210:
                    nPostfijo = new Postfijo(217);
                    nPolish = new NodoPolish(p.lexema, apuntadores);
                    if (apuntadores != null) {
                        apuntadores = null;
                    }
                    break;

                default:
                    imprimirErrores(523);
                    return null;
            }
            if (cabezaP == null) {
                cabezaP = nPostfijo;
                nodoP = cabezaP;
            } else {
                nodoP.sig = nPostfijo;
                nodoP = nPostfijo;
            }
            
            if (cabezaPolish == null) {
                cabezaPolish = nPolish;
                nodoPolish = cabezaPolish;
            } else {
                nodoPolish.sig = nPolish;
                nodoPolish = nPolish;
            }

            p = p.sig;
        }
        while (!pilaAux.isEmpty()) {
            Postfijo nodoAux = new Postfijo((int) pilaAux.pop());
            nodoP.sig = nodoAux;
            nodoP = nodoAux;
        }
        
        while (!pilaAuxPolish.isEmpty()) {
            NodoPolish nPolish = new NodoPolish(pilaAuxPolish.pop().toString(), null);
            nodoPolish.sig = nPolish;
            nodoPolish = nPolish;
        }
        return cabezaP;
    }

    private Postfijo meterOperadores(LinkedList pilaAux, Postfijo nodoP, LinkedList pilaAuxPolish) {
        Postfijo nPostfijo;
        NodoPolish nPolish;
        if (pilaAux.isEmpty()) {
            pilaAux.push(p.token);
            pilaAuxPolish.push(p.lexema);
            p = p.sig;
            return null;
        } else {
            int tokOp = (int) pilaAux.getFirst();
            int prioridad = verificarPrioridadOperadores(tokOp, p.token);
            switch (prioridad) {
                case 1:
                    while (true) {
                        nPostfijo = new Postfijo((int) pilaAux.pop());
                        nPolish = new NodoPolish(pilaAuxPolish.pop().toString(), null);
                        nodoP.sig = nPostfijo;
                        nodoP = nPostfijo;
                        nodoPolish.sig = nPolish;
                        nodoPolish = nPolish;
                        tokOp = (int) pilaAux.getFirst();
                        switch (verificarPrioridadOperadores(tokOp, p.token)) {
                            case 1:
                                continue;

                            case 2:
                                nPostfijo = new Postfijo((int) pilaAux.pop());
                                nPolish = new NodoPolish(pilaAuxPolish.pop().toString(), null);
                                nodoP.sig = nPostfijo;
                                nodoP = nPostfijo;
                                nodoPolish.sig = nPolish;
                                nodoPolish = nPolish;
                                pilaAux.push(p.token);
                                pilaAuxPolish.push(p.lexema);
                                p = p.sig;
                                break;

                            case 3:
                                pilaAux.push(p.token);
                                pilaAuxPolish.push(p.lexema);
                                p = p.sig;
                                break;
                        }
                        break;
                    }
                    break;

                case 2:
                    nPostfijo = new Postfijo((int) pilaAux.pop());
                    nPolish = new NodoPolish(pilaAuxPolish.pop().toString(), null);
                    nodoP.sig = nPostfijo;
                    nodoP = nPostfijo;
                    nodoPolish.sig = nPolish;
                    nodoPolish = nPolish;
                    pilaAux.push(p.token);
                    pilaAuxPolish.push(p.lexema);
                    p = p.sig;
                    break;

                case 3:
                    pilaAux.push(p.token);
                    pilaAuxPolish.push(p.lexema);
                    p = p.sig;
                    return null;
            }
        }
        return nodoP;
    }

    private int verificarPrioridadOperadores(int op1, int op2) {
        /**
         * Prioridad: 1. Menor Prioridad 2. Misma Prioridad 3. Mayor Prioridad
         */
        int prioridad = 0;

        // Operacionales '* y /'
        if (op1 == 105 || op1 == 106) {
            if (op2 == 105 || op2 == 106) {
                prioridad = 2;
            } else {
                prioridad = 1;
            }
            // Operacionales '+ y -'
        } else if (op1 == 103 || op1 == 104) {
            if (op2 == 105 || op2 == 106) {
                prioridad = 3;
            } else if (op2 == 103 || op2 == 104) {
                prioridad = 2;
            } else {
                prioridad = 1;
            }
            // Condicionales '< > <= >= == <>'    
        } else if ((op1 >= 107 && op1 <= 111) || op1 == 134) {
            if (op2 >= 103 && op2 <= 106) {
                prioridad = 3;
            } else if ((op2 >= 107 && op2 <= 111) || op2 == 134) {
                prioridad = 2;
            } else {
                prioridad = 1;
            }
            // Asignacion ':='
        } else if (op1 == 130) {
            if (op2 == 130) {
                prioridad = 2;
            } else {
                prioridad = 3;
            }
        }
        return prioridad;
    }
    
    private void verificarWrite() {
        NodoPolish nPolish;
        p = p.sig;
        if (p.token == 132) {
            p = p.sig;
            while (p.token != 133) {
                if (p.token == 100 || p.token == 128 || p.token == 101 || p.token == 102) {
                    // INSERTAMOS A LA LISTA DE POLISH
                    
                    String[] apuntadores = null;
                    if (!apuntadoresEntrantes.isEmpty()) {
                        apuntadores = new String[apuntadoresEntrantes.size()];
                        for (int i = 0; i < apuntadoresEntrantes.size(); i++) {
                            apuntadores[i] = apuntadoresEntrantes.pop().toString();
                        }
                    }
                    nPolish = new NodoPolish(p.lexema, apuntadores);
                    if (cabezaPolish == null) {
                        cabezaPolish = nPolish;
                        nodoPolish = cabezaPolish;
                    } else {
                        nodoPolish.sig = nPolish;
                        nodoPolish = nPolish;
                    }
                    p = p.sig;
                    if (p.token == 117) {
                        // INSERTAMOS WRITE
                        nPolish = new NodoPolish("write", null);
                        nodoPolish.sig = nPolish;
                        nodoPolish = nPolish;
                        p = p.sig;
                    }
                } else {
                    imprimirErrores(523);
                    return;
                }
            }
            // INSERTAMOS WRITE
            // INSERTAMOS WRITE
            nPolish = new NodoPolish("write", null);
            nodoPolish.sig = nPolish;
            nodoPolish = nPolish;
            p = p.sig;
            if (p.token != 118) {
                imprimirErrores(507);
            }
        } else {
            imprimirErrores(516);
        }
    }
    
    private void verificarRead(){
        NodoPolish nPolish;
        p = p.sig;
        if (p.token == 132) {
            p = p.sig;
            while (p.token != 133) {
                if (p.token == 100) {
                    // INSERTAMOS A LA LISTA DE POLISH
                    String[] apuntadores = null;
                    if (!apuntadoresEntrantes.isEmpty()) {
                        apuntadores = new String[apuntadoresEntrantes.size()];
                        for (int i = 0; i < apuntadoresEntrantes.size(); i++) {
                            apuntadores[i] = apuntadoresEntrantes.pop().toString();
                        }
                    }
                    nPolish = new NodoPolish(p.lexema, apuntadores);
                    if (cabezaPolish == null) {
                        cabezaPolish = nPolish;
                        nodoPolish = cabezaPolish;
                    } else {
                        nodoPolish.sig = nPolish;
                        nodoPolish = nPolish;
                    }
                    p = p.sig;
                    if (p.token == 117) {
                        // INSERTAMOS READ
                        nPolish = new NodoPolish("read", null);
                        nodoPolish.sig = nPolish;
                        nodoPolish = nPolish;
                        p = p.sig;
                    }
                } else {
                    imprimirErrores(523);
                    return;
                }
            }
            // INSERTAMOS READ
            nPolish = new NodoPolish("read", null);
            nodoPolish.sig = nPolish;
            nodoPolish = nPolish;
            p = p.sig;
            if (p.token != 118) {
                imprimirErrores(507);
            }
        } else {
            imprimirErrores(516);
        }
    }

    private void verificarIf() {
        p = p.sig;
        if (p.token == 212) {
            imprimirErrores(523);
        }
        
        /* EXPRESION */
        Postfijo listaPostfijo = pasarAPostfijo(2);

        if (errorEncontrado) {
            return;
        }
        
        /* CODIGO QUE IMPRIME LA LISTA DE POSTFIJO */
        Postfijo aux = listaPostfijo;
        System.out.println("\nIF");
        while (aux != null) {
            System.out.print(aux.tipo + " ");
            aux = aux.sig;
        }
        System.out.println("");
        /* FIN DE LA IMPRESION */
        
        /* SE EVALUA LA LISTA DE POSTFIJO */
        evaluarListaPostfijo(listaPostfijo);
        if (errorEncontrado) {
            return;
        }
        
        /* SALTO A FALSO */
        NodoPolish nPolish = new NodoPolish("BRF-A" + brincoIfFalso, null);
        nodoPolish.sig = nPolish;
        nodoPolish = nPolish;
        String apuntadorFalso = "A" + brincoIfFalso;
        brincoIfFalso++;
        
        p = p.sig;
        if (p.token == 214) {
            /* SENTENCIA 1 */
            verificarBloque();
            p = p.sig;
            
            String[] apuntadores = null;
            if (!apuntadoresEntrantes.isEmpty()) {
                apuntadores = new String[apuntadoresEntrantes.size()];
                for (int i = 0; i < apuntadoresEntrantes.size(); i++) {
                    apuntadores[i] = apuntadoresEntrantes.pop().toString();
                }
            }
            
            /* SALTO INCONDICIONAL */
            nPolish = new NodoPolish("BRI-B" + brincoIfIncondicional, apuntadores);
            nodoPolish.sig = nPolish;
            nodoPolish = nPolish;
            String apuntadorIncondicional = "B" + brincoIfIncondicional;
            brincoIfIncondicional++;
            
            if (p.token == 213) {
                p = p.sig;
                if (p.token == 214) {
                    /* PREPARANDO EL APUNTADOR A FALSO */
                    apuntadoresEntrantes.push(apuntadorFalso);
                    /* SENTENCIA 2 */
                    verificarBloque();
                } else {
                    imprimirErrores(511);
                }
            }
            /* PREPARANDO EL APUNTADOR INCONDICIONAL */
            apuntadoresEntrantes.push(apuntadorIncondicional);
        } else {
            imprimirErrores(511);
        }
    }

    private void verificarWhile() {
        p = p.sig;
        if (p.token == 203) {
            imprimirErrores(523);
        }
        
        String apuntadorIncondicional = "D" + brincoWhileIncondicional;
        brincoWhileIncondicional++;
        /* PREPARANDO EL APUNTADOR INCONDICIONAL */
        apuntadoresEntrantes.push(apuntadorIncondicional);
        
        Postfijo listaPostfijo = pasarAPostfijo(3);

        if (errorEncontrado) {
            return;
        }
        
        NodoPolish nPolish = new NodoPolish("BRF-C" + brincoWhileFalso, null);
        nodoPolish.sig = nPolish;
        nodoPolish = nPolish;
        String apuntadorFalso = "C" + brincoWhileFalso;
        brincoWhileFalso++;
        
        Postfijo aux = listaPostfijo;

        System.out.println("\nWHILE");
        while (aux != null) {
            System.out.print(aux.tipo + " ");
            aux = aux.sig;
        }
        
        System.out.println("");
        evaluarListaPostfijo(listaPostfijo);
        if (errorEncontrado) {
            return;
        }
        p = p.sig;
        if (p.token == 214) {
            verificarBloque();
            String[] apuntadores = null;
            if (!apuntadoresEntrantes.isEmpty()) {
                apuntadores = new String[apuntadoresEntrantes.size()];
                for (int i = 0; i < apuntadoresEntrantes.size(); i++) {
                    apuntadores[i] = apuntadoresEntrantes.pop().toString();
                }
            }
            nPolish = new NodoPolish("BRI-" + apuntadorIncondicional, apuntadores);
            nodoPolish.sig = nPolish;
            nodoPolish = nPolish;
            /* PREPARANDO EL APUNTADOR A FALSO */
            apuntadoresEntrantes.push(apuntadorFalso);
        } else {
            imprimirErrores(511);
        }
    }

    private int verificarExistenciaVar() {
        NodoVar aux = cabeza;
        while (aux != null) {
            if (aux.lexema.equals(p.lexema)) {
                return aux.tipo;
            }
            aux = aux.sig;
        }
        imprimirErrores(525);
        return -1;
    }

    private void evaluarListaPostfijo(Postfijo lista) {
        LinkedList pila = new LinkedList();
        while (lista != null) {
            if ((lista.tipo >= 206 && lista.tipo <= 208) || lista.tipo == 217) {
                pila.push(lista.tipo);
                lista = lista.sig;
            } else {
                if (pila.size() > 1) {
                    int op1 = obtenerIndiceMatrizOperador((int) pila.pop()), 
                            op2 = obtenerIndiceMatrizOperador((int) pila.pop());
                    int resultado = 0;
                    switch (lista.tipo) {
                        // Operador Suma
                        case 103:
                            resultado = suma[op1][op2];
                            break;

                        // Operador Resta
                        case 104:
                        // Operador Multiplicacion    
                        case 105:
                            resultado = restaMultiplicacion[op1][op2];
                            break;

                        // Operador Division    
                        case 106:
                            resultado = division[op1][op2];
                            break;

                        // Operador Mayor Que    
                        case 107:
                        // Operador Menor Que    
                        case 108:
                        // Operador Mayor Igual Que    
                        case 110:
                        // Operador Menor Igual Que    
                        case 111:
                            resultado = menorMayorYOIgualQue[op1][op2];
                            break;

                        // Operador Igual Que    
                        case 109:
                        // Operador Diferente
                        case 134:
                            resultado = igualODiferente[op1][op2];
                            break;

                        // Operador Asignacion
                        case 130:
                            resultado = asignacion[op1][op2];
                            break;
                    }
                    if (resultado != -1) {
                        pila.push(resultado);
                        lista = lista.sig;
                    } else {
                        imprimirErrores(526);
                        return;
                    }
                } else {
                    imprimirErrores(527);
                    return;
                }
            }
        }
    }
    
    private int obtenerIndiceMatrizOperador (int tipo) {
        switch (tipo) {
            case 206:
                return 0;
            case 207:
                return 1;
            case 208:
                return 2;
            case 217:
                return 3;
        }
        return -1;
    }
    
    public void imprimirErrores(int numError) {
        for (String[] error : erroresSintacticos) {
            if (numError == Integer.valueOf(error[1])) {
                System.out.println(ANSI_RED + "Error: " + error[0] + ANSI_RESET);
            }
        }
        errorEncontrado = true;
    }
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
}
