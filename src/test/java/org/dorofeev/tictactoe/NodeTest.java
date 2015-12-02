package org.dorofeev.tictactoe;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.dorofeev.tictactoe.Node;
import org.dorofeev.tictactoe.NodeStatus;
import org.junit.Test;
import static org.junit.Assert.*;
import org.dorofeev.tictactoe.exception.ChildrenCollectionException;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeTest {
    @Rule
    public ExpectedException expectException = ExpectedException.none();

    @Test
    public void collectionSizeError()
    {
        expectException.expect(IllegalArgumentException.class);
        expectException.expectMessage("children collection size is < 0");
        new Node(-1);
    }
    @Test
    public void testFails() throws ChildrenCollectionException {
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
        // System.out.println(Root.toString());
    }
    @Test
    public void childrenCollectionIsFullError() throws ChildrenCollectionException
    {
        expectException.expect(ChildrenCollectionException.class);
        expectException.expectMessage("The children collection is full. You can not add more nodes!");
        Node node = new Node(2);
        node.addChild(new Node(1));
        node.addChild(new Node(1));
        node.addChild(new Node(1));
        node.addChild(new Node(1));
        node.addChild(new Node(1));
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void getChildError()
    {
        Node node = new Node(2);
        node.getChild(10);
    }

    @Test
    public void toStringLeaf() throws ChildrenCollectionException {
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
