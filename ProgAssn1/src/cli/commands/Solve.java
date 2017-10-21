package cli.commands;

import cli.Environment;
import cli.Node;
import cli.SlidingPuzzleNode;

import java.io.PrintStream;
import java.util.*;

/**
 * Created by jcduf on 9/26/2017.
 */
public class Solve extends AbstractCommand {

    public static final String NAME = "solve <A-star/beam> <heuristic/k>";
    public static final String CALL_OUT = "solve";
    public static final String DESCRIPTION = "Solves the puzzle using either A* search or local beam search.";
    private static final Solve INSTANCE = new Solve();

    public PriorityQueue<SlidingPuzzleNode> queue;
    public HashSet<SlidingPuzzleNode> visited = new HashSet<>();
    public ArrayList<String> moveList = new ArrayList<>();

    public SlidingPuzzleNode node;

    /**/
    public static Solve getINSTANCE() {
        return Solve.INSTANCE;
    }

    private Solve() {
        // singleton
    }

    @Override
    public String getCallOut() {
        return Solve.CALL_OUT;
    }

    @Override
    public String getName() {
        return Solve.NAME;
    }

    @Override
    public String getDescription() {
        return Solve.DESCRIPTION;
    }

    @Override
    public void run(String[] args) {

        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();

        if(args[1].equalsIgnoreCase("A-star")){
            if(args[2].equalsIgnoreCase("h1")){
                queue = new PriorityQueue<>(100, new Comparator<SlidingPuzzleNode>(){
                    @Override
                    public int compare(SlidingPuzzleNode node1, SlidingPuzzleNode node2){
                        return Integer.compare(node1.getPathCost(), node2.getPathCost());
                    }
                });
                ArrayList<Character> state = env.getCurrentState();
                SlidingPuzzleNode rootNode = new SlidingPuzzleNode(state, null, -1, getHeuristic(state, true));
                aStar(rootNode, true);
            }else if(args[2].equalsIgnoreCase("h2")){
                queue = new PriorityQueue<>(100, new Comparator<SlidingPuzzleNode>(){
                    @Override
                    public int compare(SlidingPuzzleNode node1, SlidingPuzzleNode node2){
                        return Integer.compare(node1.getPathCost(), node2.getPathCost());
                    }
                });
                ArrayList<Character> state = env.getCurrentState();
                SlidingPuzzleNode rootNode = new SlidingPuzzleNode(state, null, -1, getHeuristic(state, false));
                aStar(rootNode, false);
            }else{
                err.println("Did not recognize heuristic. Please choose either 'h1' or 'h2'.");
            }

        }else if(args[1].equalsIgnoreCase("beam")){
            ArrayList<Character> state = env.getCurrentState();
            SlidingPuzzleNode rootNode = new SlidingPuzzleNode(state, null, -1, getHeuristic(state, false));
            localBeam(rootNode, Integer.parseInt(args[2]));
        }else{
            err.println("Did not recognize search method, please choose either 'A-star' or 'beam'.");
        }

    }

