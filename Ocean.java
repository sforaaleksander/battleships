class Ocean {
    private int oceanSize;
    private Square[][] oceanBoard;

    Ocean(int oceanSize) {
        this.oceanSize = oceanSize;
        oceanBoard = new Square[oceanSize][oceanSize];
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                oceanBoard[i][j] = new Square();
            }
        }
    }

    public Square[][] getTable() {
        return this.oceanBoard;
    }

    public void placeOnTable(Ship newShip) {
        if (newShip.getOrientation().equals("HORIZONTAL")) {
            for (int i = newShip.getPosX(); i < newShip.getPosX() + newShip.getLength(); i++) {
                oceanBoard[newShip.getPosY()][i].changeStatus("SHIP");
            }
        } else {
            for (int i = newShip.getPosY(); i < newShip.getPosY() + newShip.getLength(); i++) {
                oceanBoard[i][newShip.getPosX()].changeStatus("SHIP");
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

}