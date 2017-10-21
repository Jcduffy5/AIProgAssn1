package cli.commands;

import cli.Environment;
import java.io.PrintStream;

/**
 * Created by jcduf on 9/26/2017.
 */
public class PrintState extends AbstractCommand {

    public static final String NAME = "printState";
    public static final String CALL_OUT = "printState";
    public static final String DESCRIPTION = "Prints the current state.";
    private static final PrintState INSTANCE = new PrintState();

    /**/
    public static PrintState getINSTANCE() {
        return PrintState.INSTANCE;
    }

    private PrintState() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return PrintState.CALL_OUT;
    }

    @Override
    public String getName() {
        return PrintState.NAME;
    }

    @Override
    public String getDescription() {
        return PrintState.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();

        for(int i = 0; i < 9; i++){
            if(i % 3 == 0){
                out.print("\n");
            }
            try {
                out.print(env.getCurrentState().get(i) + " ");
            }catch(NullPointerException e){
                out.println("No state has been set yet.");
                break;
            }
        }
        out.print("\n");
    }
}

