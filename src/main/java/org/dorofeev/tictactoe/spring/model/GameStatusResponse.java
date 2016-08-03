package org.dorofeev.tictactoe.spring.model;

/**
 * @author Yury Dorofeev
 * @since 03/08/16
 */
public class GameStatusResponse {
    private GameStatus status;
    private int position;

    public GameStatusResponse(GameStatus status, int position) {
        this.status = status;
        this.position = position;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getPosition() {
        return position;
    }
}
