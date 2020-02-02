package oop.ex6.variable;

import oop.ex6.main.ProjectRegex;
import oop.ex6.main.WarningException;
import oop.ex6.scope.Scope;
import oop.ex6.syntax.Syntax;
import oop.ex6.syntax.VariablesSyntax;

import java.util.regex.Matcher;

/**
 * a factory that creates variable objects
 */
public class VariableFactory {

    /*in case that the current variable is of type int*/
    private final static String INTEGER = "int";

    /*in case that the current variable is of type String*/
    private final static String STRING = "String";

    /*in case that the current variable is of type double*/
    private final static String DOUBLE = "double";

    /*in case that the current variable is of type boolean*/
    private final static String BOOLEAN = "boolean";

    /*in case that the current variable is of type char*/
    private final static String CHAR = "char";


    /**
     * a factory method that decides what type of variable should be created
     *
     * @param finalString is the variable final or not
     * @param type        variable type
     * @param string      all declared variables names
     * @param scope       current scope
     * @return a variable object
     * @throws WarningException prints 1 if there is a problem with a variable name or a value that doesn't
     *                          match type.
     */
    public static Variable createVarObject(String finalString, String type, String string, Scope scope)
            throws
            WarningException {
        boolean isFinal = false;
        if (finalString != null && finalString.trim().equals("final")) {
            isFinal = true;
        }
        switch (type) {
            case INTEGER:
                if (string.contains("=")) { //with value
                    String[] intTuple = string.split("=");
                    if (intTuple.length != 2)
                        throw new WarningException("1");
                    String[] nameAndValue = trimNameAndValue(intTuple);
                    String name = nameAndValue[0];
                    String value = nameAndValue[1];
                    return createIntVar(type, name, isFinal, value, scope);

                } else { //without value
                    return createVarWithoutValue(INTEGER, string, isFinal);
                }
            case STRING:
                string = string.replaceAll(";", "").trim();
                if (string.contains("=")) {
                    String[] stringTuple = string.split("=");
                    if (stringTuple.length != 2)
                        throw new WarningException("1");
                    String[] nameAndValue = trimNameAndValue(stringTuple);
                    String name = nameAndValue[0];
                    String value = nameAndValue[1];
                    return createStringVar(type, name, isFinal, value, scope);

                } else { //without value
                    return createVarWithoutValue(STRING, string, isFinal);
                }
            case DOUBLE:
                string = string.replaceAll(";", "").trim();
                if (string.contains("=")) {
                    String[] doubleTuple = string.split("=");
                    if (doubleTuple.length != 2)
                        throw new WarningException("1");
                    String[] nameAndValue = trimNameAndValue(doubleTuple);
                    String name = nameAndValue[0];
                    String value = nameAndValue[1];
                    return createDoubleVar(type, name, isFinal, value, scope);

                } else { //without value
                    return createVarWithoutValue(DOUBLE, string, isFinal);
                }
            case BOOLEAN:
                string = string.replaceAll(";", "").trim();
                if (string.contains("=")) {
                    String[] booleanTuple = string.split("=");
                    if (booleanTuple.length != 2)
                        throw new WarningException("1");
                    String[] nameAndValue = trimNameAndValue(booleanTuple);
                    String name = nameAndValue[0];
                    String value = nameAndValue[1];
                    return createBooleanVar(type, name, isFinal, value, scope);

                } else { //without value
                    return createVarWithoutValue(BOOLEAN, string, isFinal);
                }
            case CHAR:
                string = string.replaceAll(";", "").trim();
                if (string.contains("=")) {
                    String[] charTuple = string.split("=");
                    if (charTuple.length != 2)
                        throw new WarningException("1");
                    String[] nameAndValue = trimNameAndValue(charTuple);
                    String name = nameAndValue[0];
                    String value = nameAndValue[1];
                    return createCharVar(type, name, isFinal, value, scope);

                } else { //without value
                    return createVarWithoutValue(CHAR, string, isFinal);
                }
            default:
                throw new WarningException("1");
        }
    }

