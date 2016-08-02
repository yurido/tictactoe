package org.dorofeev.tictactoe;

import org.dorofeev.tictactoe.exception.CreateNewNodeException;
import org.dorofeev.tictactoe.exception.NodeNotFoundException;
import org.dorofeev.tictactoe.exception.TicTacToeException;
import org.dorofeev.tictactoe.exception.UpdateStatusException;

import java.util.ArrayList;

/**
 * Game class
 * @author Yury Dorofeev
 * @version 2015-09-07
 */
public class Game {
    private ArrayList<NodeStatus> nodeStatusPrioritySchema;
    private Tree tree;
    private ArrayList<GameFigure> gameBoard;

    /**
     * Method starts new game instance. Builds new tree or continue building the
     * previous one
     * @param boardSize the size of the the game board
     * @param gameRegime
     */
    public Game(GameBoardSize boardSize, GameRegime gameRegime) throws TicTacToeException {

        if(tree == null || tree.getRoot().getMaxChildrenCapacity() != boardSize.getValue()) {
            tree = new Tree(boardSize.getValue());
        }
        else {
            tree.moveToRoot();
        }

        initNodeStatusPrioritySchema(gameRegime);
        initGameBoard(boardSize.getValue());
    }

    /**
     * Method registers new step
     * @param position position on the game board (0-n)
     * @throws TicTacToeException if container is already occupied
     */
    public void makeNewMove(GameFigure figure, int position) throws TicTacToeException {
        try {
            Node node = tree.findChildNodeWithGivenPosition(position);
            tree.moveToChild(node);
        } catch(NodeNotFoundException e) {
            tree.addNode(position);
        }
        gameBoard.set(position, figure);
    }

    /**
     * Method registers new step
     * @return new position on the game board
     * @throws TicTacToeException
     */
    public int makeNewMove(GameFigure figure) throws TicTacToeException {
        int position;
        try {
            Node node = findBestNode();
            position = node.getPosition();
            try {
                tree.moveToChild(node);
            } catch (NodeNotFoundException e) {
                throw new TicTacToeException(e.getMessage());
            }
        } catch (CreateNewNodeException e) {
            position = findEmptyPositionForNewNode();
            tree.addNode(position);
        }
        gameBoard.set(position, figure);
        return position;
    }

    public void gameOver(GameStatus status) throws TicTacToeException {
        tree.getCurrentNode().setStatus(mapNodeStatus(status));
        try {
            tree.updateTreeStatus();
        } catch (UpdateStatusException e) {
            throw new TicTacToeException(e.getMessage());
        }
        tree.moveToRoot();
        initGameBoard(tree.getRoot().getMaxChildrenCapacity());
    }

    private NodeStatus mapNodeStatus(GameStatus gameStatus) throws TicTacToeException {
        switch(gameStatus) {
            case WIN:
                return NodeStatus.WIN;
            case DRAW:
                return NodeStatus.DRAW;
            default:
                throw new TicTacToeException("Invalid node status: " + gameStatus);
        }
    }

    public GameStatus getGameStatus() {
        if (checkRows() == GameStatus.WIN) {
            return GameStatus.WIN;
        }

        if (checkColumns() == GameStatus.WIN) {
            return GameStatus.WIN;
        }

        if (checkDiagonals() == GameStatus.WIN) {
            return GameStatus.WIN;
        }

        for(GameFigure figure: gameBoard) {
            if(figure.equals(GameFigure.EMPTY)) {
                return GameStatus.CONTINUE;
            }
        }

        return GameStatus.DRAW;
    }

    public Tree getTree() {
        return tree;
    }

    private GameStatus checkDiagonals() {

        if(checkLeftDiagonal() == GameStatus.WIN) {
            return GameStatus.WIN;
        }
        if(checkRightDiagonal() == GameStatus.WIN) {
            return GameStatus.WIN;
        }
        return GameStatus.CONTINUE;
    }

    private GameStatus checkRightDiagonal() {
        int boardSize = tree.getRoot().getMaxChildrenCapacity();
        int boardSQRT = (int)Math.sqrt(tree.getRoot().getMaxChildrenCapacity());
        GameFigure figure = gameBoard.get(boardSize-boardSQRT);

        if(figure == GameFigure.EMPTY) {
            return GameStatus.CONTINUE;
        }

        for(int i = boardSize-boardSQRT; i>= boardSQRT-1; i-=(boardSQRT-1)) {
            if(!ifFigureMatch(figure, i)) {
                return GameStatus.CONTINUE;
            }
        }
        return GameStatus.WIN;
    }

