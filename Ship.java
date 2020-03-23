import java.util.ArrayList;

class Ship{
    private int length;
    private int posX;
    private int posY;
    private String orientation;
    private ArrayList<Square> listOfFields;

    Ship(int length, String orientation, int posX, int posY){
        this.length = length;
        this.orientation = orientation.toUpperCase();
        this.posX = posX;
        this.posY = posY;
        this.listOfFields = new ArrayList<>();
    }

    public int getLength(){
        return this.length;
    }

    public void addSquareToList(Square field){
        this.listOfFields.add(field);

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

    public ArrayList<Square> getListOfFields(){
        return this.listOfFields;
    }



}