package oop.ex6.syntax;

import oop.ex6.main.ProjectRegex;
import oop.ex6.main.WarningException;
import oop.ex6.scope.Scope;
import oop.ex6.variable.Variable;
import oop.ex6.variable.VariableFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * A class used to determine if a method given arguments are valid
 */
public class MethodSyntax extends Syntax {

    /**
     * a method that keeps track of method argument list
     *
     * @param arguments given arguments
     * @param scope     scope stack
     * @return variable list
     * @throws WarningException if there is a problem with one or more of the variables given
     */
    public static LinkedList<Variable> methodArgumentsList(String arguments, Stack<Scope> scope) throws
            WarningException {
        String[] tempList = arguments.split(", ", -1);
        LinkedList<Variable> variablesList = new LinkedList<>();
        if (ProjectRegex.WHITE_SPACE_PATTERN.matcher(arguments).matches()) //no arguments
            return variablesList;
        ArrayList<String[]> variablesTempList = createTempVariableList(tempList);

        for (String[] variableList : variablesTempList) {
            if (variableList.length < 2) {
                throw new WarningException("1");
            }
            String type = variableList[0];
            String name = variableList[1];
            if (variableList.length == 2) {
                variableNotFinal(variablesList, type, name, scope.peek());
            } else if (variableList.length == 3) {
                variableIsFinal(variablesList, name, variableList[2], type, scope.peek());
            } else {
                throw new WarningException("1");
            }
        }
        return variablesList;
    }

    /*
    create temporary variable list
     */
    private static ArrayList<String[]> createTempVariableList(String[] tempList) throws WarningException {
        ArrayList<String[]> variablesTempList = new ArrayList<>();
        for (String cellString : tempList) {
            if (cellString == null) //if the last char in the string is ',' - illegal.
                throw new WarningException("1");
            cellString = cellString.trim();
            String[] variable = cellString.split("\\s+");
            variablesTempList.add(variable);
        }
        return variablesTempList;
    }

    /*
    creates a final variable
     */
    private static void variableIsFinal(LinkedList<Variable> variablesList, String name, String isFinal,
                                        String type, Scope scope) throws WarningException {
        Variable variable = VariableFactory.createVarObject(name, isFinal, type, scope);
        variablesList.add(variable);
    }

    /*
    creates a non final variable
     */
    private static void variableNotFinal(LinkedList<Variable> variablesList, String type, String name,
                                         Scope scope) throws WarningException {
        Variable variable = VariableFactory.createVarObject("null", type, name, scope);
        for (Variable var : variablesList)
            if (var.getVarName().equals(variable.getVarName()))
                throw new WarningException("1");
        variablesList.add(variable);
    }

}
