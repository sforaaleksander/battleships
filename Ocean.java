import java.util.ArrayList;

class Ocean {
    private int oceanSize;
    private Square[][] oceanBoard;
    private ArrayList<Square> allShipSquares;
    private ArrayList<Square> allUnavailableSquares;

    Ocean(int oceanSize) {
        this.oceanSize = oceanSize;
        oceanBoard = new Square[oceanSize][oceanSize];
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                oceanBoard[i][j] = new Square(j, i);
            }
        }
    }

    public Square[][] getTable() {
        return this.oceanBoard;
    }

    public ArrayList<Square> getAllShipSquares(){
        return this.allShipSquares;
    }

    public void addToShipSquares(Square field){
        this.allShipSquares.add(field);
    }

    public void placeOnTable(Ship newShip) {
        if (newShip.getOrientation().equals("HORIZONTAL")) {
            for (int i = newShip.getPosX(); i < newShip.getPosX() + newShip.getLength(); i++) {
                oceanBoard[newShip.getPosY()][i].changeStatus("SHIP");
                newShip.addSquareToList(new Square(i, newShip.getPosY()));
                this.addToShipSquares(new Square(i, newShip.getPosY()));
            }
        } else {
            for (int i = newShip.getPosY(); i < newShip.getPosY() + newShip.getLength(); i++) {
                oceanBoard[i][newShip.getPosX()].changeStatus("SHIP");
                newShip.addSquareToList(new Square(newShip.getPosX(), i));
                this.addToShipSquares(new Square(newShip.getPosX(), i));
            }
        } 
    }

    public String toString() {
        String output = " ";
        String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        for (int x = 1; x < 11; x++) {
            output += " " +x;
        }
        output += "\n";
        for (int i = 0; i < this.oceanSize; i++) {
            output += letters[i];
            for (int j = 0; j < this.oceanSize; j++) {
                output += oceanBoard[i][j].toString();
            }
            output += "\n";
        }
        return output;
    }


    public void setFieldsUnavailable(){
        for (Square element : getAllShipSquares()){
            for (int i = -1; i < 2; i++){
                for (int j = -1; j <2; j++){
                    int x = element.getPosX() + j;
                    int y = element.getPosY() + i;
                    if (x >= 0 && x < 11 && y >= 0 && y < 11){
                    this.getTable()[x][y].setUnavailable();
                    }
                }
            }

        }
    }

}