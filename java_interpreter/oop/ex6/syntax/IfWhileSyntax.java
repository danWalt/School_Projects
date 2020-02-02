package oop.ex6.syntax;

import oop.ex6.main.ProjectRegex;
import oop.ex6.scope.Scope;
import oop.ex6.variable.Variable;

import java.util.regex.Matcher;

public class IfWhileSyntax extends Syntax {


    /**
     * The function check if the condition of if\while is valid.
     *
     * @param scope     the scope that contains this if/while statement.
     * @param condition the condition to check.
     * @return true the condition is a valid one, false otherwise.
     */
    public static boolean isValidCondition(Scope scope, String condition) {
        if (ProjectRegex.WHITE_SPACE_PATTERN.matcher(condition).matches()) //if the condition is nothing,
            // like "if()"
            return false;
        Matcher matcher;
        String[] varCondition = condition.split("&&|\\|\\|", -1);
        for (String var : varCondition) {
            if (!checkVariable(scope, var))
                return false;
        }
        return true;
    }

    /**
     * An auxiliary function that checks whether the variables in the condition of if\while scope are valid.
     *
     * @param scope    the scope that contains this condition.
     * @param variable a variable to check.
     * @return true if it is a valid variable, false otherwise.
     */
    private static boolean checkVariable(Scope scope, String variable) {
        variable = variable.trim();
        if (variable.equals("true") || variable.equals("false")) //true/false
            return true;
        else if (variable.matches("-?[0-9]+(\\.[0-9]+)?")) //double/int
            return true;
        else if (variable.matches("\\w")) { //variables
            Variable var = findVariable(variable, scope);
            if (var != null) {
                if (var.getVarType().equals("String") || var.getVarType().equals("char") || !var.isInitialized())
                    return false;
                else
                    return true;
            } else
                return false;
        } else
            return false;
    }


}
