package org.tictactoe;

import org.tictactoe.exceptions.ChildrenCollectionException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
 * @company Dynabyte AB
 */
public class Node{
    private Node parent=null;
    private ArrayList<Node> children=null;
    private UUID UID; // unique id
    private NodeStatus status=null;
    private int position=0; // position on the game board
    private int weight=0;   // optimisation param
    private int level=0;    // the node level in the node tree
    private int maxChildrenCapacity=0;

    /**
     * Constructor
     * @param size max size of the children collection
     * @throws IllegalArgumentException
     */
    public Node(int size) throws IllegalArgumentException{
        parent = null;
        if(size<0) throw new IllegalArgumentException("children collection size is < 0");
        children = new ArrayList<Node>(size);
        UID = UUID.randomUUID();
        status = NodeStatus.UNKNOWN;
        position = 0;
        weight = 0;
        level = 0;
        maxChildrenCapacity = size;
    }
    public void setParent(Node node){parent = node;}
    public Node getParent(){return parent;}
    /**
     * Adds new Child to the collection
     * @param node new child
     */
    public void addChild(Node node) throws ChildrenCollectionException{
        if(getChildrenNumber() >= getMaxChildrenCapacity()) {
            throw new ChildrenCollectionException("The children collection is full. You can not add more nodes!");
        }
        node.setParent(this);
        node.setLevel(getLevel() + 1);
        children.add(node);
    }
    public Node getChild(int index) throws ArrayIndexOutOfBoundsException{
        return children.get(index);
    }
    public UUID getUID(){return UID;}
    public NodeStatus getStatus(){return status;}
    public void setStatus(NodeStatus value){status = value;}
    public int getPosition(){return position;}
    public void setPosition(int value){position = value;}
    public void setWeight(int value){weight = value;}
    public int getWeight(){return weight;}
    public int getLevel(){return level;}
    public int getChildrenNumber(){return children.size();}
    public int getMaxChildrenCapacity(){return maxChildrenCapacity;}
    public ArrayList<Node> getChildren(){return children;}
    public void setLevel(int value){level = value;}
    @Override
    public String toString(){
        StringBuilder value = new StringBuilder();

        value.append("Node, UUID=" + getUID());
        value.append(", status=" + getStatus());
        value.append(", level=" + getLevel());
        value.append(", position=" + getPosition());

        // parent
        if(getParent()!=null)
            value.append(", parent="+getParent().getUID() );
        else
            value.append(", parent=null");

        // children
        value.append(", MAX children number=" + getMaxChildrenCapacity());
        value.append(", Current children number=" + getChildrenNumber());

        return value.toString();
    }
}
