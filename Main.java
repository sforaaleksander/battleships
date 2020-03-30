import java.util.HashMap;
import java.util.Map;

class Main {
    public static boolean isRunning;
    public static Map<Integer, Runnable> mainMenu;
    public static String[] mainMenuList;
    public static Map<Integer, Runnable> playGameMenu;
    public static String[] newGameMenuList;

    public static void main(String args[]) {
        initializeMain();
        while (isRunning){
        mainMenu();
        }
        Engine.scan.close();
    }

    public static void initializeMain(){
        Game.listOfPlayers.clear();
        Engine.clearScreen();
        isRunning = true;
        Engine.initializeScanner();
        Engine.initializeHashMaps();

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
        playGameMenu.put(3, () -> startCvCGame());
    }

    public static void mainMenu(){
        Engine.clearScreen();
        System.out.println("THE BATTLESHIP GAME\n");
        for (int i = 0; i < mainMenuList.length; i++){
            System.out.println(i + 1 + ". " + mainMenuList[i]);
        } int userChoice = Engine.gatherIntInput("", 4);
        Engine.clearScreen();
        mainMenu.get(userChoice).run();        
    }

    public static void newGameMenu(){
        Engine.clearScreen();
        System.out.println("PLAY NEW GAME\n");
        for (int i = 0; i < newGameMenuList.length; i++){
            System.out.println(i + 1 + ". " + newGameMenuList[i]);
        } int userChoice = Engine.gatherIntInput("", 3);
        Engine.clearScreen();
        playGameMenu.get(userChoice).run();
    }

    public static void aboutDisplay(){   
    }
 
    public static void highScoresDisplay(){
        Engine.clearScreen();
        System.out.println("HIGH SCORES BOARD\n");
        System.out.println(Engine.loadHighScores());
        Engine.gatherEmptyInput("");
    }

    public static void exitGame(){
        isRunning = false;
    }

    public static void startPvPGame(){
        Game newGame = new Game(true, true);
        newGame.gamePlay("pvpMode");
    }

    public static void startPvCGame(){
        Game newGame = new Game(true, false);
        newGame.gamePlay("pvcMode");
    }

    public static void startCvCGame(){
        Game newGame = new Game(false, false);
        newGame.gamePlay("cvcMode");

    }
}