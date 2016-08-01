package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.NodeNotFoundException;
import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateStatusException;

import java.util.Scanner;

/**
 * Created by yury on 01/08/16.
 */
public class main {
    public static void main(String[] args) throws TicTacToeException, NodeNotFoundException, UpdateStatusException {

        System.out.println("..Lets play TicTacToe 3x3");

        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        int position;

        Scanner scanner = new Scanner(System.in);
        System.out.println("..please make your move");

        while (scanner.hasNext()) {
            position = scanner.nextInt();
            game.makeNewMove(GameFigure.X, position);
            GameStatus status = game.getGameStatus();
            if(status == GameStatus.CONTINUE) {
                game.makeNewMove(GameFigure.O);
            } else {
                game.gameOver(status);
                System.out.println("..pobedili....");
                scanner.close();
            }
        }
    }
}
