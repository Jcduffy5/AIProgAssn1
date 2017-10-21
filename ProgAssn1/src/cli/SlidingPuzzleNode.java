package cli;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jcduf on 9/28/2017.
 */
public class SlidingPuzzleNode implements Node {

    private Node parent;
    private ArrayList<Character> currentState;
    private int action; //0 = up, 1 = down, 2 = right, 3 = left;
    private int pathCost;
    private int numParents ;

    public SlidingPuzzleNode(ArrayList<Character> state, Node nParent, int nAction, int cost) {
        currentState = state;
        parent = nParent;
        action = nAction;
        pathCost = cost;
        if (nParent != null) {
            numParents = nParent.getNumParents() + 1;
        } else {
            numParents = 0;
        }
    }

    public ArrayList<Character> getState(){
        return currentState;
    }

    public Node getParent(){
        return parent;
    }

    public int getAction(){
        return action;
    }

    public int getPathCost(){
        return pathCost;
    }

    public int getNumParents(){ return numParents; }

    public void setState(ArrayList<Character> newState){
        currentState = newState;
    }

    public void setParent(Node newParent){
        parent = newParent;
    }

    public void setAction(int newAction){
        action = newAction;
    }

    public void setPathCost(int newCost){
        pathCost = newCost;
    }

    public boolean hasParent(){
        if(parent != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof SlidingPuzzleNode)){
            return false;
        }
        SlidingPuzzleNode node = (SlidingPuzzleNode) o;
        return Arrays.equals(this.getState().toArray(), node.getState().toArray());

    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(this.getState().toArray());
    }

}