    private GameStatus checkLeftDiagonal() {
        int boardSize = tree.getRoot().getMaxChildrenCapacity();
        int boardSQRT = (int)Math.sqrt(tree.getRoot().getMaxChildrenCapacity());
        GameFigure figure = gameBoard.get(0);

        if(figure == GameFigure.EMPTY) {
            return GameStatus.CONTINUE;
        }

        for(int i = 0; i<boardSize; i+=boardSQRT+1) {
            if(!ifFigureMatch(figure, i)) {
                return GameStatus.CONTINUE;
            }
        }
        return GameStatus.WIN;
    }

    private GameStatus checkRows() {
        int boardSize = tree.getRoot().getMaxChildrenCapacity();
        int boardSQRT = (int)Math.sqrt(tree.getRoot().getMaxChildrenCapacity());
        int index;
        GameFigure figure = GameFigure.EMPTY;
        boolean match = false;

        for(int i=0; i<boardSize; i+=boardSQRT ) {
            for(int j=0; j<boardSQRT; j++ ) {
                index = i + j;

                if(gameBoard.get(index) == GameFigure.EMPTY) {
                    match = false;
                    break;
                }
                if(j==0) {
                    figure = gameBoard.get(index);
                } else {
                    match = ifFigureMatch(figure, index);
                    if(!match) {
                        break;
                    }
                }
            }
            if(match) {
                return GameStatus.WIN;
            }

        }
        return GameStatus.CONTINUE;
    }

    private GameStatus checkColumns() {
        int boardSize = tree.getRoot().getMaxChildrenCapacity();
        int boardSQRT = (int)Math.sqrt(tree.getRoot().getMaxChildrenCapacity());
        int index;
        GameFigure figure = GameFigure.EMPTY;
        boolean match = false;

        for(int i=0; i<boardSQRT; i++) {
            for(int j=0; j<boardSize; j+=boardSQRT) {
                index = i+j;

                if(gameBoard.get(index) == GameFigure.EMPTY) {
                    match = false;
                    break;
                }
                if(j==0) {
                    figure = gameBoard.get(index);
                } else {
                    match = ifFigureMatch(figure, index);
                    if(!match) {
                        break;
                    }
                }
            }
            if(match) {
                return GameStatus.WIN;
            }
        }
        return GameStatus.CONTINUE;
    }

    private boolean ifFigureMatch(GameFigure figure, int index){
        return gameBoard.get(index) == figure;
    }

    private int findEmptyPositionForNewNode() throws TicTacToeException {
        for(int i=0; i<gameBoard.size(); i++) {
            if(gameBoard.get(i) == GameFigure.EMPTY) {

                try {
                    tree.findChildNodeWithGivenPosition(i);
                } catch (NodeNotFoundException e) {
                    return i;
                }
            }
        }
        throw new TicTacToeException("There are no empty slots on the game board");
    }

    /**
     * Finds the best Node according to the Priority schema
     * @return Node best node
     * @exception TicTacToeException if there are no empty slots left,
     * CreateNewNodeException if a new node should be created
     */
    private Node findBestNode() throws CreateNewNodeException, TicTacToeException {

        for(NodeStatus status : nodeStatusPrioritySchema) {

            if(status == NodeStatus.NEW_NODE && tree.getCurrentNode().getChildren().size() < tree.getCurrentNode().getMaxChildrenCapacity()) {
                throw new CreateNewNodeException("New node should be created");
            }

            for(Node node : tree.getCurrentNode().getChildren()) {
                if( node.getStatus() == status) {
                    return node;
                }
            }
        }
        throw new TicTacToeException("Node not found");
    }

    /**
     * Initialization of priority schema. Schema defines which status should be chosen to make a new step in the game
     * @param regime
     */
    private void initNodeStatusPrioritySchema(GameRegime regime) {
        // high priority -------> low priority
        nodeStatusPrioritySchema = new ArrayList<NodeStatus>();

        if(regime == GameRegime.BATTLE) {
            nodeStatusPrioritySchema.add(NodeStatus.WIN);
            nodeStatusPrioritySchema.add(NodeStatus.UNKNOWN);
            nodeStatusPrioritySchema.add(NodeStatus.NEW_NODE);
            nodeStatusPrioritySchema.add(NodeStatus.DRAW);
            nodeStatusPrioritySchema.add(NodeStatus.LOSE);
        }
        else {
            nodeStatusPrioritySchema.add(NodeStatus.UNKNOWN);
            nodeStatusPrioritySchema.add(NodeStatus.NEW_NODE);
            nodeStatusPrioritySchema.add(NodeStatus.WIN);
            nodeStatusPrioritySchema.add(NodeStatus.DRAW);
            nodeStatusPrioritySchema.add(NodeStatus.LOSE);
        }
    }

    private void initGameBoard(int boardSize){
        gameBoard = new ArrayList<GameFigure>(boardSize);
        for(int i=0; i<boardSize; i++){
            gameBoard.add(i, GameFigure.EMPTY);
        }
    }
}
