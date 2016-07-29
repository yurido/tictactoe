package org.dorofeev.tictactoe;

import static org.junit.Assert.*;

import org.dorofeev.tictactoe.exception.NodeNotFoundException;
import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateStatusException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Yury Dorofeev
 * @version 2015-09-08
  */
@RunWith(MockitoJUnitRunner.class)
public class TreeTest {
    @Rule
    public ExpectedException expectException = ExpectedException.none();

    @Test
    public void create() throws TicTacToeException
    {
        Tree tree = new Tree(2);
        assertTrue(tree.getRoot()!=null);
        assertTrue(tree.getCurrentNode()!=null);
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
        assertTrue(tree.getRoot().getMaxChildrenCapacity()==2);
    }

    @Test
    public void addNode() throws TicTacToeException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        assertTrue(tree.getRoot() != tree.getCurrentNode());
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity()==1);
    }

    @Test
    public void moveToRoot() throws TicTacToeException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToRoot();
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
    }

    @Test
    public void moveToParent() throws TicTacToeException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
        tree.moveToParent();
        assertTrue(tree.getRoot().equals(tree.getCurrentNode()));
    }

    @Test
    public void nodeWithGivenPositionNotFound() throws TicTacToeException, NodeNotFoundException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        tree.addNode(1);
        Node node = tree.getCurrentNode();
        tree.moveToParent();
        Node myNode = tree.findChildNodeWithGivenPosition(1);
        assertTrue(myNode.equals(node));

        expectException.expect(NodeNotFoundException.class);
        expectException.expectMessage("Node with position 2 is not found");

        tree.findChildNodeWithGivenPosition(2);
    }

    @Test
    public void nodeNotFound() throws TicTacToeException, NodeNotFoundException {
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
        expectException.expect(NodeNotFoundException.class);
        expectException.expectMessage("Given child node is not found");

        tree.moveToChild(node);
    }

    @Test
    public void updateBranchStatusException() throws UpdateStatusException, TicTacToeException{
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToParent();
        tree.addNode(1);
        expectException.expect(UpdateStatusException.class);
        expectException.expectMessage("The node status is 'unknown'. It should be changed before calling 'UpdateBranchStatus' method");

        tree.updateTreeStatus();
    }

    @Test
    public void updateBranchStatusWinTwoIterations() throws UpdateStatusException, TicTacToeException{
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.addNode(2);
        tree.getCurrentNode().setStatus(NodeStatus.WIN);
        assertTrue(tree.getCurrentNode().getLevel() == 2);
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity() == 0);
        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.UNKNOWN);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));
        assertTrue(tree.getCurrentNode().getLevel() == 0);

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
    public void updateBranchStatusLoseOneIteration() throws TicTacToeException, NodeNotFoundException, UpdateStatusException {
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

    @Test
    public void updateBranchStatusWithEmptyNodes() throws TicTacToeException, NodeNotFoundException{
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.moveToRoot();
        assertTrue(tree.getCurrentNode().getMaxChildrenCapacity()>tree.getCurrentNode().getChildren().size());
    }

    @Test
    public void updateBranchStatusDrawUnknown() throws TicTacToeException, NodeNotFoundException, UpdateStatusException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.addNode(2);
        tree.getCurrentNode().setStatus(NodeStatus.DRAW);
        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.UNKNOWN);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));

        tree.moveToRoot();
        tree.addNode(1);
        tree.addNode(3);
        tree.getCurrentNode().setStatus(NodeStatus.WIN);
        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.DRAW);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));
    }

    @Test
    public void updateBranchStatusUnknown() throws TicTacToeException, NodeNotFoundException, UpdateStatusException {
        Tree tree = new Tree(2);
        tree.addNode(0);
        tree.addNode(2);
        tree.moveToRoot();

        tree.addNode(1);
        tree.addNode(3);
        tree.getCurrentNode().setStatus(NodeStatus.WIN);
        tree.updateTreeStatus();

        assertTrue(tree.getCurrentNode().getStatus() == NodeStatus.UNKNOWN);
        assertTrue(tree.getCurrentNode().equals(tree.getRoot()));
    }

}
