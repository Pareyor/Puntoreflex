import java.util.*;

public class ArrayAsociativo004 {

    static final int FILA = 0;
    static final int COLUMNA = 1;

    static final int ARRIBA = 0;
    static final int ABAJO = 1;
    static final int IZQUIERDA = 2;
    static final int DERECHA = 3;
    static final int SALIR = 4;

    static final int[][] MOVIMIENTO = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };

    static boolean jugando = true;

    public static void main(String[] args) {

        String[] castilloLB = {
                "................................................................",
                "................................................................",
                ".......                                                   ......",
                ".....                                                       ....",
                "....                                                         ...",
                "...      ..................................                   ..",
                "...    .....................................          ....    ..",
                "..    .......................................        ......    .",
                "..    ..|-----|..........|-------------|.....    .|-----|..    .",
                "..   ...|::+::|..........O+++++++++++++|....    ..|::+::|...   .",
                "..   ...|:+++:|..........|+++++++++++++|...    ...|:+++:|...   .",
                "..   ...|+++++------------++|-------|++------------+++++|...   .",
                "..   ...|:++++++++++++++++++|#######|++++++++++++++++++:|...   .",
                "..   ...|::+++++++++++++++++|::###::|+++++++++++++++++::|...   .",
                "..   ...---|++|-------|-+++-|:*#*#*:|-----|---|---|++|---...   .",
                "..   ......|++|---O---|+++++|*******|#####|+++-$$$|++|......   .",
                "..   ......|++|*#***#*|+++++|*******|#####-+++O*$$|++|......   .",
                "..    .....|++|*#***#*|+++++|*******|+++++X+++|**$|++|......   .",
                "..    .....|++|*#####*|+++++O*******|+++++X+++|---|++|......   .",
                "..     ....|++|*******|--+--|--***--|+++++X+++-**$|++|......   .",
                "..      ...|++|--***--|+++++|::+++::|+++++|+++O*$$|++|......   .",
                "..       ..|++|+++++++|+++++|:+++++:|+++++|+++|$$$|++|......   .",
                "...      ..|++|+##+##+|------+++++++--+++|---|----|++|......   .",
                "....      .|++|+++++++|++++++++++++++++++|***|**#*|++|......   .",
                "....       |++|+##+##+|++++++++++++++++++|---|**##|++|......   .",
                ".....      |++|+++++++|++++++++++++++++++|********|++|......   .",
                ".....      |++|+##+##+--++|----+++----|++|***|****|++|......   .",
                "....      .|++|+++++++++++|:::+++++:::|++-------**|++|......   .",
                "....     ..|++|++++++++++:|::+++++++::|+++++++++++|++|......   .",
                "...      ..|++|-------|+::|:+++++++++:|+++++++++++|++|......   .",
                "...     ...|++|*******|----++++   ++++-------|----|++|......   .",
                "..      ...|++|**|---*|+++++++     ++++++++++|++++|++|......   .",
                "..     ....|++|**|*#**|+++++++     ++++++++++O++++|++|......   .",
                "..     ....|++|**|*#**|+++++++     ++++++++++|++++|++|......   .",
                "..    .....|++|**--|**|++++++++   ++++|-------++++|++|......   .",
                "..    .....|++|****|**|++++++++++++++:|+++++++++++|++|......   .",
                "..   ......|++|--**----+++++++++++++::|+-+++++++++|++|......   .",
                "..   ......|++|++++++++++++++++++++:::|+++++++++++|++|......   .",
                "..   ......|++|-------------|--+++--|----|+++|----|++|......   .",
                "..   ......|++|XXXXXXXXXXXXX|::+++::|#***|+++|***#|++|......   .",
                "..   ......|++|X+++++++++++X| :+++: |****|+++|****|++|......   .",
                "..   ......|++|X++XXXXXXXX+X|  +++  |--O--+++--O--|++|......   .",
                "..   ......|++|X++|-----|X+X|  +++  |+++++++++++++|++|......   .",
                "..   ......|++|X++|%%%%%|X+X| :+++: |--O-|+++|-O--|++|......   .",
                "..   ......|++|X++|*****|X+X|::+++::|****|+++|****|++|......   .",
                "..   ......|++|X++|#####|X+X| :+++: |#***|+++|***#|++|......   .",
                "..   ......|++|X++|*****|X+X|  +++  |-----+++-----|++|......   .",
                "..   ......|++|X++----**|X+X|  +++  |X+++++++++++%|++|......   .",
                "..   ......|++|X++++++++|X+X|  +++  |XX+++++++++%%|++|......   .",
                "..   ......|++|XXXXXXXXX|X+X| :+++: |XXX+++++++%%%|++|......   .",
                "..   ...|---++-----------X+X|::+++::|XXXX+++++%%%%|XX---|...   .",
                "..   ...|::++++++++++++++++X| :+++: |XXXXX+++%%%%%|***$$|...   .",
                "..   ...|:+++++++++++++++++X|  +++  |#############|****$|...   .",
                "..   ...|+++++|-------------|  +++  |-------------|*****|...   .",
                "..   ...|:+++:|.............| :+++: |.............|$***$|...   .",
                "..   ...|::+::|.............|::+++::|.............|$$*$$|...   .",
                "..    ..-------.............---+++---.............-------..    .",
                "..    .....................................................    .",
                "...    ...................................................    ..",
                "...      ......................+++......................      ..",
                "....                           +++                           ...",
                ".....                          +++                          ....",
                ".......                        +++                        ......",
                "...............................+++.............................."
        };

        String[] castilloEX = {
                "                                                                ",
                "                                          %%%%%%%%%%%%%%%%%%%%%%",
                "                                          %%%%%%%%%%%%%%%%%%%%%%",
                "                                          %%%%%%%%%%%%%%%%%%%%%%",
                "                                          %%%%%%%%%%%%%%%%%%%%%%",
                "                       |----------------|%%%%%%%%%%%%%%%%%%%%%%%",
                "                       |   ###%%%%###   |%%%%%%%%%%%%%%%%%%%%%%%",
                "                       |   ##      ##   |%%%%%%%%%%%%%%%%%%%%%%%",
                "             |------|  |   #  ****  #   |%%%%%%%%%%%%%%%%%%%%%%%",
                "             |******|  |     #****#     |%%%%%%%%%%%%%%%%%%%%%%%",
                "|--------|----******---|     #****#     |---------###%%%%%%%%%%%",
                "|===*****|******||*****|     #****#     |%%%*****O**#%%%%%%%%%%%",
                "|+===****|XX****||*****|    ###**###    |%%******|**#%%%%%%%%%%%",
                "|++|------XX|-----O-|**|    #%%**%%#    |%**|-----###%%%%%%%%%%%",
                "|++|******XX|*******|**|    #%%**%%#    |%****|%%%%%%%%%%%%%%%%%",
                "|++|********|*******|**|    #%%**%%#    |*****|%%%%%%%%%%%%%%%%%",
                "|++|-**------**-|---|**|    ###**###    |*****|%%%%%%%%%%%%%%%%%",
                "|++|#**#%%%%#**#|%%%|**-------****-------**|**|%%%%%%%%%%%%%%%%%",
                "|++|%**%%%%%%***|%%%|**********************|**|%%%%%%%%%%%%%%%%%",
                "|++|%**%|-|%%***|%%%|**********************|XX|%%%%%%%%%%%%%%%%%",
                "|++|**%%|-|%%#**|%%=|-------------|--|-----|XX---|%%%%%%%%%%%%%%",
                "|++|***%---%%***|%==|    *****    |**|=%%%%|XX***|%%%%%%%%%%%%%%",
                "|++|*#*%***%***%|===|             |**|==%%%|*****-----------|--|",
                "|++|%*********#%|===|     ***     |**|===|---||***%#%#%#%-**|%%|",
                "|++|%%*|***|*%%%|+==|    *+++*    |**|===||++||**********O**O*%|",
                "|++|%%%|***|%%%%|++=|    **+***   --O|=+=||++|-***%#%#%#%-**|%%|",
                "|++-----***------+++|    |#+#|*    **|+++||++|***|----------|%%|",
                "|++++++++++++++XX+++|    |+++|*******|+++--++|***-******%%%%|%%|",
                "|++++++++++++++XX+++------#+#---------+++XX++|***********%%%|%%|",
                "--------|-------|+++++++#=+++=#++++++++++XX++|***********%%%|%%|",
                "        |%%%%%%||++++++++++++++++++++++++|--------**%%***%%%|%%|",
                "   |-----%%%%%%||++++++++++++++++++++++++|++++++++%%%%%***%%|%%|",
                "   |*%%%%%%||%%|------------------***----|+++++++%%%%%%***%*|%%|",
                "   |**%%*%%||%%|                ##***#   |++--|-|%%%%%%%*****%%|",
                "   |%*|-O------|               #*****#   |++++|||%%%%%%***%***%|",
                "   |**|%*%%%%%%|               #****#    |++++|||+%%%%%**%%%|---",
                "   |**|%%%%%%%%|              #***##     |-|++|||++|---++|---   ",
                "   |*%------|%%|------|    |---***---|   |--++|--++|+++++|      ",
                "   |%%%***%%|%%|******|    |**%******|   |++++|++++|+++++|      ",
                "   |%%%%***%|%%-******|    |*%%%**%**|   |++++|++++|++|---      ",
                "   ------|**|%%%%*||**-####-%%%|-|%%*-###-++|-|++---++|         ",
                "         |*%|%%***||********%%%|-|%%%*****++|||+++++++|         ",
                "         |%%---|---|*******%%%%---%%******++|||+++++++|         ",
                "         |%%%**|----**|####|%%%%*%%%%|###|++----|++|---         ",
                "         |%%%%*|******|    |%%%***%%%|   |+++++||++|            ",
                "         ---|**|%*****|    |%%*****%%|   |+++++||++|            ",
                "            |*%|%%|----    ---|***|---   ---|++--++|            ",
                "            |%%|%%|           |***|         |++++++|            ",
                "            |%%-%%|           |***|         |++++++|            ",
                "            |%%%%%|          #|***|#        --------            ",
                "            |%%%%%|          #|***|#                            ",
                "            -------            +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              ",
                "                               +++                              "
        };

        int[] elPersonaje = { 20, 20 };
        int viewPort = 5;

        do {
            imprimirMundo(castilloLB, elPersonaje, viewPort);
            verAccion(elPersonaje);
        } while (jugando);

    }

    static void imprimirMundo(String[] castillo, int[] personaje, int viewPort) {
        
        String elemento;
        limpiarPantalla();
        imprimirLinea(viewPort);
        for (int fila = personaje[FILA] - viewPort; fila <= personaje[FILA] + viewPort; fila++) {
            for (int columna = personaje[COLUMNA] - viewPort; columna <= personaje[COLUMNA] + viewPort; columna++) {

                if (fila == personaje[FILA] && columna == personaje[COLUMNA]) {
                    elemento = "_O_";
                } else {
                    elemento = mapear(castillo[fila].charAt(columna));
                }

                System.out.print(elemento);
            }
            System.out.println();
        }
        imprimirLinea(viewPort);
    }

    static void imprimirLinea(int viewPort) {

        System.out.println(mapear('B').repeat(viewPort * 2 + 1));
    }

    static String mapear(char elemento) {

        HashMap<String, String> tiles = new HashMap<>();

        tiles.put(" ", "   ");
        tiles.put(".", " . ");
        tiles.put("-", "[#]");
        tiles.put("=", "|||");
        tiles.put("|", "[#]");
        tiles.put(":", "oOo");
        tiles.put("+", "...");
        tiles.put("O", "[ ]");
        tiles.put("#", ".:.");
        tiles.put("*", "***");
        tiles.put("$", "$$$");
        tiles.put("X", "XXX");
        tiles.put("%", "%%%");
        tiles.put("_", "___");
        tiles.put("B", "===");

        return tiles.get("" + elemento);

    }

    static void mover(int[] unPersonaje, int direccion) {

        unPersonaje[FILA] += MOVIMIENTO[direccion][FILA];
        unPersonaje[COLUMNA] += MOVIMIENTO[direccion][COLUMNA];
    }

    static void verAccion(int[] elPersonaje) {

        switch (capturarMovimiento()) {
            case ARRIBA:
                mover(elPersonaje, ARRIBA);
                break;
            case ABAJO:
                mover(elPersonaje, ABAJO);
                break;
            case IZQUIERDA:
                mover(elPersonaje, IZQUIERDA);
                break;
            case DERECHA:
                mover(elPersonaje, DERECHA);
                break;
            case SALIR:
                jugando = !jugando;
                break;
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
        }
        return 0;
    }

    static char pedirChar() {

        Scanner entrada = new Scanner(System.in);
        return entrada.next().charAt(0);
    }

    static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
    }
}