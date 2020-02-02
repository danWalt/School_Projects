package oop.ex6.syntax;

import oop.ex6.scope.Scope;
import oop.ex6.variable.Variable;

import java.util.regex.Pattern;

/**
 * an abstract class to check syntax of different given instances
 */
public abstract class Syntax {


    /**
     * The function gets a variable name and checks whether there is such a variable in the current
     * scope (or in its external scopes).
     *
     * @param name  the name of the variable to search
     * @param scope the scope that uses this variable name
     * @return true if there is such a variable, otherwise false
     */
    public static Variable findVariable(String name, Scope scope) {
        Scope currentScope = scope;
        while (currentScope != null) {
            for (Variable variable : currentScope.getLocalVariables()) {
                if (variable.getVarName().equals(name))
                    return variable;
            }
            currentScope = currentScope.getFatherScope();
        }
        return null;
    }


}
