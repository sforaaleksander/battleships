import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static Scanner scan;
    public static boolean isRunning;
    public static Map<String, Integer> ships;
    public static Map<Integer, Runnable> mainMenu;
    public static String[] mainMenuList;
    public static Map<Integer, Runnable> playGameMenu;
    public static String[] newGameMenuList;

    public static void main(String args[]) {
        initialize();
        while (isRunning){
        mainMenu();
        }
    }

    public static void initialize(){
        isRunning = true;
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());

        ships = new HashMap<>();
        ships.put("Carrier", 5);
        // ships.put("Battleship", 4);
        // ships.put("Cruiser", 3);
        // ships.put("Submarine", 3);
        // ships.put("Destroyer", 2);

        mainMenuList = new String[]{"PLAY NEW GAME", "ABOUT", "SHOW HIGHSCORES", "EXIT"};
        mainMenu = new HashMap<>();
        mainMenu.put(1, () -> newGameMenu());
        mainMenu.put(2, () -> aboutDisplay());
        mainMenu.put(3, () -> highScoresDisplay());
        mainMenu.put(4, () -> exitGame());

        newGameMenuList = new String[] {"PLAYER VS. PLAYER", "PLAYER VS. COMPUTER", "COMPUTER VS. COMPUTER"};
        playGameMenu = new HashMap<>();
        playGameMenu.put(1, () -> startPvPGame());
        playGameMenu.put(2, () -> startPvCGame());
        playGameMenu.put(3, () -> startPvPGame());
    }

    public static void mainMenu(){
        System.out.println("THE BATTLESHIP GAME\n");
        for (int i = 0; i < mainMenuList.length; i++){
            System.out.println(i + 1 + ". " + mainMenuList[i]);
        } int userChoice = Engine.gatherIntInput("");
        mainMenu.get(userChoice).run();
    }

    public static void newGameMenu(){
        System.out.println("PLAY NEW GAME\n");
        for (int i = 0; i < newGameMenuList.length; i++){
            System.out.println(i + 1 + ". " + newGameMenuList[i]);
        } int userChoice = Engine.gatherIntInput("");
        playGameMenu.get(userChoice).run();
    }

    public static void aboutDisplay(){   
    }
 
    public static void highScoresDisplay(){  
    }

    public static void exitGame(){
        isRunning = false;
    }

    public static void startPvPGame(){
        Game newGame= new Game(true, true);
        newGame.pvpMode();

    }

    public static void startPvCGame(){}

    public static void startCvCGame(){}
}

// System.out.println(ocean.toString());
// Ship fishBoat = new Ship(2, "horizontal", 3, 3);
// Ship burza = new Ship(4, "horizontal", 5, 0);
// Ship aircraft = new Ship(6, "vertical", 1, 8);
// ocean.placeOnTable(aircraft);
// ocean.placeOnTable(burza);
// ocean.placeOnTable(fishBoat);
// System.out.println(ocean.toString());