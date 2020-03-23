class Square{
    private boolean marked;

    Square(){
    this.marked = false;
    }

    public void mark(){
        this.marked = true;
    }

    public void unmark(){
        this.marked = false;
    }


    public String toString(){
        String emptySquare = " \u25A1";
        String fullSquare = " \u25A3";
        if (this.marked){
            return fullSquare;
        } return emptySquare;
    }
}