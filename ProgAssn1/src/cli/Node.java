package cli;

import java.util.ArrayList;

/**
 * Created by jcduf on 9/28/2017.
 */
public interface Node {

    public ArrayList<Character> getState();

    public Node getParent();

    public int getAction();

    public int getPathCost();

    public int getNumParents();

    public void setState(ArrayList<Character> newState);

    public void setParent(Node parent);

    public void setAction(int action);

    public void setPathCost(int pathCost);

    public boolean hasParent();

}
