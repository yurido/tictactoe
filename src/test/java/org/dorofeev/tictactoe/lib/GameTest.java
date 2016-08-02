package org.dorofeev.tictactoe.lib;

import org.dorofeev.tictactoe.lib.exception.TicTacToeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

/**
 * @author Yury Dorofeev
 * @version 2015-12-04
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    @Rule
    public ExpectedException expectException = ExpectedException.none();
    private static int MAX_NUMBER_OF_GAMES_FOR_BATTLE_3X3 = 39000;
    private static int MAX_NUMBER_OF_GAMES_FOR_LEARNING_3X3 = 39000;
    private static long MAX_NUMBER_OF_NODES_3X3 = 362880;
    private static long MAX_REAL_NUMBER_OF_NODES_BATTLE_3X3 = 94968;
    private static long MAX_REAL_NUMBER_OF_NODES_LEARNING_3X3 = 94968;

    @Test
    public void testWinDiagonalsRight() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 2);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 6);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinDiagonalsLeft() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 0);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinDiagonalsLeft2() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 0);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinDiagonalsRight2() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 6);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 2);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinColumn1() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 0);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 3);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 6);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinColumn2() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 1);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 7);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinColumn3() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 2);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 5);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinColumn4() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 5);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 2);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinRow1() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 0);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 1);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 2);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinRow2() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 3);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 5);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testWinRow3() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 6);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 7);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }


    @Test
    public void testWinRow4() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 7);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 6);
        assertEquals(GameStatus.WIN, game.getGameStatus());
    }

    @Test
    public void testDraw() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);
        game.makeNewMove(GameFigure.X, 0);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 2);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.X, 7);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());

        game.makeNewMove(GameFigure.O, 1);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.O, 3);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.O, 5);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.O, 6);
        assertEquals(GameStatus.CONTINUE, game.getGameStatus());
        game.makeNewMove(GameFigure.O, 8);
        assertEquals(GameStatus.DRAW, game.getGameStatus());
    }

    @Test
    public void testOLearningInSmallBattleRegime() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);

        // game 1
        int position = game.makeNewMove(GameFigure.X);
        assertEquals(0, position);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(1, position);
        position = game.makeNewMove(GameFigure.X);
        assertEquals(2, position);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(3, position);
        position = game.makeNewMove(GameFigure.X);
        assertEquals(4, position);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(5, position);
        position = game.makeNewMove(GameFigure.X);
        assertEquals(6, position);
        assertEquals(GameStatus.WIN, game.getGameStatus());
        game.gameOver(GameStatus.WIN);

        // game 2
        game.makeNewMove(GameFigure.X, 0);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(1, position);
        game.makeNewMove(GameFigure.X, 2);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(3, position);
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(1 ,game.getTree().getCurrentNode().getChildren().size() );
        position = game.makeNewMove(GameFigure.O);
        // computer should chose position 6 because of position 5 means lose for ´O´
        assertEquals(6, position);
        // now there should be 2 children for X with position 4
        assertEquals(2, game.getTree().getCurrentNode().getParent().getChildren().size() );
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.WIN, game.getGameStatus());
        game.gameOver(GameStatus.WIN);

        // game 3
        game.makeNewMove(GameFigure.X, 0);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(1, position);
        game.makeNewMove(GameFigure.X, 2);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(3, position);
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(2 ,game.getTree().getCurrentNode().getChildren().size() );
        position = game.makeNewMove(GameFigure.O);
        // computer should chose position 7 because of positions 5 and 6 mean lose for ´O´
        assertEquals(7, position);
        // now there should be 3 children for X with position 4
        assertEquals(3, game.getTree().getCurrentNode().getParent().getChildren().size() );
        game.makeNewMove(GameFigure.X, 8);
        assertEquals(GameStatus.WIN, game.getGameStatus());
        game.gameOver(GameStatus.WIN);

        // game 4
        game.makeNewMove(GameFigure.X, 0);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(1, position);
        game.makeNewMove(GameFigure.X, 2);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(3, position);
        game.makeNewMove(GameFigure.X, 4);
        assertEquals(3 ,game.getTree().getCurrentNode().getChildren().size() );
        position = game.makeNewMove(GameFigure.O);
        // computer should chose position 8 because of positions 5,6,7 mean lose for ´O´
        assertEquals(8, position);
        // now there should be 4 children for X with position 4
        assertEquals(4, game.getTree().getCurrentNode().getParent().getChildren().size() );
        game.makeNewMove(GameFigure.X, 6);
        assertEquals(GameStatus.WIN, game.getGameStatus());
        // node with position 4
        Node nodeX4 = game.getTree().getCurrentNode().getParent().getParent();
        assertEquals(NodeStatus.UNKNOWN, nodeX4.getStatus());
        assertEquals(4, nodeX4.getPosition());
        assertEquals(5, nodeX4.getLevel());
        game.gameOver(GameStatus.WIN);
        // now two new tree levels status should be updated
        assertEquals(NodeStatus.WIN, nodeX4.getStatus());
        assertEquals(NodeStatus.LOSE, nodeX4.getParent().getStatus());

        // game 5
        game.makeNewMove(GameFigure.X, 0);
        position = game.makeNewMove(GameFigure.O);
        assertEquals(1, position);
        game.makeNewMove(GameFigure.X, 2);
        position = game.makeNewMove(GameFigure.O);
        // now position 3 should be ignored
        assertEquals(4, position);
        assertEquals(2, game.getTree().getCurrentNode().getParent().getChildren().size());
    }

    @Test
    public void testBattleRegime() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.BATTLE);

        int numberOfGames = 0;
        while (numberOfGames < MAX_NUMBER_OF_GAMES_FOR_BATTLE_3X3) {
            if( playNewGame(game, GameFigure.X)) {
                numberOfGames ++;
            }
        }

        assertEquals(MAX_NUMBER_OF_GAMES_FOR_BATTLE_3X3, numberOfGames);
        assertEquals(MAX_REAL_NUMBER_OF_NODES_BATTLE_3X3, game.getTree().getNumberOfNodes());
        assertEquals(NodeStatus.DRAW, game.getTree().getRoot().getStatus());

        System.out.println("..number of nodes in the tree = " + game.getTree().getNumberOfNodes() );
        System.out.println("..max tree depth = " + game.getTree().getTreeDepth() );
        System.out.println("..tree root status = " + game.getTree().getRoot().getStatus() );

        System.out.println("..number of nodes level 1 = " + game.getTree().getRoot().getChildren().size() );
        System.out.println("..number of nodes level 2 = " + game.getTree().getNumberOfNodes(2) );
        System.out.println("..number of nodes level 3 = " + game.getTree().getNumberOfNodes(3) );
        System.out.println("..number of nodes level 4 = " + game.getTree().getNumberOfNodes(4) );
        System.out.println("..number of nodes level 5 = " + game.getTree().getNumberOfNodes(5) );
        System.out.println("..number of nodes level 6 = " + game.getTree().getNumberOfNodes(6) );
        System.out.println("..number of nodes level 7 = " + game.getTree().getNumberOfNodes(7) );
        System.out.println("..number of nodes level 8 = " + game.getTree().getNumberOfNodes(8) );
        System.out.println("..number of nodes level 9 = " + game.getTree().getNumberOfNodes(9) );

        for(Node node : game.getTree().getRoot().getChildren()) {
            System.out.println("..node status level 1 = " + node.getStatus() );
        }
    }

    @Test
    public void testLearningRegime() throws TicTacToeException {
        Game game = new Game(GameBoardSize.SMALL, GameRegime.LEARNING);

        int numberOfGames = 0;
        while (numberOfGames < MAX_NUMBER_OF_GAMES_FOR_LEARNING_3X3) {
            if (playNewGame(game, GameFigure.X)) {
                numberOfGames++;
            }
        }
        System.out.println("..number of nodes in the tree = " + game.getTree().getNumberOfNodes());
        System.out.println("..max tree depth = " + game.getTree().getTreeDepth());
        System.out.println("..tree root status = " + game.getTree().getRoot().getStatus());

        assertEquals(MAX_NUMBER_OF_GAMES_FOR_LEARNING_3X3, numberOfGames);
        assertEquals(MAX_REAL_NUMBER_OF_NODES_LEARNING_3X3, game.getTree().getNumberOfNodes());
        assertEquals(NodeStatus.DRAW, game.getTree().getRoot().getStatus());

        System.out.println("..number of nodes level 1 = " + game.getTree().getRoot().getChildren().size());
        System.out.println("..number of nodes level 2 = " + game.getTree().getNumberOfNodes(2));
        System.out.println("..number of nodes level 3 = " + game.getTree().getNumberOfNodes(3));
        System.out.println("..number of nodes level 4 = " + game.getTree().getNumberOfNodes(4));
        System.out.println("..number of nodes level 5 = " + game.getTree().getNumberOfNodes(5));
        System.out.println("..number of nodes level 6 = " + game.getTree().getNumberOfNodes(6));
        System.out.println("..number of nodes level 7 = " + game.getTree().getNumberOfNodes(7));
        System.out.println("..number of nodes level 8 = " + game.getTree().getNumberOfNodes(8));
        System.out.println("..number of nodes level 9 = " + game.getTree().getNumberOfNodes(9));

        for (Node node : game.getTree().getRoot().getChildren()) {
            System.out.println("..node status level 1 = " + node.getStatus());
        }
    }

    private boolean playNewGame(Game game, GameFigure figure) throws TicTacToeException {
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
