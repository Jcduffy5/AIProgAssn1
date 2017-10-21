package cli;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import cli.commands.*;

/**
 * Created by jcduf on 9/26/2017.
 */
public class Environment {

    public static final HashMap<String, Command> COMMANDS;

    static {
        COMMANDS = new HashMap<>();
        COMMANDS.put(Help.CALL_OUT, Help.getINSTANCE());
        COMMANDS.put(MaxNodes.CALL_OUT, MaxNodes.getINSTANCE());
        COMMANDS.put(Move.CALL_OUT, Move.getINSTANCE());
        COMMANDS.put(PrintState.CALL_OUT, PrintState.getINSTANCE());
        COMMANDS.put(RandomizeState.CALL_OUT, RandomizeState.getINSTANCE());
        COMMANDS.put(SetState.CALL_OUT, SetState.getINSTANCE());
        COMMANDS.put(Solve.CALL_OUT, Solve.getINSTANCE());
        //TODO: Set puzzle type (rubik's or 8-puzzle)
    }
    /* variables */
    private PrintStream outPrintStream;
    private PrintStream errPrintStream;
    public final ArrayList<Character> goalState = new ArrayList<>(Arrays.asList('b', '1', '2', '3', '4', '5', '6', '7', '8'));
    private ArrayList<Character> currentState = new ArrayList<>(goalState); //goal is default
    private int maxNodes = 1000000; //default of a million
    private boolean currentPuzzle = true; //true is 8-puzzle, false is rubik's cube; 8 puzzle is default.
    /**/
    private static final Environment INSTANCE = new Environment(System.out, System.err);

    public static Environment getINSTANCE() {
        return Environment.INSTANCE;
    }

    public static Environment getINSTANCE(PrintStream out, PrintStream err) {
        getINSTANCE().errPrintStream = err;
        getINSTANCE().outPrintStream = out;
        return getINSTANCE();
    }

    private Environment(PrintStream out, PrintStream err) {

        this.outPrintStream = out;
        this.errPrintStream = err;
    }

    public PrintStream getOutPrintStream() {
        return outPrintStream;
    }

    public PrintStream getErrPrintStream() {
        return errPrintStream;
    }

    public void setOutPrintStream(PrintStream outPrintStream) {
        this.outPrintStream = outPrintStream;
    }

    public void setErrPrintStream(PrintStream errPrintStream) {
        this.errPrintStream = errPrintStream;
    }

    public Collection<Command> getCommands(){
        return COMMANDS.values();
    }

    public Command parseCommand(String[] args) {

        if (args == null || args.length == 0) {
            return null;
        }

        return COMMANDS.get(args[0]);
    }

    public void setCurrentState(String newState){
        currentState = parseStateString(newState);
    }

    public void setCurrentState(ArrayList<Character> newState){
        currentState = newState;
    }

    public ArrayList<Character> getCurrentState(){
        return currentState;
    }

    public void setMaxNodes(int newAmt){
        maxNodes = newAmt;
    }

    public int getMaxNodes(){
        return maxNodes;
    }

    public void setCurrentPuzzle(boolean puzz){
        currentPuzzle = puzz;
    }

    public boolean getCurrentPuzzle(){
        return currentPuzzle;
    }

    public ArrayList<Character> parseStateString(String str){
        ArrayList<Character> newState = new ArrayList<>();
        for(int i = 0; i < str.length(); i++){
            newState.add(str.charAt(i));
        }
        return newState;
    }

}

