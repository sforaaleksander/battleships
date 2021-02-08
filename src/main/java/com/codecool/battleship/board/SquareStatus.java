package com.codecool.battleship.board;

public enum SquareStatus {
    EMPTY(" \u25A1"), SHIP(" \u25A3"), HIT(" \u2612"), MISSED(" \u22A1");

    private final String graphic;

    SquareStatus(String graphic) {
        this.graphic = graphic;
    }

    public String getGraphic() {
        return graphic;
    }
}
