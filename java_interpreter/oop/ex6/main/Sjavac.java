package oop.ex6.main;

import java.io.File;
import java.io.IOException;

/**
 * the main class
 */
public class Sjavac {

    private final static String VALID_CODE = "0";
    private final static String ILLEGAL_FILE = "2";
    private final static String ILLEGAL_CODE = "1";

    private final static String PARSING_PROBLEM = "problem in parsing the given file.";
    private final static String NUM_OF_ARGS_ILLEGAL = "number of args illegal.";
    private final static String PROBLEM_WITH_FILE = "There is a problem with the input file";

    /**
     * the method that runs the file check
     * @param args the file containing the code that should be checked
     */

    public static void main(String[] args) {
        try {
            if (args.length != 1)
                throw new IOException();
            File fileToCheck = new File(args[0]);
            FileReader fileReader = new FileReader(fileToCheck);
            System.out.println(VALID_CODE);
        } catch (IOException e){
            System.out.println(ILLEGAL_FILE);
            if (args.length != 1)
                System.err.println(NUM_OF_ARGS_ILLEGAL);
            else if (e.getMessage() == null)
                System.err.println(PARSING_PROBLEM);
            else
                System.err.println(PROBLEM_WITH_FILE);
        } catch (WarningException e){
            System.out.println(ILLEGAL_CODE);
        }
    }




}
