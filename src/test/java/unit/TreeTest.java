package unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.tictactoe.Node;
import org.tictactoe.NodeStatus;
import org.tictactoe.Tree;
import org.tictactoe.exceptions.ChildNodeNotFoundException;
import org.tictactoe.exceptions.ChildrenCollectionException;
import org.tictactoe.exceptions.IllegalStatusException;

/**
 * @author Yury Dorofeev
 * @version 2015-09-08
  */
@RunWith(MockitoJUnitRunner.class)
public class TreeTest {
    @Rule
    public ExpectedException expectException = ExpectedException.none();

    @Test
    public void create()
    {
        Tree tree = new Tree(2);
        assertTrue(tree.getRoot()!=null);
        assertTrue(tree.getCurrentNode()!=null);
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
        assertTrue(tree.getRoot().getMaxChildrenCapacity()==2);
    }

    @Test
    public void addNode() throws ChildrenCollectionException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        assertTrue(tree.getRoot() != tree.getCurrentNode());
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity()==1);
    }

    @Test
    public void moveToRoot() throws ChildrenCollectionException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToRoot();
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
    }

    @Test
    public void moveToParent() throws ChildrenCollectionException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
        tree.moveToParent();
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
    }

    @Test
    public void findNodeWithGivenPosition() throws ChildrenCollectionException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        tree.addNode(1);
        Node node = tree.getCurrentNode();
        tree.moveToParent();
        Node myNode = tree.findChildNodeWithGivenPosition(1);
        assertTrue(myNode.equals(node));
        myNode = tree.findChildNodeWithGivenPosition(2);
        assertTrue(myNode==null);
    }

    @Test
    public void moveToChild() throws ChildrenCollectionException, ChildNodeNotFoundException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        tree.addNode(1);
        Node node = tree.getCurrentNode();
        tree.moveToParent();
        tree.moveToChild(node);
        assertTrue(tree.getCurrentNode().equals(node));
        tree.moveToParent();
        node = new Node(0);
        expectException.expect(ChildNodeNotFoundException.class);
        expectException.expectMessage("Given child node is not found");
        tree.moveToChild(node);
    }

    @Test
    public void updateBranchStatusIllegalStatusException() throws ChildrenCollectionException, IllegalStatusException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        tree.addNode(1);
        expectException.expect(IllegalStatusException.class);
        expectException.expectMessage("The node status has 'unknown' value. It " +
                "should be changed before calling 'UpdateBranchStatus' method.");
        tree.updateTreeStatus();
    }

    @Test
    public void updateBranchStatusWinTwoIterations() throws ChildrenCollectionException, IllegalStatusException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.addNode(2);
        tree.getCurrentNode().setStatus(NodeStatus.WIN);
        assertTrue(tree.getCurrentNode().getLevel() == 2);
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity() == 0);
        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.UNKNOWN);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));
        assertTrue(tree.getCurrentNode().getLevel()==0);

        tree.moveToRoot();
        tree.addNode(1);
        tree.addNode(3);
        tree.getCurrentNode().setStatus(NodeStatus.WIN);
        assertTrue(tree.getCurrentNode().getLevel() == 2);
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity() == 0);
        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.WIN);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));
        assertTrue(tree.getCurrentNode().getLevel()==0);
    }

    @Test
    public void updateBranchStatusLoseOneIteration() throws ChildrenCollectionException, IllegalStatusException, ChildNodeNotFoundException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.addNode(2);
        tree.getCurrentNode().setStatus(NodeStatus.LOSE);
        assertTrue(tree.getCurrentNode().getLevel() == 2);
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity() == 0);
        assertTrue(tree.getCurrentNode().getPosition() == 2);
        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.LOSE);

        tree.moveToRoot();
        tree.addNode(1);
        tree.addNode(3);
        tree.moveToRoot();
        // move to the bottom
        Node currentNode = tree.findChildNodeWithGivenPosition(0);
        tree.moveToChild(currentNode);
        currentNode = tree.findChildNodeWithGivenPosition(2);
        tree.moveToChild(currentNode);
        assertTrue(tree.getCurrentNode().getLevel() == 2);
        assertTrue(tree.getCurrentNode().getPosition() == 2);
        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.LOSE);

        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.LOSE);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));
        assertTrue(tree.getCurrentNode().getLevel() == 0);
    }
}
