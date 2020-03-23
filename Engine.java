class Engine {

    public static boolean checkPlacement(Ship theShip, Square[][] oceanBoard) {
            if (theShip.getOrientation().equals("HORIZONTAL")) {
                if (theShip.getPosX() == 0){
                for (int i = theShip.getPosX(); i < theShip.getPosX() + theShip.getLength() + 1; i++) {
                    if (oceanBoard[theShip.getPosY()][i].getStatus().equals("SHIP")){
                        return false;
                    }
                    if (oceanBoard[theShip.getPosY()][i].getStatus().equals("SHIP")){
                        return false;
                    }
                }
            }
            } else {
                for (int i = theShip.getPosY(); i < theShip.getPosY() + theShip.getLength(); i++) {
                    if (oceanBoard[i][theShip.getPosX()].getStatus().equals("SHIP")){
                        return false;
                    };
                }
    
            }
        return true;}


    public static boolean checkIfAvailable(Square field){
        if (field.getIsAvailable()){
            return true;
        } return false;
    }

    public static boolean checkIfFitsOnMap(Ship theShip, Square[][] table) {
        if (theShip.getOrientation().equals("HORIZONTAL")) {
            if (theShip.getLength() + theShip.getPosX() < table.length && theShip.getPosX() >= 0) {
                return true;
            }
            return false;
        } else {
            if (theShip.getLength() + theShip.getPosY() < table.length && theShip.getPosY() >= 0) {
                return true;
            }
            return false;
        }
    }
}