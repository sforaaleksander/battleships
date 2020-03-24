import java.util.ArrayList;

class Ocean {
    private int oceanSize;
    private Square[][] oceanBoard;
    private ArrayList<Square> allShipSquares;
    private ArrayList<Square> allUnavailableSquares;

    Ocean(int oceanSize) {
        this.oceanSize = oceanSize;
        this.oceanBoard = initializeOceanBoard(oceanSize);

    }

    public Square[][] initializeOceanBoard(int oceanSize) {
        oceanBoard = new Square[oceanSize][oceanSize];
        allShipSquares = new ArrayList<>();
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                oceanBoard[i][j] = new Square(j, i);
            }
        }
        return oceanBoard;
    }

    public Square[][] getOceanBoard() {
        return this.oceanBoard;
    }

    public int getOceanSize() {
        return this.oceanSize;
    };

    public Square getSquareByPos(int posY, int posX) {
        return this.getOceanBoard()[posY][posX];
    }

    public ArrayList<Square> getAllShipSquares() {
        return this.allShipSquares;
    }

    public void addToShipSquares(Square field) {
        this.allShipSquares.add(field);
    }

    public boolean placeOnTable(Ship newShip) {
        if (Engine.checkIfAvailable(this.getOceanBoard()[newShip.getPosX()][newShip.getPosY()])
                && Engine.checkIfFitsOnMap(newShip, this.getOceanBoard())) {

            if (newShip.getOrientation().equals("HORIZONTAL")) {
                for (int i = newShip.getPosX(); i < newShip.getPosX() + newShip.getLength(); i++) {
                    this.getOceanBoard()[newShip.getPosY()][i].changeStatus("SHIP");
                    Square field = this.getSquareByPos(i, newShip.getPosY());
                    newShip.addSquareToList(field);
                    this.addToShipSquares(field);
                }
            } else {
                for (int i = newShip.getPosY(); i < newShip.getPosY() + newShip.getLength(); i++) {
                    this.getOceanBoard()[i][newShip.getPosX()].changeStatus("SHIP");
                    Square field = this.getSquareByPos(newShip.getPosX(), i);
                    newShip.addSquareToList(field);
                    this.addToShipSquares(field);
                }
            }
            return true;
        } else
            return false;
    }

    public String toString() {
        String output = " ";
        String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        for (int x = 1; x < 11; x++) {
            output += " " + x;
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

    public void setFieldsUnavailable() {
        for (Square element : getAllShipSquares()) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int x = element.getPosX() + j;
                    int y = element.getPosY() + i;
                    if (x >= 0 && x < 11 && y >= 0 && y < 11) {
                        this.getOceanBoard()[x][y].setUnavailable();
                    }
                }
            }

        }
    }

}