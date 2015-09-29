package org.tictactoe;

import org.tictactoe.exceptions.ChildrenCollectionException;
import java.util.ArrayList;

/**
 * @author Yury Dorofeev
 * @version 2015-09-07
 * @company Dynabyte AB
 */
public class Game{
    private ArrayList<NodeStatus> prioritySchema;
    private Tree tree;
    private int regime =0;
    private ArrayList<Figure> gameGrid;

    /**
     * Method starts new game instance. Builds new tree or continue building the
     * previous one
     * @param field_size the size of the the game board
     * @param regime 1 - man vs computer, 2 - computer vs computer
     * @throws Exception
     */
    public void startNewGame(int field_size, int regime){

        // new tree
        if(tree ==null){
            tree = new Tree(field_size);
        }
        else{
            // if new grid size build new tree
            if(tree.getRoot().getMaxChildrenCapacity()!=field_size)
                tree = new Tree(field_size);
            else
                tree.moveToRoot();
        }

        // set up game regime
        if(this.regime !=regime){
            InitPrioritySchema(regime);
        }
        this.regime = regime;

        // init game grid
        InitGameGrid(field_size);
    }

    /**
     * Method registers new step
     * @param position position on the game field (0-n)
     * @param figure X or 0
     * @return 0-0 win, 1-X win, 2-draw, -1-game is not over
     * @throws Exception if container is already occupied
     */
    public int registerStep(int position, Figure figure) throws ChildrenCollectionException {

        if(gameGrid.get(position)==figure) {
            gameGrid.set(position, figure);

            // add new node to the tree
            Node Node = tree.findNodeWithPosition(position);
            if (Node == null) {
                // add new node to the tree
                tree.addNode(position);
            }
        } // position is empty

        // check if a game is over
        return CheckGameOver();

        // update tree status!
    }

    /**
     * Method checks if the game is finished
     * @return 0-0 win, 1-X win, 2-draw, -1 -game is not over
     */
    private int CheckGameOver(){

        int gridSQRT = (int)Math.sqrt(tree.getRoot().getMaxChildrenCapacity());
        int gridSize = tree.getRoot().getMaxChildrenCapacity();
        int index = 0;
        boolean match = false;
        Figure prevFigure = Figure.UNKNOWN;
        int whoWin = -1;

        // check horizont (go through the first line)
        for(int i=0; i<gridSQRT; i++ ){

            // go through the columns
            for(int j=1; j<=gridSQRT; j++ ){

                // take the slot index
                index = i + (gridSize-gridSQRT*j);
                if(this.gameGrid.get(index)== Figure.UNKNOWN){

                    // dont need to search
                    prevFigure = Figure.UNKNOWN;
                    match = false;
                    break;
                }

                if(j==1)
                    prevFigure = gameGrid.get(index); // take the first figure on column
                else{
                    // take the slot value (0 or X)
                    match = ifFigureMatch(prevFigure, index);
                    if(!match) break;

                } // if
            } // for

            // if the whole column is 0 or X then game is over
            whoWin = this.whoWin(match, prevFigure);
            if(whoWin>-1) return whoWin;

        } // for horizont

        prevFigure = Figure.UNKNOWN;
        match = false;

        // check vertical (go through the first column)
        for(int i=0; i<gridSize-gridSQRT+1; i+=gridSQRT){

            // go through the rows
            for(int j=0; j<gridSQRT; j++){

                index=i+j;
                if(gameGrid.get(index)== Figure.UNKNOWN){

                    // don't need to analize
                    prevFigure = Figure.UNKNOWN;
                    match = false;
                    break;
                }

                if(j==0)
                    prevFigure= gameGrid.get(index); // take the first figure
                else{
                    // take the slot value (0 or X)
                    match = ifFigureMatch(prevFigure, index);
                    if(!match) break;

                } // if
            } // for rows

            // if the whole column is 0 or X then game is over
            whoWin = whoWin(match, prevFigure);
            if(whoWin>-1) return whoWin;

        } // for vertical

        prevFigure = Figure.UNKNOWN;
        match=false;

        // check left cross line (from the upper left corner to the bottom right one)
        for(int i=0; i<gridSize; i+=gridSQRT+1){
            // 0 1 2     0 1  2 3
            // 3 4 5     4 5  6 7
            // 6 7 8     8 9 10 11
            //           12 13 14 15
            index=i;
            if(gameGrid.get(index)== Figure.UNKNOWN){
                // don't need to analize
                prevFigure = Figure.UNKNOWN;
                match=false;
                break;
            }

            if(index==0)
                prevFigure= gameGrid.get(index); // take the first figure
            else{
                // take the slot value (0 or X)
                match = ifFigureMatch(prevFigure, index);
                if(!match) break;

            } // if

        } // for

        // if the whole line is 0 or X then game is over
        whoWin = whoWin(match, prevFigure);
        if(whoWin>-1) return whoWin;

        prevFigure = Figure.UNKNOWN;
        match=false;

        // check right cross line (from the upper right corner to the bottom left one)
        for(int i=gridSize-gridSQRT; i>gridSQRT-1; i-=(gridSQRT-1)){

            index=i;
            if(gameGrid.get(index)== Figure.UNKNOWN){
                // don't need to analize
                prevFigure = Figure.UNKNOWN;
                match=false;
                break;
            }
            if(index==(gridSize-gridSQRT))
                prevFigure= gameGrid.get(index); // take the first figure
            else{
                // take the slot value (0 or X)
                match = ifFigureMatch(prevFigure, index);
                if(!match) break;

            } // if

        }
        // if the whole line is 0 or X then game is over
        whoWin = whoWin(match, prevFigure);
        if(whoWin>-1) return whoWin;

        // draw?
        for(Figure i : gameGrid){
            if(i== Figure.UNKNOWN) return -1; // game is not over!
        }

        return -2; // draw
    }

