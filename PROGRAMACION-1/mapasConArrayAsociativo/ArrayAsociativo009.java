import java.util.*;

public class ArrayAsociativo009 {

    static final int FILA = 0;
    static final int COLUMNA = 1;

    static final int ARRIBA = 0;
    static final int ABAJO = 1;
    static final int IZQUIERDA = 2;
    static final int DERECHA = 3;
    static final int SALIR = 4;
    static final int CAMBIA_VISUALIZACION = 5;
    static final int NADA = 999;

    static final int VISUALIZACION_NORMAL = 0;
    static final int VISUALIZACION_SIN_COLOR = 1;
    static final int VISUALIZACION_RAW = 2;
    static final int VISUALIZACION_COLISIONES = 3;

    static final int[][] MOVIMIENTO = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };

    static double hora = 12.0;
    static int alcanceVision;
    static int viewPort;
    static int modoVisualizacion = VISUALIZACION_NORMAL;

    static int minFila, minColumna, maxFila, maxColumna;

    static boolean jugando = true;

    public static void main(String[] args) {

        String[] castilloLB = {
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............                                                   .............",
                "............                                                       ...........",
                "...........                                                         ..........",
                "..........      ..................................                   .........",
                "..........    .....................................          ....    .........",
                ".........    .......................................        ......    ........",
                ".........    ..|-----|..........|-------------|.....    .|-----|..    ........",
                ".........   ...|::+::|..........O+++++++++++++|....    ..|::+::|...   ........",
                ".........   ...|:+++:|..........|+++++++++++++|...    ...|:+++:|...   ........",
                ".........   ...|+++++------------++|-------|++------------+++++|...   ........",
                ".........   ...|:++++++++++++++++++|#######|++++++++++++++++++:|...   ........",
                ".........   ...|::+++++++++++++++++|::###::|+++++++++++++++++::|...   ........",
                ".........   ...---|++|-------|-+++-|:*#*#*:|-----|---|---|++|---...   ........",
                ".........   ......|++|---O---|+++++|*******|#####|+++-$$$|++|......   ........",
                ".........   ......|++|*#***#*|+++++|*******|#####-+++O*$$|++|......   ........",
                ".........    .....|++|*#***#*|+++++|*******|+++++X+++|**$|++|......   ........",
                ".........    .....|++|*#####*|+++++O*******|+++++X+++|---|++|......   ........",
                ".........     ....|++|*******|--+--|--***--|+++++X+++-**$|++|......   ........",
                ".........      ...|++|--***--|+++++|::+++::|+++++|+++O*$$|++|......   ........",
                ".........       ..|++|+++++++|+++++|:+++++:|+++++|+++|$$$|++|......   ........",
                "..........      ..|++|+##+##+|------+++++++--+++|---|----|++|......   ........",
                "...........      .|++|+++++++|++++++++++++++++++|***|**#*|++|......   ........",
                "...........       |++|+##+##+|++++++++++++++++++|---|**##|++|......   ........",
                "............      |++|+++++++|++++++++++++++++++|********|++|......   ........",
                "............      |++|+##+##+--++|----+++----|++|***|****|++|......   ........",
                "...........      .|++|+++++++++++|:::+++++:::|++-------**|++|......   ........",
                "...........     ..|++|++++++++++:|::+++++++::|+++++++++++|++|......   ........",
                "..........      ..|++|-------|+::|:+++++++++:|+++++++++++|++|......   ........",
                "..........     ...|++|*******|----++++   ++++-------|----|++|......   ........",
                ".........      ...|++|**|---*|+++++++     ++++++++++|++++|++|......   ........",
                ".........     ....|++|**|*#**|+++++++     ++++++++++O++++|++|......   ........",
                ".........     ....|++|**|*#**|+++++++     ++++++++++|++++|++|......   ........",
                ".........    .....|++|**--|**|++++++++   ++++|-------++++|++|......   ........",
                ".........    .....|++|****|**|++++++++++++++:|+++++++++++|++|......   ........",
                ".........   ......|++|--**----+++++++++++++::|+-+++++++++|++|......   ........",
                ".........   ......|++|++++++++++++++++++++:::|+++++++++++|++|......   ........",
                ".........   ......|++|-------------|--+++--|----|+++|----|++|......   ........",
                ".........   ......|++|XXXXXXXXXXXXX|::+++::|#***|+++|***#|++|......   ........",
                ".........   ......|++|X+++++++++++X| :+++: |****|+++|****|++|......   ........",
                ".........   ......|++|X++XXXXXXXX+X|  +++  |--O--+++--O--|++|......   ........",
                ".........   ......|++|X++|-----|X+X|  +++  |+++++++++++++|++|......   ........",
                ".........   ......|++|X++|%%%%%|X+X| :+++: |--O-|+++|-O--|++|......   ........",
                ".........   ......|++|X++|*****|X+X|::+++::|****|+++|****|++|......   ........",
                ".........   ......|++|X++|#####|X+X| :+++: |#***|+++|***#|++|......   ........",
                ".........   ......|++|X++|*****|X+X|  +++  |-----+++-----|++|......   ........",
                ".........   ......|++|X++----**|X+X|  +++  |X+++++++++++%|++|......   ........",
                ".........   ......|++|X++++++++|X+X|  +++  |XX+++++++++%%|++|......   ........",
                ".........   ......|++|XXXXXXXXX|X+X| :+++: |XXX+++++++%%%|++|......   ........",
                ".........   ...|---++-----------X+X|::+++::|XXXX+++++%%%%|XX---|...   ........",
                ".........   ...|::++++++++++++++++X| :+++: |XXXXX+++%%%%%|***$$|...   ........",
                ".........   ...|:+++++++++++++++++X|  +++  |#############|****$|...   ........",
                ".........   ...|+++++|-------------|  +++  |-------------|*****|...   ........",
                ".........   ...|:+++:|.............| :+++: |.............|$***$|...   ........",
                ".........   ...|::+::|.............|::+++::|.............|$$*$$|...   ........",
                ".........    ..-------.............---+++---.............-------..    ........",
                ".........    .....................................................    ........",
                "..........    ...................................................    .........",
                "..........      ......................+++......................      .........",
                "...........                           +++                           ..........",
                "............                          +++                          ...........",
                "..............                        +++                        .............",
                "......................................+++.....................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                "..............................................................................",
                ".............................................................................."
        };
        String[] castilloEX = {
                "                                                                              ",
                "                                                                              ",
                "                                                                              ",
                "                                                                              ",
                "                                                                              ",
                "                                                                              ",
                "                                                                              ",
                "                                                                              ",
                "                                                 %%%%%%%%%%%%%%%%%%%%%%       ",
                "                                                 %%%%%%%%%%%%%%%%%%%%%%       ",
                "                                                 %%%%%%%%%%%%%%%%%%%%%%       ",
                "                                                 %%%%%%%%%%%%%%%%%%%%%%       ",
                "                              |----------------|%%%%%%%%%%%%%%%%%%%%%%%       ",
                "                              |   ###%%%%###   |%%%%%%%%%%%%%%%%%%%%%%%       ",
                "                              |   ##      ##   |%%%%%%%%%%%%%%%%%%%%%%%       ",
                "                    |------|  |   #  ****  #   |%%%%%%%%%%%%%%%%%%%%%%%       ",
                "                    |******|  |     #****#     |%%%%%%%%%%%%%%%%%%%%%%%       ",
                "       |--------|----******---|     #****#     |---------###%%%%%%%%%%%       ",
                "       |===*****|******||*****|     #****#     |%%%*****O**#%%%%%%%%%%%       ",
                "       |+===****|XX****||*****|    ###**###    |%%******|**#%%%%%%%%%%%       ",
                "       |++|------XX|-----O-|**|    #%%**%%#    |%**|-----###%%%%%%%%%%%       ",
                "       |++|******XX|*******|**|    #%%**%%#    |%****|%%%%%%%%%%%%%%%%%       ",
                "       |++|********|*******|**|    #%%**%%#    |*****|%%%%%%%%%%%%%%%%%       ",
                "       |++|-**------**-|---|**|    ###**###    |*****|%%%%%%%%%%%%%%%%%       ",
                "       |++|#**#%%%%#**#|%%%|**-------****-------**|**|%%%%%%%%%%%%%%%%%       ",
                "       |++|%**%%%%%%***|%%%|**********************|**|%%%%%%%%%%%%%%%%%       ",
                "       |++|%**%|-|%%***|%%%|**********************|XX|%%%%%%%%%%%%%%%%%       ",
                "       |++|**%%|-|%%#**|%%=|-------------|--|-----|XX---|%%%%%%%%%%%%%%       ",
                "       |++|***%---%%***|%==|    *****    |**|=%%%%|XX***|%%%%%%%%%%%%%%       ",
                "       |++|*#*%***%***%|===|             |**|==%%%|*****-----------|--|       ",
                "       |++|%*********#%|===|     ***     |**|===|---||***%#%#%#%-**|%%|       ",
                "       |++|%%*|***|*%%%|+==|    *+++*    |**|===||++||**********O**O*%|       ",
                "       |++|%%%|***|%%%%|++=|    **+***   --O|=+=||++|-***%#%#%#%-**|%%|       ",
                "       |++-----***------+++|    |#+#|*    **|+++||++|***|----------|%%|       ",
                "       |++++++++++++++XX+++|    |+++|*******|+++--++|***-******%%%%|%%|       ",
                "       |++++++++++++++XX+++------#+#---------+++XX++|***********%%%|%%|       ",
                "       --------|-------|+++++++#=+++=#++++++++++XX++|***********%%%|%%|       ",
                "               |%%%%%%||++++++++++++++++++++++++|--------**%%***%%%|%%|       ",
                "          |-----%%%%%%||++++++++++++++++++++++++|++++++++%%%%%***%%|%%|       ",
                "          |*%%%%%%||%%|------------------***----|+++++++%%%%%%***%*|%%|       ",
                "          |**%%*%%||%%|                ##***#   |++--|-|%%%%%%%*****%%|       ",
                "          |%*|-O------|               #*****#   |++++|||%%%%%%***%***%|       ",
                "          |**|%*%%%%%%|               #****#    |++++|||+%%%%%**%%%|---       ",
                "          |**|%%%%%%%%|              #***##     |-|++|||++|---++|---          ",
                "          |*%------|%%|------|    |---***---|   |--++|--++|+++++|             ",
                "          |%%%***%%|%%|******|    |**%******|   |++++|++++|+++++|             ",
                "          |%%%%***%|%%-******|    |*%%%**%**|   |++++|++++|++|---             ",
                "          ------|**|%%%%*||**-####-%%%|-|%%*-###-++|-|++---++|                ",
                "                |*%|%%***||********%%%|-|%%%*****++|||+++++++|                ",
                "                |%%---|---|*******%%%%---%%******++|||+++++++|                ",
                "                |%%%**|----**|####|%%%%*%%%%|###|++----|++|---                ",
                "                |%%%%*|******|    |%%%***%%%|   |+++++||++|                   ",
                "                ---|**|%*****|    |%%*****%%|   |+++++||++|                   ",
                "                   |*%|%%|----    ---|***|---   ---|++--++|                   ",
                "                   |%%|%%|           |***|         |++++++|                   ",
                "                   |%%-%%|           |***|         |++++++|                   ",
                "                   |%%%%%|          #|***|#        --------                   ",
                "                   |%%%%%|          #|***|#                                   ",
                "                   -------            +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     ",
                "                                      +++                                     "
        };

        int[] elPersonaje = { 31, 33 };

        String[] mundo;
        mundo = castilloLB;
        inicializaMundo(mundo, castilloLB);

        do {
            actualizarTiempo();
            imprimirMundo(mundo, elPersonaje);
            verAccion(elPersonaje, mundo);
        } while (jugando);

    }

    private static void inicializaMundo(String[] mundo, String[] castilloLB) {

        viewPort = 7;
        alcanceVision = viewPort / 2;

        minFila = 0 + viewPort;
        minColumna = 0 + viewPort;
        maxFila = mundo.length - (viewPort + 1);
        maxColumna = mundo[0].length() - (viewPort + 1);
    }

    static void actualizarTiempo() {
        hora = hora + 0.2;
        if (hora >= 24) {
            hora = 0;
        }
        calculaAlcanceVision();
    }

    static void calculaAlcanceVision() {

        alcanceVision = viewPort * 2;

        if (hora < 1 || hora > 23) {
            alcanceVision = 1;
        } else if (hora < 3 || hora > 21) {
            alcanceVision = alcanceVision / 5;
        } else if (hora < 4 || hora > 20) {
            alcanceVision = alcanceVision / 5 * 2;
        } else if (hora < 5 || hora > 19) {
            alcanceVision = alcanceVision / 5 * 3;
        } else if (hora < 6 || hora > 18) {
            alcanceVision = alcanceVision / 5 * 4;
        }

    }

    static void imprimirMundo(String[] castillo, int[] personaje) {

        String elemento;
        limpiarPantalla();
        imprimirLinea();
        imprimirElCielo();
        imprimirLinea();
        for (int fila = personaje[FILA] - viewPort; fila <= personaje[FILA] + viewPort; fila++) {
            for (int columna = personaje[COLUMNA] - viewPort; columna <= personaje[COLUMNA] + viewPort; columna++) {

                elemento = mapear('P', modoVisualizacion);
                if (!(fila == personaje[FILA] && columna == personaje[COLUMNA])) {
                    elemento = mapear(castillo[fila].charAt(columna), modoVisualizacion);
                }

                if (!(Math.pow((personaje[FILA] - fila), 2)
                        + Math.pow((personaje[COLUMNA] - columna), 2) <= alcanceVision * alcanceVision)) {
                    elemento = mapear('D', modoVisualizacion);
                }

                System.out.print(elemento);
            }
            System.out.println();
        }
        imprimirLinea();
        imprimirStatus(personaje);
        imprimirLinea();
    }

    private static void imprimirStatus(int[] personaje) {
        System.out.print("HORA: [" + (int) hora + "] ");
        System.out.print("/ (" + personaje[FILA] + "," + personaje[COLUMNA] + ")");
        System.out.println(" / SKIN[" + modoVisualizacion + "]");
    }

    static void imprimirLinea() {

        System.out.println(mapear('B', modoVisualizacion).repeat(viewPort * 2 + 1));
    }

    static String mapear(char elemento, int modoVisualizacion) {

        switch (modoVisualizacion) {
            case VISUALIZACION_NORMAL:
                return mapearNormal(elemento);
            case VISUALIZACION_SIN_COLOR:
                return mapearSinColor(elemento);
            case VISUALIZACION_RAW:
                return mapearRaw(elemento);
            case VISUALIZACION_COLISIONES:
                return tipoTerreno(elemento);
        }
        return "";
    }

    private static String mapearRaw(char elemento) {

        HashMap<String, String> tiles = new HashMap<>();

        tiles.put(" ", " ");
        tiles.put(".", ".");
        tiles.put("-", "-");
        tiles.put("=", "=");
        tiles.put("|", "|");
        tiles.put(":", ":");
        tiles.put("+", "+");
        tiles.put("O", "O");
        tiles.put("#", "#");
        tiles.put("*", "*");
        tiles.put("$", "$");
        tiles.put("X", "X");
        tiles.put("%", "%");
        tiles.put("_", "_");
        tiles.put("~", "~");
        tiles.put("B", "B");
        tiles.put("P", "P");
        tiles.put("D", "D");
        tiles.put("S", "S");
        tiles.put("C", "C");

        return tiles.get("" + elemento);
    }

    static String mapearSinColor(char elemento) {

        HashMap<String, String> tiles = new HashMap<>();

        tiles.put(" ", " ~~ ");
        tiles.put(".", " . .");
        tiles.put("-", "[##]");
        tiles.put("=", "||||");
        tiles.put("|", "[##]");
        tiles.put(":", "oO*o");
        tiles.put("+", "..:.");
        tiles.put("O", "[  ]");
        tiles.put("#", "::::");
        tiles.put("*", "    ");
        tiles.put("$", "$$$$");
        tiles.put("X", "||||");
        tiles.put("%", "%%%%");
        tiles.put("_", "____");
        tiles.put("~", " ~ ~");
        tiles.put("B", "====");
        tiles.put("P", "_()_");
        tiles.put("D", "    ");
        tiles.put("S", " () ");
        tiles.put("C", "    ");

        return tiles.get("" + elemento);
    }

    static String mapearNormal(char elemento) {

        HashMap<String, String> tiles = new HashMap<>();

        tiles.put(" ", BLUE_BOLD_BRIGHT + BLUE_BACKGROUND_BRIGHT + " ~~ " + RESET);
        tiles.put(".", YELLOW_BRIGHT + GREEN_BACKGROUND + " . ." + RESET);
        tiles.put("-", WHITE + WHITE_BACKGROUND + "[##]" + RESET);
        tiles.put("=", BLACK_BACKGROUND_BRIGHT + "||||" + RESET);
        tiles.put("|", WHITE + WHITE_BACKGROUND + "[##]" + RESET);
        tiles.put(":", YELLOW_BRIGHT + GREEN_BACKGROUND + "oO*o" + RESET);
        tiles.put("+", GREEN_BOLD + YELLOW_BACKGROUND + "..:." + RESET);
        tiles.put("O", WHITE_BACKGROUND_BRIGHT + "[  ]" + RESET);
        tiles.put("#", BLACK + RED_BACKGROUND + "::::" + RESET);
        tiles.put("*", YELLOW + YELLOW_BACKGROUND + "    " + RESET);
        tiles.put("$", RED_BRIGHT + YELLOW_BACKGROUND + "$$$$" + RESET);
        tiles.put("X", RED_UNDERLINED + YELLOW_BACKGROUND + "||||" + RESET);
        tiles.put("%", GREEN_BOLD + YELLOW_BACKGROUND + "%%%%" + RESET);
        tiles.put("_", BLACK + "____" + RESET);
        tiles.put("~", BLUE_BACKGROUND_BRIGHT + " ~ ~" + RESET);
        tiles.put("B", BLUE + BLUE_BACKGROUND + "====" + RESET);
        tiles.put("P", RED + "_()_" + RESET);
        tiles.put("D", BLACK_BACKGROUND_BRIGHT + "    " + RESET);
        tiles.put("S", YELLOW_BOLD_BRIGHT + BLUE_BACKGROUND + " () " + RESET);
        tiles.put("C", BLUE_BACKGROUND + "    " + RESET);

        return tiles.get("" + elemento);
    }

    static String tipoTerreno(char elemento) {

        HashMap<String, String> tiles = new HashMap<>();

        tiles.put(" ", "1");
        tiles.put(".", "0");
        tiles.put("-", "1");
        tiles.put("=", "0");
        tiles.put("|", "1");
        tiles.put(":", "0");
        tiles.put("+", "0");
        tiles.put("O", "0");
        tiles.put("#", "0");
        tiles.put("*", "0");
        tiles.put("$", "0");
        tiles.put("X", "0");
        tiles.put("%", "0");
        tiles.put("_", "0");
        tiles.put("~", "1");
        tiles.put("B", "0");
        tiles.put("P", "*");
        tiles.put("D", "0");
        tiles.put("S", "0");
        tiles.put("C", "0");

        return tiles.get("" + elemento);
    }

    static void mover(int[] unPersonaje, int direccion, String[] mapa) {

        int oldFila = unPersonaje[FILA];
        int oldColumna = unPersonaje[COLUMNA];

        unPersonaje[FILA] += MOVIMIENTO[direccion][FILA];
        unPersonaje[COLUMNA] += MOVIMIENTO[direccion][COLUMNA];

        if (!tipoTerreno(mapa[unPersonaje[FILA]].charAt(unPersonaje[COLUMNA])).equals("0")) {
            unPersonaje[FILA] = oldFila;
            unPersonaje[COLUMNA] = oldColumna;
        }

        if (unPersonaje[FILA] < minFila) {
            unPersonaje[FILA] = unPersonaje[FILA] + 1;
        }
        if (unPersonaje[FILA] > maxFila) {
            unPersonaje[FILA] = unPersonaje[FILA] - 1;
        }
        if (unPersonaje[COLUMNA] < minColumna) {
            unPersonaje[COLUMNA] = unPersonaje[COLUMNA] + 1;
        }
        if (unPersonaje[COLUMNA] > maxColumna) {
            unPersonaje[COLUMNA] = unPersonaje[COLUMNA] - 1;
        }

    }

    static void verAccion(int[] elPersonaje, String[] elMundo) {

        switch (capturarMovimiento()) {
            case ARRIBA:
                mover(elPersonaje, ARRIBA, elMundo);
                break;
            case ABAJO:
                mover(elPersonaje, ABAJO, elMundo);
                break;
            case IZQUIERDA:
                mover(elPersonaje, IZQUIERDA, elMundo);
                break;
            case DERECHA:
                mover(elPersonaje, DERECHA, elMundo);
                break;
            case SALIR:
                jugando = !jugando;
                break;
            case CAMBIA_VISUALIZACION:
                cambiaVisualizacion();
                break;
            case NADA:
                break;
        }
    }

    static void cambiaVisualizacion() {
        modoVisualizacion++;
        if (modoVisualizacion > 3) {
            modoVisualizacion = 0;
        }
    }

    static int capturarMovimiento() {

        switch (pedirChar()) {
            case 's', 'S', '8':
                return ABAJO;
            case 'w', 'W', '2':
                return ARRIBA;
            case 'a', 'A', '4':
                return IZQUIERDA;
            case 'd', 'D', '6':
                return DERECHA;
            case 'f', 'F':
                return SALIR;
            case 'v', 'V':
                return CAMBIA_VISUALIZACION;
        }
        return NADA;
    }

    static char pedirChar() {

        Scanner entrada = new Scanner(System.in);
        String inputUsuario = entrada.nextLine() + "x"; // Este es un caso que justifica un comentario!
        return inputUsuario.charAt(0); // Lo comentamos en clase ;)
    }

    static void limpiarPantalla() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void imprimirElCielo() {

        for (int i = 0; i < viewPort * 2 + 1; i = i + 1) {
            if ((hora > 6) && (hora <= 18) && (i == (int) (((viewPort * 2)) - ((hora - 7) * (viewPort * 2) / 12)))) {
                System.out.print(mapear('S', modoVisualizacion));
            } else {
                System.out.print(mapear('C', modoVisualizacion));
            }
        }
        System.out.println();
    }

    // Reset
    public static final String RESET = "\033[0m"; // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m"; // BLACK
    public static final String RED = "\033[0;31m"; // RED
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String YELLOW = "\033[0;33m"; // YELLOW
    public static final String BLUE = "\033[0;34m"; // BLUE
    public static final String PURPLE = "\033[0;35m"; // PURPLE
    public static final String CYAN = "\033[0;36m"; // CYAN
    public static final String WHITE = "\033[0;37m"; // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
    public static final String RED_BOLD = "\033[1;31m"; // RED
    public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
    public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m"; // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m"; // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m"; // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m"; // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m"; // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m"; // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
    public static final String RED_BACKGROUND = "\033[41m"; // RED
    public static final String GREEN_BACKGROUND = "\033[42m"; // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m"; // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m"; // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m"; // BLACK
    public static final String RED_BRIGHT = "\033[0;91m"; // RED
    public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m"; // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m"; // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE

}