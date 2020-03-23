import java.util.HashMap;
import java.util.Map;

class Square{
    private String status;
    private int posX;
    private int posY;

    Square(int posX, int posY){
    this.status = "EMPTY";
    this.posX = posX;
    this.posY = posY;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public String getStatus(){
        return this.status;
    }

    public void changeStatus(String status){
        this.status = status;
    }

    public String toString(){
        String emptyMark = " \u25A1";
        String shipMark = " \u25A3";
        String hitMark = " \u2612";
        String missedMark = " \u22A1";  // " \u29C7"; 
        Map<String, String> squareGraphic = new HashMap<>();
        squareGraphic.put("EMPTY", emptyMark);
        squareGraphic.put("SHIP", shipMark);
        squareGraphic.put("HIT", hitMark);
        squareGraphic.put("MISSED", missedMark);
        return squareGraphic.get(this.getStatus());
    }
}