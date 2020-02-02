package oop.ex6.main;

import java.util.regex.Pattern;

/**
 * a class that holds all project REGEX.
 */
public class ProjectRegex {

    /* regex check if line is a white space */
    public final static Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\s*");

    /* regex symbol for a ;*/
    final static Pattern SEMICOLON_PATTERN = Pattern.compile(";\\s*$");

    /* regex check if line end with "}" (and spaces may appear after it) */
    final static Pattern CURLY_BRACKETS_CLOSE_PATTERN = Pattern.compile("\\s*\\}\\s*$");

    /* regex check if line is a signature of opening a function.*/
    final static Pattern METHOD_PATTERN = Pattern.compile("^\\s*void\\s+([A-Za-z]\\w*)\\s*\\((.*)\\)\\s*\\{\\s*$");

    /* regex check if line is a calling a function.*/
    final static Pattern METHOD_CALL = Pattern.compile("^\\s*([A-Za-z]+\\w*)\\s*\\((.*)\\)\\s*;\\s*$");

    /* regex check if line is while line. */
    final static Pattern IF_PATTERN = Pattern.compile("^\\s*if\\s*\\((.*)\\)\\s*\\{\\s*$");

    /* regex check if line is if line.  */
    final static Pattern WHILE_PATTERN = Pattern.compile("^\\s*while\\s*\\((.*)\\)\\s*\\{\\s*$");

    /*check variable row format*/
    final static Pattern VARIABLE_ROW = Pattern.compile("^\\s*(final\\s+)?(char|boolean|int|double|String)" +
            "\\s+(.+);$");

    /*a regex to find return statement line*/
    final static Pattern RETURN_STATEMENT = Pattern.compile("\\s*return\\s*;\\s*");

    /**
     * regex used to find variables value
     */
    public final static Pattern INT_VALID_VALUE = Pattern.compile("^[-+]?\\d+$");
    public final static Pattern BOOLEAN_VALID_VALUE = Pattern.compile("^[-+]?\\d+(\\.\\d+)?$|^(true|false)" +
            "$");
    public final static Pattern CHAR_VALID_VALUE = Pattern.compile("\'.\'");
    public final static Pattern STRING_VALID_VALUE = Pattern.compile("\".*\"");
    public final static Pattern DOUBLE_VALID_VALUE = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
    public final static Pattern VARIABLE_VALID_NAME = Pattern.compile("([A-Za-z]+\\w*)|(_\\w+)");


}
