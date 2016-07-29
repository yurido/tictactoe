package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Yury Dorofeev
 * @version 2015-12-04
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    @Rule
    public ExpectedException expectException = ExpectedException.none();

    @Test
    public void startNewGame() throws TicTacToeException {
        Game game = new Game();
        game.startNewGame(GameBoardSize.LARGE, GameRegime.BATLE);
    }
}
