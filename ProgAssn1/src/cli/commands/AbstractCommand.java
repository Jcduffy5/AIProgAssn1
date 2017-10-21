package cli.commands;

/**
 * Created by jcduf on 9/26/2017.
 */
public abstract class AbstractCommand implements Command{
    /**
     * returns the next index of the provided arg in the args array if it exists
     * or returns null if unsatisfied.
     *
     * @param args
     * @param arg
     * @return
     */
    protected String getArgIfExists(String[] args, String arg) {
        return getArgIfExists(args, arg, null);
    }

    /**
     * returns the next index of the provided arg in the args array if it exists
     * or returns defaultReturn if unsatisfied.
     *
     * @param args
     * @param arg
     * @return
     */
    protected String getArgIfExists(String[] args, String arg, String defaultReturn) {

        if (args == null || args.length == 0) {
            return defaultReturn;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase(arg)) {
                if (args.length > (i + 1)) {
                    return args[i + 1];
                } else {
                    return defaultReturn;
                }
            }
        }
        return defaultReturn;
    }
}