    /**
     * Method check if the current grid figure = the previous one
     * @param figure X or O
     * @param index game grid index
     * @return true/false
     */
    private boolean ifFigureMatch(Figure figure, int index){
        // take the slot value (0 or X)
        return gameGrid.get(index) == figure;
    }

    /**
     * Method returns 0-if O win, 1-if X win, -1 if NO win
     * @param match some condition
     * @param figure X or O
     * @return 0 if O win, 1 if X win, -1 otherwise
     */
    private int whoWin(boolean match, Figure figure){
        if(match){
            // some win!
            if(figure== Figure.O)
                return 0;
            else if(figure== Figure.X)
                return 1;
            // otherwise continue to analize
        }
        return -1;
    }
    /**
     * Method initializes game field
     * @param gridSize size of the game grid
     * @throws IllegalArgumentException if field size is not 9, 16, 25, 36, 42, 64, 81
     */
    private void InitGameGrid(int gridSize) throws IllegalArgumentException{

        int val = (int)Math.sqrt(gridSize);
        if(val!=3&&val!=4&&val!=5&&val!=6&&val!=7&&val!=8){
            throw new IllegalArgumentException("Field size should be 9, 16, 25, 36, 42, 64, 81");
        }

        // init grid
        this.gameGrid = new ArrayList<Figure>(gridSize);
        for(int i=0; i<gridSize; i++){
            gameGrid.add(i, Figure.UNKNOWN);
        }
    }
    /**
     * Sets new priority schema
     * @param schema 1-Battle, 2-study regime
     * @throws Exception if schema has another value
     */
    private void ChangePrioritySchema(int schema) throws IllegalArgumentException{
        if(schema>=1 && schema<=2)
            InitPrioritySchema(schema);
        else
            throw new IllegalArgumentException("Priority schema value shall be 1 or 2");
    }

    /**
     * Finds the best Node according to the Priority schema
     * @return Node existed node or null if new Node should be created
     * @throws Exception if there no child nodes
     */
    private Node FindBestNode() throws Exception{
        Node CurrentNode = tree.getCurrentNode();

        for(NodeStatus SchemaStatus : prioritySchema){

            // new node ?
            if(SchemaStatus==NodeStatus.NEW_NODE){
                // if children limit is not reached
                if(CurrentNode.getChildrenNumber()<CurrentNode.getMaxChildrenCapacity()){
                    return null;
                }
            }

            // loop through the children collection
            for(Node node : CurrentNode.getChildren()){
                if( node.getStatus()==SchemaStatus){
                    return node;
                }
            } // for

        } // for

        // if Node is not found
        if(CurrentNode.getMaxChildrenCapacity()>0){

            // create new
            if(CurrentNode.getChildrenNumber()<CurrentNode.getMaxChildrenCapacity()){
                return null;
            }

            // take the first one
            if(CurrentNode.getChildrenNumber()>0){
                return CurrentNode.getChild(0);
            }

        }
        else{
            throw new Exception("There are no children nodes left");
        }

        // just because the method must return Node
        return null;
    }

    /**
     * Initialization of priority schema. Schema defines which status should be
     * chosen to make new step in the game
     * @param schema 1 - for the real battle, 2 - for the education regime
     */
    private void InitPrioritySchema(int schema){

        // |high|normal |low|
        // |----|-------|---|
        // |  0 |1|2|3  | 4 | index

        if(schema==1){
            prioritySchema = new ArrayList<NodeStatus>(5);

            // high priority
            prioritySchema.add(NodeStatus.WIN);
            // normal priority
            prioritySchema.add(NodeStatus.UNKNOWN);
            prioritySchema.add(NodeStatus.NEW_NODE);
            prioritySchema.add(NodeStatus.DRAW);
            // low priority
            prioritySchema.add(NodeStatus.DRAW);
        }
        else{
            prioritySchema = new ArrayList<NodeStatus>(2);

            // high priority
            prioritySchema.add(NodeStatus.UNKNOWN);
            // normal priority
            prioritySchema.add(NodeStatus.NEW_NODE);
            // all others don't matter
        }
    }
}
