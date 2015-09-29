package org.tictactoe;

import org.tictactoe.exceptions.ChildrenCollectionException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
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

    public Node(int size) throws IllegalArgumentException
    {
        parent = null;
        if(size<0)
        {
            throw new IllegalArgumentException("children collection size is < 0");
        }
        children = new ArrayList<Node>(size);
        UID = UUID.randomUUID();
        status = NodeStatus.UNKNOWN;
        position = 0;
        weight = 0;
        level = 0;
        maxChildrenCapacity = size;
    }
    public void setParent(Node node)
    {
        parent = node;
    }
    public Node getParent()
    {
        return parent;
    }
    public void addChild(Node node) throws ChildrenCollectionException
    {
        if(children!=null && children.size() >= getMaxChildrenCapacity())
        {
            throw new ChildrenCollectionException("The children collection is full. You can not add more nodes!");
        }
        node.setParent(this);
        node.setLevel(getLevel() + 1);
        children.add(node);
    }
    public Node getChild(int index)
    {
        return children.get(index);
    }
    public UUID getUID()
    {
        return UID;
    }
    public NodeStatus getStatus()
    {
        return status;
    }
    public void setStatus(NodeStatus status)
    {
        this.status = status;
    }
    public int getPosition()
    {
        return position;
    }
    public void setPosition(int position)
    {
        this.position = position;
    }
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
    public int getWeight()
    {
        return weight;
    }
    public int getLevel()
    {
        return level;
    }
    public int getMaxChildrenCapacity()
    {
        return maxChildrenCapacity;
    }
    public ArrayList<Node> getChildren()
    {
        return children;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    @Override
    public String toString()
    {
        StringBuilder value = new StringBuilder();

        value.append("Node, UUID=" + getUID());
        value.append(", status=" + getStatus());
        value.append(", level=" + getLevel());
        value.append(", position=" + getPosition());

        // parent
        if(getParent()!=null)
        {
            value.append(", parent=" + getParent().getUID());
        }
        else
        {
            value.append(", parent=null");
        }

        // children
        String childrenNumber = "0";
        if(children!=null)
        {
            childrenNumber = String.valueOf(children.size());
        }
        value.append(", MAX children number=" + getMaxChildrenCapacity());
        value.append(", Current children number=" + childrenNumber);

        return value.toString();
    }
}
