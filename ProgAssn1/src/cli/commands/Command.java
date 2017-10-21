package cli.commands;

/**
 * Created by jcduf on 9/26/2017.
 */
public interface Command {

    public String getName();

    public String getCallOut();

    public String getDescription();

    public void run(String[] args);

}
