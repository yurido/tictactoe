package unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.tictactoe.Tree;

/**
 * @author Yury Dorofeev
 * @version 2015-09-08
 * @company Dynabyte AB
 */
public class TreeTest {
    private Tree tree;

    @Before
    public void setup()
    {
        tree = new Tree(4);
    }
}
