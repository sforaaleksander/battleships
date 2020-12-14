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
        IO.scan.close();
    }

    public static void initializeMain(){
        Engine.clearScreen();
        isRunning = true;
        IO.initializeScanner();
        Engine.initializeHashMaps();

        mainMenuList = new String[]{"PLAY NEW GAME", "ABOUT", "SHOW HIGHSCORES", "EXIT"};
        mainMenu = new HashMap<>();
        mainMenu.put(1, () -> newGameMenu());
        mainMenu.put(2, () -> aboutDisplay());
        mainMenu.put(3, () -> highScoresDisplay());
        mainMenu.put(4, () -> exitGame());

        newGameMenuList = new String[] {"PLAYER VS. PLAYER", "PLAYER VS. COMPUTER", "COMPUTER VS. COMPUTER"};
        playGameMenu = new HashMap<>();
        playGameMenu.put(1, () -> new Game("pvp"));
        playGameMenu.put(2, () -> new Game("pvc"));
        playGameMenu.put(3, () -> new Game("cvc"));
    }

    public static void mainMenu(){
        Engine.clearScreen();
        System.out.println("THE BATTLESHIP GAME\n");
        for (int i = 0; i < mainMenuList.length; i++){
            System.out.println(i + 1 + ". " + mainMenuList[i]);
        } int userChoice = IO.gatherIntInput("", 4);
        Engine.clearScreen();
        mainMenu.get(userChoice).run();        
    }

    public static void newGameMenu(){
        Engine.clearScreen();
        System.out.println("PLAY NEW GAME\n");
        for (int i = 0; i < newGameMenuList.length; i++){
            System.out.println(i + 1 + ". " + newGameMenuList[i]);
        } int userChoice = IO.gatherIntInput("", 3);
        Engine.clearScreen();
        playGameMenu.get(userChoice).run();
    }

    public static void aboutDisplay(){   
    }
 
    public static void highScoresDisplay(){
        Engine.clearScreen();
        System.out.println("HIGH SCORES BOARD\n");
        System.out.println(IO.loadHighScores());
        IO.gatherEmptyInput("");
    }

    public static void exitGame(){
        isRunning = false;
    }
}