class Ocean{
    private int oceanSize;
    private Square[][] theOcean;


    Ocean(int oceanSize){
        this.oceanSize = oceanSize;
        theOcean = new Square[oceanSize][oceanSize];
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                theOcean[i][j] = new Square();
            }
        }
    }

    public Square[][] getTable(){
        return this.theOcean;
    }

    public void placeOnTable(Ship newShip){
        if (newShip.getOrientation().equals("HORIZONTAL")){
        for (int i=newShip.getPosX(); i < newShip.getPosX() + newShip.getLength(); i ++){
            theOcean[newShip.getPosY()][i].mark();
        }
    } else{
        for (int i = newShip.getPosY(); i < newShip.getPosY() + newShip.getLength(); i ++){
            theOcean[i][newShip.getPosX()].mark();
        }

        }
    }
    


    public String toString(){
        String output = "";
        for (int i=0; i<this.oceanSize; i++){
            for (int j=0; j<this.oceanSize; j++){
                output += theOcean[i][j].toString();
            }
            output += "\n";
        }
    return output;
    }

    
}