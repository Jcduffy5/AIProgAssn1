package cli.commands;

import cli.Environment;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jcduf on 9/26/2017.
 */
public class Move extends AbstractCommand {

    public static final String NAME = "move <direction>";
    public static final String CALL_OUT = "move";
    public static final String DESCRIPTION = "Moves the blank tile 'up', 'down', 'left, or 'right'.";
    private static final Move INSTANCE = new Move();
    private ArrayList<Character> globalState;


    /**/
    public static Move getINSTANCE() {
        return Move.INSTANCE;
    }

    private Move() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return Move.CALL_OUT;
    }

    @Override
    public String getName() {
        return Move.NAME;
    }

    @Override
    public String getDescription() {
        return Move.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        ArrayList<Character> newState;
        if(args[1].equalsIgnoreCase("up")){
            newState = moveUp(env.getCurrentState());
            if(newState.get(0) != '0') {
                env.setCurrentState(newState);
            }
        }else if(args[1].equalsIgnoreCase("down")){
            newState = moveDown(env.getCurrentState());
            if(newState.get(0) != '0') {
                env.setCurrentState(newState);
            }
        }else if(args[1].equalsIgnoreCase("right")){
            newState = moveRight(env.getCurrentState());
            if(newState.get(0) != '0') {
                env.setCurrentState(newState);
            }
        }else if(args[1].equalsIgnoreCase("left")){
            newState = moveLeft(env.getCurrentState());
            if(newState.get(0) != '0') {
                env.setCurrentState(newState);
            }
        }else{
            err.println("Direction not recognized. Please choose 'up', 'down', 'left', or 'right'.");
        }

    }

    //the movement methods will return an ArrayList with a single '0' in it if the move is impossible

    public static ArrayList<Character> moveUp(ArrayList<Character> state){

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        ArrayList<Character> newState = new ArrayList<>(state);

        //if moving in 8-puzzle
        if(env.getCurrentPuzzle()){
            if((state.get(0).compareTo('b') == 0) || (state.get(1).compareTo('b') == 0) || (state.get(2).compareTo('b') == 0) ){
                //err.println("Cannot move up in this state.");
                return new ArrayList<Character>(Arrays.asList('0'));
            }else{
                int index = getIndexOfBlank(state);
                Character temp = state.get(index - 3);
                newState.set(index - 3, state.get(index));
                newState.set(index, temp);

            }
        }else{
            //rubik's cube

        }
        return newState;
    }

    public static ArrayList<Character> moveDown(ArrayList<Character> state){

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        ArrayList<Character> newState = new ArrayList<>(state);

        //if moving in 8-puzzle
        if(env.getCurrentPuzzle()){
            if((state.get(6).compareTo('b') == 0) || (state.get(7).compareTo('b') == 0) || (state.get(8).compareTo('b') == 0) ){
                //err.println("Cannot move down in this state.");
                return new ArrayList<Character>(Arrays.asList('0'));
            }else{
                int index = getIndexOfBlank(state);
                Character temp = state.get(index + 3);
                newState.set(index + 3, state.get(index));
                newState.set(index, temp);
            }
        }else{
            //rubik's cube

        }
        return newState;
    }

    public static ArrayList<Character> moveRight(ArrayList<Character> state){

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        ArrayList<Character> newState = new ArrayList<>(state);
        //if moving in 8-puzzle
        if(env.getCurrentPuzzle()){

            if((state.get(2).compareTo('b') == 0) || (state.get(5).compareTo('b') == 0) || (state.get(8).compareTo('b') == 0) ){
                //err.println("Cannot move right in this state.");
                return new ArrayList<Character>(Arrays.asList('0'));
            }else{
                int index = getIndexOfBlank(state);
                Character temp = state.get(index + 1);
                newState.set(index + 1, state.get(index));
                newState.set(index, temp);
            }
        }else{
            //rubik's cube

        }
        return newState;
    }

    public static ArrayList<Character> moveLeft(ArrayList<Character> state){

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        ArrayList<Character> newState = new ArrayList<>(state);

        //if moving in 8-puzzle
        if(env.getCurrentPuzzle()){
            if((state.get(0).compareTo('b') == 0) || (state.get(3).compareTo('b') == 0) || (state.get(6).compareTo('b') == 0) ){
                //err.println("Cannot move left in this state.");
                return new ArrayList<Character>(Arrays.asList('0'));
            }else{
                int index = getIndexOfBlank(state);
                Character temp = state.get(index - 1);
                newState.set(index - 1, state.get(index));
                newState.set(index, temp);
            }
        }else{
            //rubik's cube

        }
        return newState;
    }

    public static int getIndexOfBlank(ArrayList<Character> state){
        for(int i = 0; i < 9; i++){
            if(state.get(i).compareTo('b') == 0){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Character> getGlobalState(){
        return globalState;
    }
}
