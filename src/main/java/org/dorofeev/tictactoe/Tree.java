package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.NodeNotFoundException;
import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateStatusException;

/**
 * Tree class
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
public class Tree{
    private Node root;
    private Node currentNode;

    public Tree(int rootSize) throws TicTacToeException {
        root = new Node(rootSize);
        currentNode = root;
    }

    /**
     * Method adds new Child node to the Current node and moves Current node to the new one
     * @param position position on the game board
     * @throws TicTacToeException
     */
    public void addNode(int position) throws TicTacToeException {
        Node node = new Node(currentNode.getMaxChildrenCapacity()-1);
        node.setPosition(position);
        currentNode.addChild(node);
        currentNode = node;
    }
    public void moveToRoot()
    {
        currentNode = root;
    }
    public Node getRoot()
    {
        return root;
    }
    public void moveToParent()
    {
        if(currentNode.equals(root)) {
            return;
        }
        currentNode = currentNode.getParent();
    }

    public Node findChildNodeWithGivenPosition(int position) throws NodeNotFoundException
    {
        for(Node node : currentNode.getChildren()) {
            if(node.getPosition() == position) {
                return node;
            }
        }
        throw new NodeNotFoundException("Node with position "+position+" is not found");
    }

    public void moveToChild(Node node) throws NodeNotFoundException
    {
        if(!currentNode.getChildren().contains(node)) {
            throw new NodeNotFoundException("Given child node is not found. Node: " + node.toString());
        }
        currentNode = node;
    }

    public Node getCurrentNode()
    {
        return currentNode;
    }

    /**
     * Method updates status of all the nodes in the current branch (up to the
     * root). Call this method when the game is over. Update current node status
     * before calling this method!
     */
    public void updateTreeStatus() throws UpdateStatusException
    {
        updateBranchStatus(true);
    }

    private void updateBranchStatus(boolean isFirstCall) throws UpdateStatusException
    {
        int losers = 0;
        int draws = 0;
        boolean allChildrenHaveStatus = (currentNode.getMaxChildrenCapacity() == currentNode.getChildren().size());

        if(isFirstCall) {
            updateBranchStatusFirstCall();
            return;
        }

        for(Node node : currentNode.getChildren())
        {
            switch (node.getStatus())
            {
                case WIN:
                    // if a Child wins then Parent loses
                    updateStatusAndMoveToParent(NodeStatus.LOSE);
                    return;
                case LOSE:
                    // if ALL children lose then parent wins
                    losers++;
                    break;
                case DRAW:
                    draws++;
                    break;
                default:
                    allChildrenHaveStatus = false;
                    break;
            }
        }

        updateBranchStatusLose(losers);
        updateBranchStatusDraw(draws, allChildrenHaveStatus);
    }
    private void updateBranchStatusDraw(int numberOfDraws, boolean allChildrenHaveStatus) throws UpdateStatusException {
        if(numberOfDraws == currentNode.getMaxChildrenCapacity() || allChildrenHaveStatus) {
            updateStatusAndMoveToParent(NodeStatus.DRAW);
        }
    }

    /**
     * If ALL children are losers then parent wins
     * @param numberOfLosers
     * @throws UpdateStatusException
     */
    private void updateBranchStatusLose(int numberOfLosers) throws UpdateStatusException {
        if(numberOfLosers == currentNode.getMaxChildrenCapacity()) {
            updateStatusAndMoveToParent(NodeStatus.WIN);
        }
    }

    private void updateBranchStatusFirstCall() throws UpdateStatusException
    {
        if(currentNode.getStatus() == NodeStatus.UNKNOWN) {
            throw new UpdateStatusException("The node status is 'unknown'. It " +
                    "should be changed before calling 'UpdateBranchStatus' method");
        }

        gotoParentAndUpdateBranch();
    }
    private void gotoParentAndUpdateBranch() throws UpdateStatusException {
        if(currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            updateBranchStatus(false);
        }
    }

    private void updateStatusAndMoveToParent(NodeStatus status) throws UpdateStatusException {
        if(currentNode.getStatus() == NodeStatus.UNKNOWN) {
            currentNode.setStatus(status);
            gotoParentAndUpdateBranch();
        }
    }
}
