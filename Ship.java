class Ship{
    private int length;
    private int posX;
    private int posY;
    private String orientation;

    Ship(int length, String orientation, int posY, int posX){
        this.length = length;
        this.orientation = orientation.toUpperCase();
        this.posX = posX;
        this.posY = posY;
    }

    public int getLength(){
        return this.length;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public String getOrientation(){
        return this.orientation;
    }

}