package org.dorofeev.tictactoe.spring.controller;

import org.dorofeev.tictactoe.lib.Game;
import org.dorofeev.tictactoe.lib.GameBoardSize;
import org.dorofeev.tictactoe.lib.GameRegime;
import org.dorofeev.tictactoe.lib.exception.TicTacToeException;
import org.dorofeev.tictactoe.spring.model.GameFigure;
import org.dorofeev.tictactoe.spring.model.GameStatus;
import org.dorofeev.tictactoe.spring.model.GameStatusResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

/**
 * @author Yury Dorofeev
 * @since 02/08/16
 */
@Controller
public class ControllerGame {
    private static final int MAX_NUMBER_OF_GAMES = 39000;
    private Game game;

    @RequestMapping("startNewGame")
    public void startNewGame() throws TicTacToeException {
        game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
    }

    @RequestMapping("makeNewMoveWithPosition")
    public GameStatusResponse makeNewMoveWithPosition(@WebParam GameFigure figure, @WebParam int position) throws TicTacToeException {
        game.makeNewMove(mapGameFigure(figure), position);
        return getGameStatusResponse(position);
    }

    @RequestMapping("makeNewMove")
    public GameStatusResponse makeNewMove(@WebParam GameFigure figure) throws TicTacToeException {
        int position = game.makeNewMove(mapGameFigure(figure));
        return getGameStatusResponse(position);
    }

    @RequestMapping("makeComputerSmart")
    public void makeSmart() throws TicTacToeException {
        int numberOfGames = 0;

        while (numberOfGames < MAX_NUMBER_OF_GAMES) {
            if (playNewGame(game, org.dorofeev.tictactoe.lib.GameFigure.X)) {
                numberOfGames++;
            }
        }
    }

    private org.dorofeev.tictactoe.lib.GameFigure mapGameFigure(GameFigure figure) {
        if (figure == GameFigure.O) {
            return org.dorofeev.tictactoe.lib.GameFigure.O;
        } else {
            return org.dorofeev.tictactoe.lib.GameFigure.X;
        }
    }

    private GameStatusResponse mapGameStatusResponse(org.dorofeev.tictactoe.lib.GameStatus status, int position) throws TicTacToeException {
        return new GameStatusResponse(mapGameStatus(status), position);
    }

    private GameStatus mapGameStatus(org.dorofeev.tictactoe.lib.GameStatus status) throws TicTacToeException {
        switch (status) {
            case WIN:
                return GameStatus.WIN;
            case DRAW:
                return GameStatus.DRAW;
            case CONTINUE:
                return GameStatus.CONTINUE;
        }
        throw new TicTacToeException("Status not found");
    }

    private GameStatusResponse getGameStatusResponse(int position) throws TicTacToeException {
        org.dorofeev.tictactoe.lib.GameStatus status = game.getGameStatus();
        if(status != org.dorofeev.tictactoe.lib.GameStatus.CONTINUE) {
            game.gameOver(status);
        }
        return mapGameStatusResponse(status, position);
    }

    private boolean playNewGame(Game game, org.dorofeev.tictactoe.lib.GameFigure figure) throws TicTacToeException {
        org.dorofeev.tictactoe.lib.GameStatus status = game.getGameStatus();
        if(status == org.dorofeev.tictactoe.lib.GameStatus.CONTINUE) {
            game.makeNewMove(figure);
            return playNewGame(game, figure == org.dorofeev.tictactoe.lib.GameFigure.X ? org.dorofeev.tictactoe.lib.GameFigure.O : org.dorofeev.tictactoe.lib.GameFigure.X);
        } else {
            game.gameOver(status);
            return true;
        }
    }

}

