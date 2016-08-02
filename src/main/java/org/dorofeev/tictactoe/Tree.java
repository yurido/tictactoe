package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.NodeNotFoundException;
import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateStatusException;

/**
 * Tree class
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
public class Tree {
    private Node root;
    private Node currentNode;
    private long numberOfNodes;
    private long numberOfNodesPerLevel;
    private int maxTreeDepth;

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
    public void moveToParent() {
        if(currentNode.equals(root)) {
            return;
        }
        currentNode = currentNode.getParent();
    }

    public Node findChildNodeWithGivenPosition(int position) throws NodeNotFoundException {
        for(Node node : currentNode.getChildren()) {
            if(node.getPosition() == position) {
                return node;
            }
        }
        throw new NodeNotFoundException("Node with position "+position+" is not found");
    }

    public void moveToChild(Node node) throws NodeNotFoundException {
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
    public void updateTreeStatus() throws UpdateStatusException {
        updateBranchStatus(true);
    }

    public long getNumberOfNodes() {
        numberOfNodes = 0;
        calcNumberOfNodes(root);
        return numberOfNodes;
    }

    public long getNumberOfNodes(int level) {
        numberOfNodesPerLevel = 0;
        calcNumberOfNodesPerLevel(root, level);
        return numberOfNodesPerLevel;
    }

    public int getTreeDepth() {
        maxTreeDepth = 0;
        calcTreeDepth(root);
        return maxTreeDepth;
    }

    private void calcNumberOfNodesPerLevel(Node node, int level) {
        for(Node child : node.getChildren()) {
            if(child.getLevel() == level) {
                numberOfNodesPerLevel ++;
            } else {
                calcNumberOfNodesPerLevel(child, level);
            }
        }
    }

    private void calcTreeDepth(Node node) {
        for(Node child : node.getChildren()) {
            if(child.getLevel() > maxTreeDepth) {
                maxTreeDepth = child.getLevel();
            }
            calcTreeDepth(child);
        }
    }

    private void calcNumberOfNodes(Node node) {
        for(Node child : node.getChildren()) {
            numberOfNodes += child.getChildren().size();
            calcNumberOfNodes(child);
        }
    }

    private void updateBranchStatus(boolean isFirstCall) throws UpdateStatusException {
        int losers = 0;
        int draws = 0;
        boolean allChildrenHaveStatus = (currentNode.getMaxChildrenCapacity() == currentNode.getChildren().size());

        if(isFirstCall) {
            if(currentNode.getStatus() == NodeStatus.UNKNOWN) {
                throw new UpdateStatusException("The node status is 'unknown'. It " +
                        "should be changed before calling 'UpdateBranchStatus' method");
            }
            gotoParentAndUpdateBranch();
            return;
        }

        for(Node node : currentNode.getChildren()) {
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

        if(losers == currentNode.getMaxChildrenCapacity()) {
            updateStatusAndMoveToParent(NodeStatus.WIN);
        } else if(draws == currentNode.getMaxChildrenCapacity() || allChildrenHaveStatus) {
            updateStatusAndMoveToParent(NodeStatus.DRAW);
        }
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
