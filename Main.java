class Main {

    public static void main(String args[]) {

        int oceanSize = 10;
        Ocean ocean = new Ocean(oceanSize);

        
        System.out.println(ocean.toString());

        Ship fishBoat = new Ship(2, "horizontal", 3, 3);

        Ship burza = new Ship(4, "horizontal", 5, 1);

        ocean.placeOnTable(burza);

        ocean.placeOnTable(fishBoat);

        System.out.println(ocean.toString());


    
    }
}