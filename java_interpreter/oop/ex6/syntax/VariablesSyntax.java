package oop.ex6.syntax;

import oop.ex6.main.ProjectRegex;
import oop.ex6.main.WarningException;


import java.util.regex.Matcher;

/**
 * a class that checks a variable assignment matches variable type
 */
public class VariablesSyntax extends Syntax {


    /*different variables types representation*/
    private final static String INTEGER = "int";
    private final static String STRING = "String";
    private final static String DOUBLE = "double";
    private final static String BOOLEAN = "boolean";
    private final static String CHAR = "char";


    /**
     * The function checks whether the assignment can be performed when type1 is the type of variable to
     * assign to the variable with type type2.
     *
     * @param type1 the type of the variable to assigemnt.
     * @param type2 the type of variable to assign into the first variable.
     * @return true if appropriate, otherwise false.
     */
    public static boolean checkType(String type1, String type2) {
        switch (type1) {
            case INTEGER:
                return (type2.equals(INTEGER));
            case STRING:
                return (type2.equals(STRING));
            case CHAR:
                return (type2.equals(CHAR));
            case DOUBLE:
                return (type2.equals(INTEGER) || type2.equals(DOUBLE));
            case BOOLEAN:
                return (type2.equals(INTEGER) || type2.equals(DOUBLE) || type2.equals(BOOLEAN));
            default:
                return false;
        }
    }

    /**
     * this function get a value and return the type that matches him.
     *
     * @param value the value to find his type.
     * @return the correct type.
     */
    public static String getValueType(String value) {
        value = value.trim();
        if (ProjectRegex.INT_VALID_VALUE.matcher(value).matches())
            return INTEGER;
        else if (ProjectRegex.BOOLEAN_VALID_VALUE.matcher(value).matches())
            return BOOLEAN;
        else if (ProjectRegex.DOUBLE_VALID_VALUE.matcher(value).matches())
            return DOUBLE;
        else if (ProjectRegex.CHAR_VALID_VALUE.matcher(value).matches())
            return CHAR;
        else if (ProjectRegex.STRING_VALID_VALUE.matcher(value).matches())
            return STRING;
        else
            return "null";
    }


    /*makes sure that a variable name wasn't used before*/
    public static void isValidName(String varName) throws WarningException {
        Matcher matcher = ProjectRegex.VARIABLE_VALID_NAME.matcher(varName);
        if (!matcher.matches())
            throw new WarningException("1");
    }

}

