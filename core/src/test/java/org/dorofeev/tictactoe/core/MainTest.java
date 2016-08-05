package org.dorofeev.tictactoe.core;

import org.dorofeev.tictactoe.core.exception.TicTacToeException;

import java.util.Scanner;

/**
 * This class should be used as manual testing. Rename it to main and run
 * @author Yury Dorofeev
 * @since  2016-08-02
 */
public class MainTest {
    private static int MAX_NUMBER_OF_GAMES_FOR_BATTLE_3X3 = 39000;

    public static void main(String[] args) throws TicTacToeException {

        System.out.println("..Computer learning");

        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        int numberOfGames = 0;
        while (numberOfGames < MAX_NUMBER_OF_GAMES_FOR_BATTLE_3X3) {
            if( playNewGame(game, GameFigure.X)) {
                numberOfGames ++;
            }
        }

        System.out.println("..Lets play TicTacToe 3x3 with a smart computer!");

        int position;
        GameStatus status;

        Scanner scanner = new Scanner(System.in);
        System.out.println("..please make your move: ");

        while (scanner.hasNext()) {
            position = scanner.nextInt();

            status = makeMoveX(game, position);
            if(status != GameStatus.CONTINUE) {
                game.gameOver(status);
                System.out.println("..X " + status);
                System.out.println("..lets continue, make your move");

            } else {
                status = makeMoveO(game);
                if (status != GameStatus.CONTINUE) {
                    game.gameOver(status);
                    System.out.println("..O " + status);
                    System.out.println("..lets continue, make your move");
                }
            }
        }
    }

    private static GameStatus makeMoveO(Game game) throws TicTacToeException {
        int position = game.makeNewMove(GameFigure.O);
        System.out.println("..O made a move: " + position);
        return game.getGameStatus();
    }

    private static GameStatus makeMoveX(Game game, int position) throws TicTacToeException {
        game.makeNewMove(GameFigure.X, position);
        return game.getGameStatus();
    }

    private static boolean playNewGame(Game game, GameFigure figure) throws TicTacToeException {
        GameStatus status = game.getGameStatus();
        if(status == GameStatus.CONTINUE) {
            game.makeNewMove(figure);
            return playNewGame(game, figure == GameFigure.X ? GameFigure.O : GameFigure.X);
        } else {
            game.gameOver(status);
            return true;
        }
    }
}
