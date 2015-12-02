package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.TicTacToeException;
import java.util.ArrayList;

/**
 * Game class
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
public class Game{
    private ArrayList<NodeStatus> nodeStatusPrioritySchema;
    private Tree tree;
    private GameRegime gameRegime;
    private ArrayList<Figure> gameBoard;

    /**
     * Method starts new game instance. Builds new tree or continue building the
     * previous one
     * @param boarSize the size of the the game board
     * @param gameRegime
     */
    public void startNewGame(GameBoardSize boarSize, GameRegime gameRegime) throws TicTacToeException{

        if(tree == null || tree.getRoot().getMaxChildrenCapacity() != boarSize.getValue())
        {
            tree = new Tree(boarSize.getValue());
        }
        else
        {
            tree.moveToRoot();
        }

        if(gameRegime != gameRegime)
        {
            initNodeStatusPrioritySchema(gameRegime);
        }
        this.gameRegime = gameRegime;

        initGameBoard(boarSize.getValue());
    }

    /**
     * Method registers new step
     * @param position position on the game field (0-n)
     * @param figure X or 0
     * @return 0-0 win, 1-X win, 2-draw, -1-game is not over
     * @throws Exception if container is already occupied
     */
    public int makeNewMove(int position, Figure figure) throws TicTacToeException {

        if(gameBoard.get(position)!=Figure.EMPTY){
            throw new TicTacToeException("Position "+position+" on the game board is already ocupied");
        }

        gameBoard.set(position, figure);
        Node Node = tree.findChildNodeWithGivenPosition(position);
        if (Node == null) {
            tree.addNode(position);
        }


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
        Figure prevFigure = Figure.EMPTY;
        int whoWin = -1;

        // check horizont (go through the first line)
        for(int i=0; i<gridSQRT; i++ ){

            // go through the columns
            for(int j=1; j<=gridSQRT; j++ ){

                // take the slot index
                index = i + (gridSize-gridSQRT*j);
                if(this.gameBoard.get(index)== Figure.EMPTY){

                    // dont need to search
                    prevFigure = Figure.EMPTY;
                    match = false;
                    break;
                }

                if(j==1)
                    prevFigure = gameBoard.get(index); // take the first figure on column
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

        prevFigure = Figure.EMPTY;
        match = false;

        // check vertical (go through the first column)
        for(int i=0; i<gridSize-gridSQRT+1; i+=gridSQRT){

            // go through the rows
            for(int j=0; j<gridSQRT; j++){

                index=i+j;
                if(gameBoard.get(index)== Figure.EMPTY){

                    // don't need to analize
                    prevFigure = Figure.EMPTY;
                    match = false;
                    break;
                }

                if(j==0)
                    prevFigure= gameBoard.get(index); // take the first figure
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

        prevFigure = Figure.EMPTY;
        match=false;

        // check left cross line (from the upper left corner to the bottom right one)
        for(int i=0; i<gridSize; i+=gridSQRT+1){
            // 0 1 2     0 1  2 3
            // 3 4 5     4 5  6 7
            // 6 7 8     8 9 10 11
            //           12 13 14 15
            index=i;
            if(gameBoard.get(index)== Figure.EMPTY){
                // don't need to analize
                prevFigure = Figure.EMPTY;
                match=false;
                break;
            }

            if(index==0)
                prevFigure= gameBoard.get(index); // take the first figure
            else{
                // take the slot value (0 or X)
                match = ifFigureMatch(prevFigure, index);
                if(!match) break;

            } // if

        } // for

        // if the whole line is 0 or X then game is over
        whoWin = whoWin(match, prevFigure);
        if(whoWin>-1) return whoWin;

        prevFigure = Figure.EMPTY;
        match=false;

        // check right cross line (from the upper right corner to the bottom left one)
        for(int i=gridSize-gridSQRT; i>gridSQRT-1; i-=(gridSQRT-1)){

            index=i;
            if(gameBoard.get(index)== Figure.EMPTY){
                // don't need to analize
                prevFigure = Figure.EMPTY;
                match=false;
                break;
            }
            if(index==(gridSize-gridSQRT))
                prevFigure= gameBoard.get(index); // take the first figure
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
        for(Figure i : gameBoard){
            if(i== Figure.EMPTY) return -1; // game is not over!
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
        return gameBoard.get(index) == figure;
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
     * Method initializes game board
     * @param boardSize size of the game grid
     */
    private void initGameBoard(int boardSize){
        gameBoard = new ArrayList<Figure>(boardSize);
        for(int i=0; i<boardSize; i++){
            gameBoard.add(i, Figure.UNKNOWN);
        }
    }
    /**
     * Sets new priority schema
     * @param schema 1-Battle, 2-study gameRegime
     * @throws Exception if schema has another value
     */
    private void ChangePrioritySchema(int schema) throws IllegalArgumentException{
        if(schema>=1 && schema<=2)
            initNodeStatusPrioritySchema(schema);
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

        for(NodeStatus SchemaStatus : nodeStatusPrioritySchema){

            // new node ?
            if(SchemaStatus==NodeStatus.NEW_NODE){
                // if children limit is not reached
                if(CurrentNode.getChildren().size()<CurrentNode.getMaxChildrenCapacity()){
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
            if(CurrentNode.getChildren().size()<CurrentNode.getMaxChildrenCapacity()){
                return null;
            }

            // take the first one
            if(CurrentNode.getChildren().size()>0){
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
     * @param regime
     */
    private void initNodeStatusPrioritySchema(GameRegime regime){

        // |high|normal |low|
        // |----|-------|---|
        // |  0 |1|2|3  | 4 | index

        if(regime==GameRegime.FIGHT){
            nodeStatusPrioritySchema = new ArrayList<NodeStatus>(5);

            // high priority
            nodeStatusPrioritySchema.add(NodeStatus.WIN);
            // normal priority
            nodeStatusPrioritySchema.add(NodeStatus.UNKNOWN);
            nodeStatusPrioritySchema.add(NodeStatus.NEW_NODE);
            nodeStatusPrioritySchema.add(NodeStatus.DRAW);
            // low priority
            nodeStatusPrioritySchema.add(NodeStatus.DRAW);
        }
        else{
            nodeStatusPrioritySchema = new ArrayList<NodeStatus>(2);

            // high priority
            nodeStatusPrioritySchema.add(NodeStatus.UNKNOWN);
            // normal priority
            nodeStatusPrioritySchema.add(NodeStatus.NEW_NODE);
            // all others, doesn't matter
        }
    }
}
