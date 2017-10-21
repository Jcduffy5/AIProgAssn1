package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import cli.commands.*;

public class Main {

        public static final HashMap<String, Command> COMMANDS;

        static {
            COMMANDS = new HashMap<>();
            COMMANDS.put(Help.CALL_OUT, Help.getINSTANCE());
            COMMANDS.put(Help.CALL_OUT, Help.getINSTANCE());
            COMMANDS.put(MaxNodes.CALL_OUT, MaxNodes.getINSTANCE());
            COMMANDS.put(Move.CALL_OUT, Move.getINSTANCE());
            COMMANDS.put(PrintState.CALL_OUT, PrintState.getINSTANCE());
            COMMANDS.put(RandomizeState.CALL_OUT, RandomizeState.getINSTANCE());
            COMMANDS.put(SetState.CALL_OUT, SetState.getINSTANCE());
            COMMANDS.put(Solve.CALL_OUT, Solve.getINSTANCE());
            //TODO: Set puzzle type (rubik's or 8-puzzle)
        }

    public static void main(String[] args) {
        boolean readingFromFile = false;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a command: ");
        String inputStr = sc.nextLine();
        args = inputStr.split(" ");
        while(!args[0].equals("exit")) {
            if(args[0].equals("file")){
                try {
                    File file = new File(args[1]);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        args = line.split(" ");
                        final Command cmd = findCommand(args);

                        if (cmd == null) {
                            System.err.println("first Command not found or invalid command provided. See help command for options.");
                        } else {
                            cmd.run(args);
                        }
                    }
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                final Command cmd = findCommand(args);

                if (cmd == null) {
                    System.err.println("Command not found or invalid command provided. See help command for options.");
                } else {
                    cmd.run(args);
                }
                System.out.print("Enter a command: ");
                inputStr = sc.nextLine();
                args = inputStr.split(" ");
            }
        }
    }

    private static Command findCommand(String[] args) {

        if (args == null || args.length == 0) {
            return null;
        }

        return COMMANDS.get(args[0]);
    }

}

