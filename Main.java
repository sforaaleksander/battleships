import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static Scanner scan;
    public static Map<String, Integer> ships;
    public static void main(String args[]) {
        initialize();
    }

    public static void initialize(){
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());
        ships = new HashMap<>();
        ships.put("Carrier", 5);
        ships.put("Battleship", 4);
        ships.put("Cruiser", 3);
        ships.put("Submarine", 3);
        ships.put("Destroyer", 2);
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