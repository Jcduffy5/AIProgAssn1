package cli.commands;

import cli.Environment;
import java.io.PrintStream;

/**
 * Created by jcduf on 9/26/2017.
 */
public final class Help implements Command {

    public static final String NAME = "help";
    public static final String CALL_OUT = "help";
    public static final String DESCRIPTION = "Help menu and system information.";
    private static final Help INSTANCE = new Help();

    /**/
    public static Help getINSTANCE() {
        return Help.INSTANCE;
    }

    private Help() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return Help.CALL_OUT;
    }

    @Override
    public String getName() {
        return Help.NAME;
    }

    @Override
    public String getDescription() {
        return Help.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {
        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();

        out.println("Welcome to help. Below are the available cli.commands.");
        out.println("");
        out.println("");
        out.println("|command\t\t|name\t\t|description");
        env.getCommands().forEach((command) -> {
            out.println(String.format("|%s\t\t|%s\t\t|%s",
                    command.getCallOut(),
                    command.getName(),
                    command.getDescription()));
        });
        out.println(String.format("|%s\t\t|%s\t\t|%s",
                "exit",
                "exit",
                "Exits the program."));
        out.println("");
        out.println("");

    }

}
