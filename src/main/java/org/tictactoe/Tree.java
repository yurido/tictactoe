package org.tictactoe;

import org.tictactoe.exceptions.ChildNodeNotFoundException;
import org.tictactoe.exceptions.ChildrenCollectionException;
import org.tictactoe.exceptions.IllegalStatusException;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
public class Tree{
    private Node root;
    private Node currentNode;

    public Tree(int rootSize)
    {
        root = new Node(rootSize);
        currentNode = root;
    }
    public void addNode(int position) throws ChildrenCollectionException
    {
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
        if(currentNode.equals(root))return;
        currentNode = currentNode.getParent();
    }
    /**
     * Method finds the child Node with the given position on the game board
     * @return Node or null if position is empty
     */
    public Node findNodeWithGivenPosition(int position)
    {
        for(Node node : currentNode.getChildren())
        {
            if(node.getPosition()==position)
            {
                return node;
            }
        }
        return null;
    }
    /**
     * Moves current node to the given child node
     */
    public void moveToChild(Node node) throws ChildNodeNotFoundException
    {
        for(Node child : currentNode.getChildren())
        {
            if(node.equals(child))
            {
                currentNode = node;
                return;
            }
        }

        throw new ChildNodeNotFoundException("Given child node is not found");
    }
    public Node getCurrentNode()
    {
        return currentNode;
    }
    /**
     * Method updates status of all the nodes in the current branch (upp to the
     * root). Call this method when the game is over. Update current node status
     * before calling this method.
     * @param isFirstCall set to true when you call this method
     * @throws Exception if Current node status = -1
     */
    public void updateBranchStatus(boolean isFirstCall) throws IllegalStatusException
    {
        int losers=0;
        int draws=0;

        if(isFirstCall)
        {
            if(currentNode.getStatus()==NodeStatus.UNKNOWN)
            {
                throw new IllegalStatusException("The node status has 'unknown' value. It " +
                        "should be changed before calling 'UpdateBranchStatus' method.");
            }

            if(currentNode.getParent()!=null)
            {
                currentNode = currentNode.getParent();
                updateBranchStatus(false);
            }
            return;
        } // first call

        // check all the children
        for(Node node : currentNode.getChildren())
        {
            switch (node.getStatus())
            {
                case WIN:
                    // if a Child win then Parent loose
                    currentNode.setStatus(NodeStatus.LOSE);
                    if(currentNode.getParent()==null)
                    {
                        return;
                    }
                    currentNode = currentNode.getParent();
                    updateBranchStatus(false);
                    return;
                case LOSE:
                    // if ALL children lose then parent win
                    losers++;
                    break;
                case DRAW:
                    draws++;
                default:
                    break;
            }
        } // for

        if(losers== currentNode.getMaxChildrenCapacity())
        {
            // if ALL children are losers, parent win
            currentNode.setStatus(NodeStatus.WIN);
            // and continue
            if(currentNode.getParent()!=null)
            {
                currentNode = currentNode.getParent();
                updateBranchStatus(false);
            }
            return;
        } // all children are losers

        if(draws== currentNode.getMaxChildrenCapacity())
        {
            currentNode.setStatus(NodeStatus.DRAW);
        }
    }
}
