package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateStatusException;
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

    @Test
    public void testSmallGameBatleRegime() throws TicTacToeException, UpdateStatusException {
        Game game = new Game();
        game.startNewGame(GameBoardSize.SMALL, GameRegime.BATLE);
        int newPosition = game.makeNewMove(GameFigure.X);
        assertEquals(0, newPosition);
        newPosition = game.makeNewMove(GameFigure.O);
        assertEquals(1, newPosition);
        assertEquals(GameStatus.CONTINUE, game.checkIfGameIsOver());
        newPosition = game.makeNewMove(GameFigure.X);
        assertEquals(2, newPosition);
        newPosition = game.makeNewMove(GameFigure.O);
        assertEquals(3, newPosition);
        newPosition = game.makeNewMove(GameFigure.X);
        assertEquals(4, newPosition);
        newPosition = game.makeNewMove(GameFigure.O);
        assertEquals(5, newPosition);
        newPosition = game.makeNewMove(GameFigure.X);
        assertEquals(6, newPosition);
        assertEquals(GameStatus.WIN, game.checkIfGameIsOver());
        game.gameOver(GameStatus.WIN);

    }
}
