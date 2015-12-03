package org.dorofeev.tictactoe;

/**
 * Game Node
 * @author Yury Dorofeev
 * @version 2015-12-03
 */
public class GameBoardNode {
    private GameFigure figure;
    private int position;

    public GameBoardNode(GameFigure figure, int position){
        this.figure = figure;
        this.position = position;
    }

    public GameFigure getFigure() {
        return figure;
    }

    public int getPosition() {
        return position;
    }
}