    public void aStar(SlidingPuzzleNode rootNode, boolean isH1){


        final Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();
        int nodesConsidered = 0;
        queue.clear();
        visited.clear();
        queue.add(rootNode);
        while(!queue.isEmpty()){
            if(nodesConsidered > env.getMaxNodes()){
                err.println("[error] Max nodes of " + env.getMaxNodes() + " has been exceeded.");
                return;
            }
            this.node = queue.poll();
            nodesConsidered++;

            if(isSolved(this.node.getState())){
                countMoves(this.node);
                if(!moveList.isEmpty()) {
                    out.println(moveList.size() + " moves were made to solve the puzzle.");
                    out.print("The moves made were: ");
                    for (int i = moveList.size() - 1; i >= 0; i--) {
                        out.print(moveList.get(i) + " ");
                    }
                    out.println("");
                }else{
                    out.println("The puzzle was already in a solved state. Zero moves were taken.");
                }
                return;
            }

            visited.add(node);

            if(Move.moveUp(node.getState()).size() != 1){
                ArrayList<Character> newState = Move.moveUp(node.getState());
                SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, node, 0,  1 + node.getNumParents() + getHeuristic(newState, isH1));
                addToQueue(newNode);
            }
            if(Move.moveDown(node.getState()).size() != 1){
                ArrayList<Character> newState = Move.moveDown(node.getState());
                SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, node, 1, 1 + node.getNumParents() + getHeuristic(newState, isH1));
                addToQueue(newNode);
            }
            if(Move.moveLeft(node.getState()).size() != 1){
                ArrayList<Character> newState = Move.moveLeft(node.getState());
                SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, node, 2, 1 + node.getNumParents() + getHeuristic(newState, isH1));
                addToQueue(newNode);
            }
            if(Move.moveRight(node.getState()).size() != 1){
                ArrayList<Character> newState = Move.moveRight(node.getState());
                SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, node, 3, 1 + node.getNumParents() + getHeuristic(newState, isH1));
                addToQueue(newNode);
            }
        }
    }

    public static int getHeuristic(ArrayList<Character> state, boolean isH1){
        Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        int heuristic = 0;

        if(isH1){
            //number of misplaced tiles
            for(int i = 0; i < 9; i++){
                if(i == 0){
                    if(state.get(0) != 'b') {
                        heuristic++;
                    }
                }
                else if(state.get(i) != Character.forDigit(i, 10)){
                    heuristic++;
                }
            }
        }else{
            for(int i = 0; i < 9; i++){
                if(state.get(i).compareTo('b') != 0){
                    heuristic += getManhattanDistance(i, Integer.parseInt(state.get(i).toString()));
                }
            }
        }
        return heuristic;
    }

    //returns the manhattan distance a tile needs to travel to get to goal state.
    public static int getManhattanDistance(int currentIndex, int goalIndex){
        return Math.abs((currentIndex / 3) - ((goalIndex) / 3)) + Math.abs((currentIndex % 3) - ((goalIndex) % 3));
    }

    //make sure there aren't redundant/visited states in queue
    private void addToQueue(SlidingPuzzleNode node){
        Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        if(node != null && node.getState().size() != 1){
            if(!visited.contains(node)){
                queue.add(node);
            }
        }
    }

    public boolean isSolved(ArrayList<Character> candidate){
        Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        ArrayList<Character> goal = env.goalState;
        boolean solved = true;
        for(int i = 0; i < candidate.size(); i++){
            if(candidate.get(i).compareTo(goal.get(i)) != 0){
                solved = false;
            }
        }
        env.setCurrentState(candidate);
        return solved;
    }

    public void countMoves(Node leafNode){
        moveList.clear();
        Node currentNode = leafNode;

        while(currentNode.hasParent()){
            moveList.add(intToDirection(currentNode.getAction()));
            //System.out.println(currentNode.getState());
            currentNode = currentNode.getParent();

        }
        //System.out.println(currentNode.getState());
    }

    public String intToDirection(int input){
        if(input == 0){
            return "up";
        }else if(input == 1){
            return "down";
        }else if(input == 2){
            return "left";
        }else if(input == 3){
            return "right";
        }else{
            return "";
        }
    }

    //uses manhattan distance heuristic
    public void localBeam(SlidingPuzzleNode node, int k){
        Environment env = Environment.getINSTANCE();
        final PrintStream out = env.getOutPrintStream();
        final PrintStream err = env.getErrPrintStream();

        boolean solved = false;
        moveList.clear();

        queue = new PriorityQueue<>(11, new Comparator<Node>(){
            @Override
            public int compare(Node node1, Node node2){
                return getHeuristic(node1.getState(), false) - getHeuristic(node2.getState(), false);
            }
        });

        ArrayList<Character> randomState = new ArrayList<Character>();
        ArrayList<Integer> heuristics = new ArrayList<>();
        SlidingPuzzleNode[] beam = new SlidingPuzzleNode[k];
        SlidingPuzzleNode workingNode;
        boolean plateau, stuckAtOne;
        beam[0] = node;
        int nodesConsidered = 0;
        int plateauCounter = 0; //this increasingly 'shakes up' the moves if a plateau is hit.
        if(isSolved(beam[0].getState())){
            solved = true;
            out.println("The puzzle was already in a solved state and no moves were made.");
            return;
        }
        while(!solved ) {
            for(int i = 0; i < k; i++) {

                plateau = true;
                stuckAtOne = false;
                if(beam[i] != null) {
                    if(nodesConsidered > env.getMaxNodes()){
                        err.println("[error] Max nodes of " + env.getMaxNodes() + " has been exceeded.");
                        return;
                    }
                    nodesConsidered++;
                    heuristics.clear();
                    workingNode = beam[i];
                    if (Move.moveUp(workingNode.getState()).size() != 1) {
                        ArrayList<Character> newState = Move.moveUp(workingNode.getState());
                        SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, workingNode, 0, getHeuristic(newState, false));
                        if(getHeuristic(newState,false) <= getHeuristic(workingNode.getState(), false)) {
                            plateau = false;
                            queue.add(newNode);
                        }
                        if(getHeuristic(newState,false) == 1 &&(getHeuristic(workingNode.getState(), false) == 1)){
                            stuckAtOne = true;
                        }
                    }
                    if (Move.moveDown(workingNode.getState()).size() != 1) {
                        ArrayList<Character> newState = Move.moveDown(workingNode.getState());
                        SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, workingNode, 1, getHeuristic(newState, false));
                        if(getHeuristic(newState,false) <= getHeuristic(workingNode.getState(), false)) {
                            plateau = false;
                            queue.add(newNode);
                        }
                        if(getHeuristic(newState,false) == 1 &&(getHeuristic(workingNode.getState(), false) == 1)){
                            stuckAtOne = true;
                        }
                    }
                    if (Move.moveLeft(workingNode.getState()).size() != 1) {
                        ArrayList<Character> newState = Move.moveLeft(workingNode.getState());
                        SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, workingNode, 2, getHeuristic(newState, false));
                        if(getHeuristic(newState,false) <= getHeuristic(workingNode.getState(), false)) {
                            plateau = false;
                            queue.add(newNode);
                        }
                        if(getHeuristic(newState,false) == 1 &&(getHeuristic(workingNode.getState(), false) == 1)){
                            stuckAtOne = true;
                        }
                    }
                    if (Move.moveRight(workingNode.getState()).size() != 1) {
                        ArrayList<Character> newState = Move.moveRight(workingNode.getState());
                        SlidingPuzzleNode newNode = new SlidingPuzzleNode(newState, workingNode, 3, getHeuristic(newState, false));
                        if(getHeuristic(newState,false) <= getHeuristic(workingNode.getState(), false)) {
                            plateau = false;
                            queue.add(newNode);
                        }
                        if(getHeuristic(newState,false) == 1 &&(getHeuristic(workingNode.getState(), false) == 1)){
                            stuckAtOne = true;
                        }
                    }
                    if(stuckAtOne){
                        queue.add(RandomizeState.randomizeNodes(workingNode, 5));
                    }
                    //if stuck, randomize a bit.
                    if(plateau) {
                        plateauCounter++;
                        if(plateauCounter > 5){ //should put a cap on this thing.
                            plateauCounter = 5;
                        }
                        queue.add(RandomizeState.randomizeNodes(workingNode, plateauCounter));
                    }else{
                        plateauCounter = 0;
                    }
                }
            }
            int counter = 0;
            while(!queue.isEmpty() && counter < k){
                beam[counter] = queue.poll();
                counter++;
            }

            for(int i = 0; i < k; i++){
                if(beam[i] != null) {
                    if (isSolved(beam[i].getState())) {
                        countMoves(beam[i]);
                        out.println(moveList.size() + " moves were made to solve the puzzle.");
                        out.print("The moves made were: ");
                        for (int j = moveList.size() - 1; j >= 0; j--) {
                            out.print(moveList.get(j) + " ");
                        }
                        out.println("");
                        return;
                    }
                }
            }
            queue.clear();
        }



    }


}