    /*
    trims name and value
     */
    private static String[] trimNameAndValue(String[] nameValueTuple) throws WarningException {
        String[] nameAndValue = new String[2];
        nameAndValue[0] = nameValueTuple[0].trim();
        nameAndValue[1] = nameValueTuple[1].trim();
        VariablesSyntax.isValidName(nameAndValue[0]);
        return nameAndValue;

    }


    /*
    create char variable object with value
     */
    private static Variable createCharVar(String type, String name, boolean isFinal, String value, Scope
            scope) throws WarningException {
        if (ProjectRegex.CHAR_VALID_VALUE.matcher(value).matches()) {
            return new IntVar(type, name, isFinal, true);
        } else {
            Variable newVar = Syntax.findVariable(value, scope);
            if (newVar != null && newVar.isInitialized() && VariablesSyntax.checkType
                    (CHAR, newVar.getVarType()))
                return new IntVar(CHAR, name, isFinal, true);
            else
                throw new WarningException("1");
        }
    }

    /*
    create boolean variable object with value
     */
    private static Variable createBooleanVar(String type, String name, boolean isFinal, String value, Scope
            scope) throws WarningException {
        if (ProjectRegex.BOOLEAN_VALID_VALUE.matcher(value).matches()) {
            return new IntVar(type, name, isFinal, true);
        } else {
            Variable newVar = Syntax.findVariable(value, scope);
            if (newVar != null && newVar.isInitialized() && VariablesSyntax.checkType
                    (BOOLEAN, newVar.getVarType()))
                return new IntVar(BOOLEAN, name, isFinal, true);
            else
                throw new WarningException("1");
        }
    }

    /*
    create double variable object with value
     */
    private static Variable createDoubleVar(String type, String name, boolean isFinal, String value, Scope
            scope) throws WarningException {
        if (ProjectRegex.DOUBLE_VALID_VALUE.matcher(value).matches()) {
            return new IntVar(type, name, isFinal, true);
        } else {
            Variable newVar = Syntax.findVariable(value, scope);
            if (newVar != null && newVar.isInitialized() && VariablesSyntax.checkType
                    (DOUBLE, newVar.getVarType()))
                return new IntVar(DOUBLE, name, isFinal, true);
            else
                throw new WarningException("1");
        }
    }

    /*
    create string variable object with value
     */
    private static Variable createStringVar(String type, String name, boolean isFinal, String value, Scope
            scope) throws WarningException {
        if (ProjectRegex.STRING_VALID_VALUE.matcher(value).matches()) {
            return new IntVar(type, name, isFinal, true);
        } else {
            Variable newVar = Syntax.findVariable(value, scope);
            if (newVar != null && newVar.isInitialized() && VariablesSyntax.checkType
                    (STRING, newVar.getVarType()))
                return new IntVar(STRING, name, isFinal, true);
            else
                throw new WarningException("1");
        }
    }

    /*
    create integer variable object with value
     */
    private static IntVar createIntVar(String type, String name, boolean isFinal, String value, Scope
            scope) throws WarningException {
        if (ProjectRegex.INT_VALID_VALUE.matcher(value).matches()) {
            return new IntVar(type, name, isFinal, true);
        } else {
            Variable newVar = Syntax.findVariable(value, scope);
            if (newVar != null && newVar.isInitialized() && VariablesSyntax.checkType
                    (INTEGER, newVar.getVarType()))
                return new IntVar(INTEGER, name, isFinal, true);
            else
                throw new WarningException("1");
        }

    }

    /*
    create a variable object with no value assigned
     */
    private static Variable createVarWithoutValue(String type, String string, boolean isFinal) throws
            WarningException {
        if (isFinal)
            throw new WarningException("1"); //Final variable not initialized.
        VariablesSyntax.isValidName(string);
        switch (type) {
            case INTEGER:
                return new IntVar(INTEGER, string, false, false);
            case STRING:
                return new StringVar(STRING, string, false, false);
            case DOUBLE:
                return new DoubleVar(DOUBLE, string, false, false);
            case BOOLEAN:
                return new BooleanVar(BOOLEAN, string, false, false);
            case CHAR:
                return new CharVar(CHAR, string, false, false);
            default:
                throw new WarningException("1");
        }
    }


}

