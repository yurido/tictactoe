package org.dorofeev.tictactoe.lib;

import org.dorofeev.tictactoe.lib.exception.TicTacToeException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeTest {
    @Rule
    public ExpectedException expectException = ExpectedException.none();

    @Test
    public void collectionSizeError() throws TicTacToeException
    {
        expectException.expect(TicTacToeException.class);
        expectException.expectMessage("children collection size is < 0");

        new Node(-1);
    }

    @Test
    public void testFails() throws TicTacToeException{
        Node Root = new Node(3);

        assertEquals(null, Root.getParent());
        Root.setParent(new Node(0));
        assertNotEquals(null, Root.getParent());

        Node Node = new Node(2);
        Node.setStatus(NodeStatus.UNKNOWN);
        Node.setParent(Root);

        Root.addChild(Node);
        Node = new Node(2);
        Node.setStatus(NodeStatus.LOSE);
        Node.setParent(Root);
        Root.addChild(Node);

        Node N = Root.getChild(0);
        assertEquals(NodeStatus.UNKNOWN, N.getStatus());
    }
    @Test
    public void childrenCollectionIsFullError() throws TicTacToeException
    {
        expectException.expect(TicTacToeException.class);
        expectException.expectMessage("The children collection is full. You can not add more nodes!");

        Node node = new Node(2);
        node.addChild(new Node(1));
        node.addChild(new Node(1));
        node.addChild(new Node(1));
        node.addChild(new Node(1));
        node.addChild(new Node(1));
    }
    @Test
    public void getChildError()throws TicTacToeException
    {
        Node node = new Node(2);
        expectException.expect(TicTacToeException.class);
        expectException.expectMessage("Child node with index 10 does not exist");

        node.getChild(10);
    }

    @Test
    public void toStringLeaf() throws TicTacToeException {
        Node node = new Node(2);
        Node child = new Node(1);
        node.addChild(child);
        assertTrue(child.getParent() != null);
        child.toString();

        assertTrue(child.getWeight() == 0);
        child.setWeight(1);
        assertTrue(child.getWeight() == 1);
    }

}
