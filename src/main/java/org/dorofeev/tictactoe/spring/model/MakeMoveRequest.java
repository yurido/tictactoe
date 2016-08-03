package org.dorofeev.tictactoe.spring.model;

/**
 * Created by yury on 03/08/16.
 */
public class MakeMoveRequest {
    private String figure;
    private String position;

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
