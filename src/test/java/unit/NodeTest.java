package unit;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.tictactoe.Node;
import org.tictactoe.NodeStatus;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tictactoe.exceptions.ChildrenCollectionException;

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
        System.out.println(Root.toString());
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
}
