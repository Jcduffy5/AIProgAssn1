package cli.commands;

import cli.Environment;
import java.io.PrintStream;

/**
 * Created by jcduf on 9/26/2017.
 */
public class MaxNodes extends AbstractCommand {

    public static final String NAME = "maxNodes <n>";
    public static final String CALL_OUT = "maxNodes";
    public static final String DESCRIPTION = "Specifies the maximum number of nodes to be considered during a search.";
    private static final MaxNodes INSTANCE = new MaxNodes();

    /**/
    public static MaxNodes getINSTANCE() {
        return MaxNodes.INSTANCE;
    }

    private MaxNodes() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return MaxNodes.CALL_OUT;
    }

    @Override
    public String getName() {
        return MaxNodes.NAME;
    }

    @Override
    public String getDescription() {
        return MaxNodes.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        try {
            int newAmt = Integer.parseInt(args[1]);
            env.setMaxNodes(newAmt);
            out.println("[ok] MaxNodes has been set to: " + newAmt);
        }catch(NumberFormatException e){
            err.println("Cannot set max nodes to a number that is not an integer.");
        }

    }
}

