package org.tictactoe;

import org.tictactoe.exceptions.ChildrenCollectionException;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
 * @company Dynabyte AB
 */
public class Tree{
    private Node Root;
    private Node CurrentNode;

    public Tree(int rootSize){
        Root = new Node(rootSize);
        CurrentNode = Root;
    }
    public void addNode(int position) throws ChildrenCollectionException {
        Node Node = new Node(CurrentNode.getMaxChildrenCapacity()-1);
        Node.setPosition(position);
        CurrentNode.addChild(Node);
        CurrentNode = Node;
    }
    public void moveToRoot(){CurrentNode = Root;}
    public Node getRoot(){return Root;}
    /**
     * Moves current node to its parent
     * @return false if current node reaches Root
     */
    public boolean moveToParent(){
        if(CurrentNode.equals(Root))return false;
        CurrentNode = CurrentNode.getParent();
        if(CurrentNode.equals(Root))return false;
        return true;
    }
    /**
     * Finds Node with the given position on the game board or null if not found
     * @return CNode
     * @throws Exception
     */
    public Node findNodeWithPosition(int value){
        for(Node node : CurrentNode.getChildren()){
            if(node.getPosition()==value)
                return node;
        }

        return null;
    }
    /**
     * Moves current node to the given child node
     * @throws Exception if given node is not found in the current node's children
     */
    public void moveToChild(Node Node) throws Exception{

        for(Node node : CurrentNode.getChildren()){
            if(node.equals(Node)){
                CurrentNode = Node;
                return;
            }
        }

        throw new Exception("Try to move to the non existed node in the collection");
    }
    public Node getCurrentNode(){return CurrentNode;}

    /**
     * Method updates status of all the nodes in the current branch (upp to the
     * Root). Call this method when the game is over. Update current node status
     * before calling this method.
     * @param first_call set to true when you call this method
     * @throws Exception if Current node status = -1
     */
    public void updateBranchStatus(boolean first_call){
        int losers=0;
        int draws=0;
        int nodes=CurrentNode.getChildrenNumber();

        if(first_call){
            if(CurrentNode.getStatus()==NodeStatus.UNKNOWN){
                throw new IllegalStateException("The node status has 'unknown' value. It " +
                        "should be changed before calling 'UpdateBranchStatus' method.");
            }

            if(CurrentNode.getParent()!=null){
                CurrentNode = CurrentNode.getParent();
                updateBranchStatus(false);
            }
            return;
        } // first call

        // check all the children
        for(Node node : CurrentNode.getChildren()){
            if(node.getStatus()==NodeStatus.WIN){

                // if a Child win then Parent loose
                CurrentNode.setStatus(NodeStatus.LOSE);
                if(CurrentNode.getParent()!=null){
                    CurrentNode = CurrentNode.getParent();
                    updateBranchStatus(false);
                }
                return;
            }
            else if(node.getStatus()==NodeStatus.LOSE){
                // if ALL children lose then parent win
                losers++;
            }
            else if(node.getStatus()==NodeStatus.DRAW){
                // draw
                draws++;
            }
            else{
                // status is unknown
                nodes -= 1;
            }

        } // for

        if(losers==CurrentNode.getMaxChildrenCapacity()){
            // if ALL children are losers, parent win
            CurrentNode.setStatus(NodeStatus.WIN);
            // and continue
            if(CurrentNode.getParent()!=null){
                CurrentNode = CurrentNode.getParent();
                updateBranchStatus(false);
            }
            return;

        } // all children are losers

        if(nodes==CurrentNode.getMaxChildrenCapacity()){
            // if other rules don't work then parent draw
            CurrentNode.setStatus(NodeStatus.DRAW);
        }

        // otherwise do nothing
    }
}
