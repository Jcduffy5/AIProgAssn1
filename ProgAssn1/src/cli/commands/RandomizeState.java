package cli.commands;

import cli.Environment;
import cli.SlidingPuzzleNode;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jcduf on 9/26/2017.
 */
public class RandomizeState extends AbstractCommand {

    public static final String NAME = "randomizeState <n>";
    public static final String CALL_OUT = "randomizeState";
    public static final String DESCRIPTION = "Makes n random moves from the goal state.";
    private static final RandomizeState INSTANCE = new RandomizeState();

    /**/
    public static RandomizeState getINSTANCE() {
        return RandomizeState.INSTANCE;
    }

    private RandomizeState() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return RandomizeState.CALL_OUT;
    }

    @Override
    public String getName() {
        return RandomizeState.NAME;
    }

    @Override
    public String getDescription() {
        return RandomizeState.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();

        //only increments numMoves if the move was possible.
        try{
            Integer n = Integer.parseInt(args[1]);
            ArrayList<Character> newState = randomize(env.getCurrentState(), n);
            env.setCurrentState(newState);
        }catch(NumberFormatException e){
            err.println("The number of random moves requested must be an integer.");
        }

    }

    public static ArrayList<Character> randomize(ArrayList<Character> state, int n){
        int numMoves = 0;
        ArrayList<Character> newState, tempState;
        Random random = new Random();
        newState = new ArrayList<>(state);
        while( numMoves < n){
            int num = random.nextInt(4);
            if(num == 0){
                tempState = Move.moveUp(newState);
                if(tempState.size() != 1) {
                    newState = tempState;
                    numMoves++;
                }
            }else if(num == 1){
                tempState = Move.moveDown(newState);
                if(tempState.size() != 1) {
                    newState = tempState;
                    numMoves++;
                }
            }else if(num == 2){
                tempState = Move.moveLeft(newState);
                if(tempState.size() != 1) {
                    newState = tempState;
                    numMoves++;
                }
            }else if(num == 3){
                tempState = Move.moveRight(newState);
                if(tempState.size() != 1) {
                    newState = tempState;
                    numMoves++;
                }
            }
        }

        return newState;
    }

    public static SlidingPuzzleNode randomizeNodes(SlidingPuzzleNode node, int n){
        int numMoves = 0;
        ArrayList<Character> tempState;
        Random random = new Random();
        while( numMoves < n){
            int num = random.nextInt(4);
            if(num == 0){
                tempState = Move.moveUp(node.getState());
                if(tempState.size() != 1) {
                    node = new SlidingPuzzleNode(tempState, node, 0, Solve.getHeuristic(tempState, false));
                    numMoves++;
                }
            }else if(num == 1){
                tempState = Move.moveDown(node.getState());
                if(tempState.size() != 1) {
                    node = new SlidingPuzzleNode(tempState, node, 1, Solve.getHeuristic(tempState, false));
                    numMoves++;
                }
            }else if(num == 2){
                tempState = Move.moveLeft(node.getState());
                if(tempState.size() != 1) {
                    node = new SlidingPuzzleNode(tempState, node, 2, Solve.getHeuristic(tempState, false));
                    numMoves++;
                }
            }else if(num == 3){
                tempState = Move.moveRight(node.getState());
                if(tempState.size() != 1) {
                    node = new SlidingPuzzleNode(tempState, node, 3, Solve.getHeuristic(tempState, false));
                    numMoves++;
                }
            }
        }

        return node;
    }
}
