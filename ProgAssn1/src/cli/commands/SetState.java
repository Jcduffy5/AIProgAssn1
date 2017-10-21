package cli.commands;

import cli.Environment;
import java.io.PrintStream;

/**
 * Created by jcduf on 9/26/2017.
 */
public class SetState extends AbstractCommand {

    public static final String NAME = "setState <state>";
    public static final String CALL_OUT = "setState";
    public static final String DESCRIPTION = "Sets the state of the puzzle.";
    private static final SetState INSTANCE = new SetState();

    /**/
    public static SetState getINSTANCE() {
        return SetState.INSTANCE;
    }

    private SetState() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return SetState.CALL_OUT;
    }

    @Override
    public String getName() {
        return SetState.NAME;
    }

    @Override
    public String getDescription() {
        return SetState.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();

        StringBuilder sb = new StringBuilder();
        if((args[1].length() == 3) && (args[2].length() == 3) && (args[3].length() == 3)){
            sb.append(args[1]);
            sb.append(args[2]);
            sb.append(args[3]);
            if(validateStateString(sb.toString())){
                env.setCurrentState(sb.toString());
                out.println("[ok] State set to " + sb.toString());
            }else{
                printInvalidStateMsg(err);
            }
        }else{
            printInvalidStateMsg(err);
        }

    }

    //ensures proposed state is valid
    public boolean validateStateString(String candidate){
        boolean found1 = false, found2 = false, found3 = false, found4 = false, found5 = false, found6 = false, found7 = false, found8 = false, foundb = false;
        for(int i = 0; i < candidate.length(); i++){
            if(candidate.charAt(i) == '1'){
                found1 = true;
            }else if(candidate.charAt(i) == '2'){
                found2 = true;
            }else if(candidate.charAt(i) == '3'){
                found3 = true;
            }else if(candidate.charAt(i) == '4'){
                found4 = true;
            }else if(candidate.charAt(i) == '5'){
                found5 = true;
            }else if(candidate.charAt(i) == '6'){
                found6 = true;
            }else if(candidate.charAt(i) == '7'){
                found7 = true;
            }else if(candidate.charAt(i) == '8'){
                found8 = true;
            }else if(candidate.charAt(i) == 'b'){
                foundb = true;
            }
        }
        if(found1 && found2 && found3 && found4 && found5 && found6 && found7 && found8 && foundb){
            return true;
        }else{
            return false;
        }
    }

    public void printInvalidStateMsg(PrintStream err){
        err.println("The proposed state is invalid. Please ensure you include numbers 1-8 as well as a blank space denoted by 'b' in the format of xxx xxx xxx.");
    }
}


