import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    private static Scanner scan;

    public static void main(String args[]) {
        initialize();
        
    }

    private static String gatherInput(String title) {
        System.out.println(title);
        String userInput = scan.next().toUpperCase();
        return userInput;
    }

    private static int gatherIntInput(String title) {
        System.out.println(title);
        int userInput = scan.nextInt();
        return userInput;
    }

    private static void initialize(){
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());

        int oceanSize = 10;
        Ocean ocean = new Ocean(oceanSize);

        Map<String, Integer> ships = new HashMap<>();
        ships.put("Carrier", 5);
        ships.put("Battleship", 4);
        ships.put("Cruiser", 3);
        ships.put("Submarine", 3);
        ships.put("Destroyer", 2);

        for (String key : ships.keySet()) {
            System.out.println("Place the " + key + "ship on your board!");
            String userOrientation = gatherInput("Type [horizontal] or [vertical] for your ship placement.");
            int posX = gatherIntInput("Type in the X position.");
            int posY = gatherIntInput("Type in the Y position.");
            int length = ships.get(key);
            Ship newShip = new Ship(length, userOrientation, posX, posY);
            ocean.placeOnTable(newShip);
            ocean.setFieldsUnavailable();
        }
        System.out.println(ocean.toString());
    }

 
}

// System.out.println(ocean.toString());
// Ship fishBoat = new Ship(2, "horizontal", 3, 3);
// Ship burza = new Ship(4, "horizontal", 5, 0);
// Ship aircraft = new Ship(6, "vertical", 1, 8);
// ocean.placeOnTable(aircraft);
// ocean.placeOnTable(burza);
// ocean.placeOnTable(fishBoat);
// System.out.println(ocean.toString());