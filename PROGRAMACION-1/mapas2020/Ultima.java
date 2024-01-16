import java.util.Scanner;

class Ultima {

    static final int ROW = 0;
    static final int COLUMN = 1;
    static final int TRANSPORT = 2;

    static final int ON_FOOT = 0;
    static final int ON_SHIP = 1;

    static final int CHARACTER_TILE = 10;
    static final int SUN_TILE = 11;
    static final int SKY_TILE = 12;
    static final int SHIP_TILE = 13;
    static final int DARKNESS_TILE = 14;
    static final int LINE_TILE = 15;

    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;
    static final int EXIT = 4;
    static final int CHANGE_VIEW = 5;
    static final int CHANGE_TRANSPORT = 6;
    static final int NOTHING = 999;

    static final int NORMAL_VIEW = 0;
    static final int MONOCHROME_VIEW = 1;
    static final int RAW_VIEW = 2;
    static final int COLLISION_VIEW = 3;

    static final int[][] MOVEMENTS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };

    static double hour;
    static int viewRange;
    static int viewPort;
    static int viewMode = NORMAL_VIEW;

    static int rowMin, columnMin, rowMax, columnMax;

    static boolean isPlaying = true;

    public static void main(String[] args) {

        int[][] elMundo = Constantes.ULTIMA;

        int[] avatar = { 10, 28, ON_FOOT };

        initWorld(elMundo);

        do {
            updateTime();
            printWorld(elMundo, avatar);
            getAction(avatar, elMundo);
        } while (isPlaying);
    }

    private static void initWorld(int[][] world) {

        viewPort = 10;
        viewRange = viewPort * 2;

        rowMin = 0 + viewPort;
        columnMin = 0 + viewPort;
        rowMax = world.length - (viewPort + 1);
        columnMax = world[0].length - (viewPort + 1);
        hour = 6;
    }

    static void updateTime() {

        hour = hour + 0.1;
        if (hour >= 24) {
            hour = 0;
        }
        updateViewRange();
    }

    static void updateViewRange() {

        int maxRange = viewPort * 2;
        if (hour < 4 || hour > 20) {
            viewRange = 2;
        } else if (hour < 6) {
            viewRange = (int) (2 + (maxRange - 2) * (hour - 4) / 2);
        } else if (hour < 18) {
            viewRange = maxRange;
        } else {
            viewRange = (int) (maxRange - (maxRange - 2) * (hour - 18) / 2);
        }
    }

    static void printWorld(int[][] world, int[] character) {

        String tile;
        cleanScreen();
        printSky();
        for (int row = character[ROW] - viewPort; row <= character[ROW] + viewPort; row++) {
            for (int column = character[COLUMN] - viewPort; column <= character[COLUMN] + viewPort; column++) {

                tile = getTileView(world[row][column], viewMode);

                if (wallOnSight(character, row, column, world)) {
                    tile = getTileView(DARKNESS_TILE, viewMode);
                } else if (!(Math.pow((row - character[ROW]), 2) + Math.pow((column - character[COLUMN]), 2) <= Math.pow(viewRange, 2))) {
                    tile = getTileView(DARKNESS_TILE, viewMode);
                }

                if ((row == character[ROW] && column == character[COLUMN])) {
                    tile = getTileView(character[2] == ON_FOOT ? CHARACTER_TILE : SHIP_TILE, viewMode);
                }

                System.out.print(tile);
            }
            System.out.println();
        }
        printStatus(character);
    }

    static boolean wallOnSight(int[] character, int row, int column, int[][] world) {

        int currentX, currentY, destinationX, destinationY;
        currentX = character[ROW];
        currentY = character[COLUMN];
        destinationX = row;
        destinationY = column;

        int deltaX = Math.abs(destinationX - currentX);
        int deltaY = Math.abs(destinationY - currentY);

        int stepX = currentX < destinationX ? 1 : -1;
        int stepY = currentY < destinationY ? 1 : -1;

        int error = deltaX - deltaY;
        int errorUpdate;

        boolean destinationReached = false;
        while (!destinationReached) {
            if (currentX == destinationX && currentY == destinationY) {
                destinationReached=true;
            } else {
                if (world[currentX][currentY] == 1) {
                    return true;
                } else {
                    errorUpdate = 2 * error;
                    if (errorUpdate > -deltaY) {
                        error = error - deltaY;
                        currentX = currentX + stepX;
                    }
                    if (errorUpdate < deltaX) {
                        error = error + deltaX;
                        currentY = currentY + stepY;
                    }
                }
            }
        }

        return false;
    }
    

    static void printStatus(int[] character) {

        printLine();
        System.out.print("HORA: [" + (int) hour + "] ");
        System.out.print("/ (" + character[ROW] + "," + character[COLUMN] + ")");
        System.out.println(" / SKIN[" + viewMode + "] / ViewRange: " + viewRange);
        printLine();
    }

    static void printLine() {

        System.out.println(getTileView(LINE_TILE, viewMode).repeat(viewPort * 2 + 1));
    }

    static String getTileView(int tile, int viewMode) {

        switch (viewMode) {
            case NORMAL_VIEW:
                return normalMap(tile);
            case MONOCHROME_VIEW:
                return monochromeMap(tile);
            case RAW_VIEW:
                return rawMap(tile);
            case COLLISION_VIEW:
                return "" + (canTraverse(tile, 0) ? 0 : 1);
        }
        return "";
    }

    static String rawMap(int tile) {

        return "" + tile;
    }

    static String monochromeMap(int tile) {

        String tiles[] = {
                "( )",
                "[#]",
                ".:.",
                "~ ~",
                "[=]",
                "555",
                "oO*",
                "777",
                "%%%",
                "/\\^",
                "\\Ô/",
                "() ",
                "   ",
                "\\_/",
                "   ",
                "---"
        };
        return tiles[tile];
    }

    static String normalMap(int tile) {

        String tiles[] = {
                BLACK_BACKGROUND_BRIGHT                 + monochromeMap(tile) + RESET,
                WHITE + WHITE_BACKGROUND                + monochromeMap(tile) + RESET,
                GREEN_BOLD + GREEN_BACKGROUND           + monochromeMap(tile) + RESET,
                WHITE + BLUE_BACKGROUND                 + monochromeMap(tile) + RESET,
                WHITE + GREEN_BACKGROUND_BRIGHT         + monochromeMap(tile) + RESET,
                RED + GREEN_BACKGROUND_BRIGHT           + monochromeMap(tile) + RESET,
                YELLOW_BRIGHT + GREEN_BACKGROUND        + monochromeMap(tile) + RESET,
                ""                                      + monochromeMap(tile) + RESET,
                GREEN_BOLD + YELLOW_BACKGROUND          + monochromeMap(tile) + RESET,
                RED_UNDERLINED + YELLOW_BACKGROUND      + monochromeMap(tile) + RESET,
                ""                                      + monochromeMap(tile) + RESET,
                YELLOW_BOLD_BRIGHT + BLUE_BACKGROUND    + monochromeMap(tile) + RESET,
                BLUE_BACKGROUND                         + monochromeMap(tile) + RESET,
                ""                                      + monochromeMap(tile) + RESET,
                ""                                      + monochromeMap(tile) + RESET,
                ""                                      + monochromeMap(tile) + RESET
        };

        return tiles[tile];
    }

    static boolean canTraverse(int tile, int transport) {
        switch (transport) {
            case ON_FOOT:
                return (tile % 2 == 0);
            case ON_SHIP:
                return tile == 3 ? true : false;
        }
        return false;
    }

    static void move(int[] character, int direction, int[][] world) {

        int previousRow = character[ROW];
        int previousColumn = character[COLUMN];

        character[ROW] += MOVEMENTS[direction][ROW];
        character[COLUMN] += MOVEMENTS[direction][COLUMN];

        if (!canTraverse(world[character[ROW]][character[COLUMN]], character[2])) {
            character[ROW] = previousRow;
            character[COLUMN] = previousColumn;
        }

        if (character[ROW] < rowMin) {
            character[ROW] = character[ROW] + 1;
        }
        if (character[ROW] > rowMax) {
            character[ROW] = character[ROW] - 1;
        }
        if (character[COLUMN] < columnMin) {
            character[COLUMN] = character[COLUMN] + 1;
        }
        if (character[COLUMN] > columnMax) {
            character[COLUMN] = character[COLUMN] - 1;
        }
    }

    static void getAction(int[] character, int[][] world) {

        switch (getMovement()) {
            case UP:
                move(character, UP, world);
                break;
            case DOWN:
                move(character, DOWN, world);
                break;
            case LEFT:
                move(character, LEFT, world);
                break;
            case RIGHT:
                move(character, RIGHT, world);
                break;
            case EXIT:
                isPlaying = !isPlaying;
                break;
            case CHANGE_VIEW:
                changeView();
                break;
            case CHANGE_TRANSPORT:
                changeTransport(character);
                break;
            case NOTHING:
                break;
        }
    }

    private static void changeTransport(int[] character) {

        character[TRANSPORT] = (character[TRANSPORT] == ON_FOOT ? ON_SHIP : ON_FOOT);
    }

    static void changeView() {

        viewMode++;
        if (viewMode > 3) {
            viewMode = 0;
        }
    }

    static int getMovement() {

        switch (askChar()) {
            case 's', 'S', '8':
                return DOWN;
            case 'w', 'W', '2':
                return UP;
            case 'a', 'A', '4':
                return LEFT;
            case 'd', 'D', '6':
                return RIGHT;
            case 'f', 'F':
                return EXIT;
            case 'v', 'V':
                return CHANGE_VIEW;
            case 'q', 'Q':
                return CHANGE_TRANSPORT;
        }
        return NOTHING;
    }

    static char askChar() {

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine() + "x"; // Este es un caso que justifica un comentario!
        return userInput.charAt(0); // Lo comentamos en clase ;)
    }

    static void cleanScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void printSky() {

        printLine();
        for (int i = 0; i < viewPort * 2 + 1; i = i + 1) {
            int sunPosition = -1;
            if ((hour > 6) && (hour <= 18)) {
                sunPosition = (int) (viewPort * 2 * (1 - (hour - 7) / 12.0));

            }
            if (i == sunPosition) {
                System.out.print(getTileView(SUN_TILE, viewMode));
            } else {
                System.out.print(getTileView(SKY_TILE, viewMode));
            }
        }
        System.out.println();
        printLine();
    }

    public static final String RESET = "\033[0m";

    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";

    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

    public static final String BLACK_BRIGHT = "\033[0;90m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    public static final String BLUE_BRIGHT = "\033[0;94m";
    public static final String PURPLE_BRIGHT = "\033[0;95m";
    public static final String CYAN_BRIGHT = "\033[0;96m";
    public static final String WHITE_BRIGHT = "\033[0;97m";

    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";

    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";
}