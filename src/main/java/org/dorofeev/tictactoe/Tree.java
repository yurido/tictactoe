package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.NodeNotFoundException;
import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateBrunchStatusException;

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
        if(currentNode.equals(root)){
            return;
        }
        currentNode = currentNode.getParent();
    }

    public Node findChildNodeWithGivenPosition(int position) throws NodeNotFoundException
    {
        for(Node node : currentNode.getChildren())
        {
            if(node.getPosition()==position)
            {
                return node;
            }
        }
        throw new NodeNotFoundException("Node with position "+position+" is not found");
    }

    public void moveToChild(Node node) throws NodeNotFoundException
    {
        if(!currentNode.getChildren().contains(node)){
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
     * before calling this method.
     */
    public void updateTreeStatus() throws UpdateBrunchStatusException
    {
        updateBranchStatus(true);
    }

    private void updateBranchStatus(boolean isFirstCall) throws UpdateBrunchStatusException
    {
        int losers=0;
        int draws=0;
        boolean allChildrenHaveStatus=(currentNode.getMaxChildrenCapacity()==currentNode.getChildren().size());

        if(isFirstCall)
        {
            updateBranchStatusFirstCall();
            return;
        }

        for(Node node : currentNode.getChildren())
        {
            switch (node.getStatus())
            {
                case WIN:
                    // if a Child win then Parent loose
                    updateStatusAndMoveToParent(NodeStatus.LOSE.toString());
                    return;
                case LOSE:
                    // if ALL children lose then parent win
                    losers++;
                    break;
                case DRAW:
                    draws++;
                    break;
                default:
                    allChildrenHaveStatus=false;
                    break;
            }
        } // for

        updateBranchStatusLose(losers);
        updateBranchStatusDraw(draws, allChildrenHaveStatus);
    }
    private void updateBranchStatusDraw(int draws, boolean allChildrenHaveStatus)  throws UpdateBrunchStatusException {
        if(draws == currentNode.getMaxChildrenCapacity() || allChildrenHaveStatus)
        {
            updateStatusAndMoveToParent(NodeStatus.DRAW.toString());
        }
    }

    private void updateBranchStatusLose(int losers) throws UpdateBrunchStatusException {
        if(losers != currentNode.getMaxChildrenCapacity())
        {
            return;
        }
        // if ALL children are losers then parent wins
        updateStatusAndMoveToParent(NodeStatus.WIN.toString());
    }

    private void updateBranchStatusFirstCall() throws UpdateBrunchStatusException
    {
        if(currentNode.getStatus()== NodeStatus.UNKNOWN)
        {
            throw new UpdateBrunchStatusException("The node status is 'unknown'. It " +
                    "should be changed before calling 'UpdateBranchStatus' method");
        }

        gotoParentAndUpdateBranch();
    }
    private void gotoParentAndUpdateBranch() throws UpdateBrunchStatusException{
        if(currentNode.getParent()==null)
        {
            return;
        }
        currentNode = currentNode.getParent();
        updateBranchStatus(false);
    }

    private void updateStatusAndMoveToParent(String status) throws UpdateBrunchStatusException{
        if(currentNode.getStatus()==NodeStatus.UNKNOWN) {
            currentNode.setStatus(NodeStatus.valueOf(status));
            gotoParentAndUpdateBranch();
        }
    }
}
