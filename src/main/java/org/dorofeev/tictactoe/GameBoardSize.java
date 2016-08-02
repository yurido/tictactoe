package org.dorofeev.tictactoe;

/**
 * GameBoardSize collection
 * @author Yury Dorofeev
 * @version 2015-12-02
 */
public enum GameBoardSize {
    SMALL(9),
    MEDIUM(16),
    LARGE(25);
    private final int value;
    GameBoardSize(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
