package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.CreateNewNodeException;
import org.dorofeev.tictactoe.exception.NodeNotFoundException;
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
    private ArrayList<GameBoardNode> gameBoard;


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
     * @throws TicTacToeException if container is already occupied
     */
    public void makeNewMove(int position) throws TicTacToeException {
        try {
            Node node = tree.findChildNodeWithGivenPosition(position);
            tree.moveToChild(node);
        }catch(NodeNotFoundException e){
            tree.addNode(position);
        }
    }

    /**
     * Method registers new move in the current game
     * @return new move position on the game board
     * @throws TicTacToeException
     */
    public int makeNewMove() throws TicTacToeException{
        int position=-1;
        try{
            Node node=findBestNode();
            position = node.getPosition();
        }catch (CreateNewNodeException e){
            position = findBoardPosition();
            tree.addNode(position);
        }
        return position;
    }

    public void gameOver(GameStatus status) throws TicTacToeException{
        tree.getCurrentNode().setStatus(NodeStatus.valueOf(String.valueOf(status)));
        tree.updateTreeStatus();
    }

    public GameStatus checkIfGameIsOver(){
        // check rows

        // check columns

        // check diagonal
    }

    private int findBoardPosition() throws TicTacToeException{
        for(GameBoardNode node: gameBoard){
            if(node.getFigure()== GameFigure.EMPTY){
                return node.getPosition();
            }
        }
        throw new TicTacToeException("The board game is full");
    }

    /**
     * Finds the best Node according to the Priority schema
     * @return Node best node
     * @exception TicTacToeException if there are no empty slots left, CreateNewNodeException if new node should be created
     */
    private Node findBestNode() throws TicTacToeException, CreateNewNodeException{
        Node currentNode = tree.getCurrentNode();

        for(NodeStatus status : nodeStatusPrioritySchema){

            if(status==NodeStatus.NEW_NODE && currentNode.getChildren().size()<currentNode.getMaxChildrenCapacity()){
                throw new CreateNewNodeException("New node should be created");
            }

            for(Node node : currentNode.getChildren()){
                if( node.getStatus()==status){
                    return node;
                }
            }

        }

        if(currentNode.getMaxChildrenCapacity()>0){

            if(currentNode.getChildren().size()<currentNode.getMaxChildrenCapacity()){
                throw new CreateNewNodeException("New node should be created");
            }

            if(currentNode.getChildren().size()>0){
                return currentNode.getChild(0);
            }

        }

        throw new TicTacToeException("There are no free nodes left");
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

    private void initGameBoard(int boardSize){
        gameBoard = new ArrayList<GameBoardNode>(boardSize);
        for(int i=0; i<boardSize; i++){
            gameBoard.add(i, new GameBoardNode(GameFigure.EMPTY, i));
        }
    }
}
