package org.dorofeev.tictactoe.server.model;

/**
 * @author Yury Dorofeev
 * @since 03/08/16
 */
public class GameResponse {
    private String status;
    private String position;

    public GameResponse() {}

    public GameResponse(String status, String position) {
        this.status = status;
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "status: " + status + ", position: " + position;
    }
}
