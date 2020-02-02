package oop.ex6.main;

import oop.ex6.scope.*;
import oop.ex6.syntax.*;
import oop.ex6.variable.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;

public class FileReader {

    /*a list that holds all lines in file*/
    private static LinkedList<String> codeLines;

    /*a stack that hold current open scopes*/
    private static Stack<Scope> openScopes;

    /*a global scope representing current file global scope*/
    private GlobalScope globalScope;


    /**
     * given a java text file this method converts it into a list of the lines
     *
     * @param codeFile the code file to process
     * @throws IOException   prints 2 if there is a problem with the given file
     * @throws WarningException prints 1 if the code format is bad
     */
    public FileReader(File codeFile) throws IOException, WarningException {
        codeLines = readLines(codeFile);
        openScopes = new Stack<Scope>();
        globalScope = new GlobalScope(null);
        openScopes.add(globalScope);
        addGlobalVariableAndMethods(codeLines);
        checkFile(codeLines);
    }


    /*creates a linked list of strings representing each line in the code*/
    private LinkedList<String> readLines(File file) throws IOException {
        LinkedList<String> linesArray = new LinkedList<>();
        BufferedReader br = new BufferedReader(new java.io.FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty() || !line.trim().equals("") || !line.trim().equals("\n")) {
                linesArray.add(line);
            }
        }
        return linesArray;
    }

    /*
     * the function creates two lists - a list with all the global variables and list with all the methods.
     *
     * @param codeFileLines a list that contains all the file's lines.
     * @throws WarningException prints 1 if there is a problem with the code format
     */
    private void addGlobalVariableAndMethods(LinkedList<String> codeFileLines) throws WarningException {
        for (String line : codeFileLines) {

            if (openScopes.isEmpty()) //More parentheses were closed than opened.
                throw new WarningException("1");

            // == start of method statement == //
            Matcher matcher1 = ProjectRegex.METHOD_PATTERN.matcher(line);
            if (matcher1.find() && openScopes.peek() == globalScope) {
                String name = matcher1.group(1).trim();
                String arguments = matcher1.group(2).trim();
                addToMethodList(name, arguments);
            }

            // == declare a new global variable line == //
            //Matcher matcher2 = VARIABLE_ROW.matcher(line);
            if (ProjectRegex.VARIABLE_ROW.matcher(line).matches() && openScopes.peek() == globalScope) {
                processVariable(line);
            }
            // checks if the current row contains an if condition or while loop pattern
            if (ProjectRegex.IF_PATTERN.matcher(line).matches() || ProjectRegex.WHILE_PATTERN.matcher(line).matches())
                openScopes.add(new IfWhileScope(openScopes.peek()));

            //checks a variable value line assignment
            if (line.contains("=") && openScopes.peek() == globalScope) {
                checkAssignment(line, globalScope);
            }

            //checks for closing a scope
            Matcher matcher3 = ProjectRegex.CURLY_BRACKETS_CLOSE_PATTERN.matcher(line);
            if (matcher3.find()) {
                if (openScopes.isEmpty()) {
                    throw new WarningException("1");
                } else {
                    openScopes.pop();
                }
            }
        }
        if (openScopes.isEmpty() || openScopes.peek() != globalScope)
            throw new WarningException("1");
    }

    /*
    once a row containing a global variable is located, this method process it and creates it.
     */
    private void processVariable(String line) throws WarningException {
        Matcher m = ProjectRegex.VARIABLE_ROW.matcher(line);
        m.matches();
        String isFinal = m.group(1);
        if (isFinal != null) {
            isFinal = isFinal.trim();
        }
        String type = m.group(2).trim();
        String nameAndValue;
        if (m.group(3).contains(",")) {
            String[] variables = m.group(3).split(",", -1);
            multipleVariableCreation(isFinal, type, variables);

        } else {
            nameAndValue = m.group(3).trim();
            oneVariableCreation(isFinal, type, nameAndValue);
        }
    }

    /*
    splits a row with multiple variable initialization and creates them
     */
    private void multipleVariableCreation(String isFinal, String type, String[] variables) throws WarningException {
        for (String variable : variables) {
            if (variable.equals("")) {
                throw new WarningException("1");
            }
            String nameAndValue = variable.trim();
            oneVariableCreation(isFinal, type, nameAndValue);
        }
    }

    /*
    adds a global variable to global variable list
     */
    private void oneVariableCreation(String isFinal, String type, String nameAndValue) throws
            WarningException {
        if (nameAndValue.contains("int") || nameAndValue.contains("double") | nameAndValue.contains
                ("String") | nameAndValue.contains("char") | nameAndValue.contains("boolean")) {
            throw new WarningException("1");
        } else {
            Variable newVariable = VariableFactory.createVarObject(isFinal, type, nameAndValue, openScopes.peek
                    ());
            checkIfVariableExists(newVariable.getVarName());
            globalScope.addLocalVariables(newVariable);
        }
    }

    /*
   checks if there is a variable with the same name
    */
    private void checkIfVariableExists(String varName) throws WarningException {
        for (Variable variable : globalScope.getLocalVariables()) {
            if (varName.equals(variable.getVarName()))
                throw new WarningException("1");
        }
    }


    /*
     * this function gets a method name and the method's arguments that are declared in the global scope.
     * the method then checks if this method name is valid. if it is, adds the method the methods list.
     *
     * @param methodName the name of the method
     * @param arguments  the arguments the method takes
     * @throws WarningException if the method illegal.
     */
    private void addToMethodList(String methodName, String arguments) throws WarningException {
        LinkedList<Variable> argumentsList;
        if (methodListIsEmpty()) {
            argumentsList = MethodSyntax.methodArgumentsList(arguments, openScopes);
            addSingleMethod(globalScope, argumentsList, methodName);
        } else {
            checkIfMethodExists(methodName);
            argumentsList = MethodSyntax.methodArgumentsList(arguments, openScopes);
            addSingleMethod(globalScope, argumentsList, methodName);
        }
    }

    /*
    checks if a method with the same name had been created before
     */
    private void checkIfMethodExists(String methodName) throws WarningException {
        for (int i = 0; i < globalScope.getMethodList().size(); i++)
            if (methodName.equals(globalScope.getMethodList().get(i).getMethodName()))
                throw new WarningException("1");
    }

    /*
    add a single method to global scope method list
     */
    private void addSingleMethod(GlobalScope globalScope, LinkedList<Variable> argumentsList, String
            methodName) throws WarningException {
        MethodScope newMethod = new MethodScope(globalScope, argumentsList, methodName);
        globalScope.setMethodList(newMethod);
        openScopes.add(newMethod);
    }

    /*
    checks if current global scope is empty or not
     */
    private boolean methodListIsEmpty() {
        return globalScope.getMethodList().isEmpty();
    }


    /*This methods process the whole file after all global variable and methods are documented and known*/
    private void checkFile(LinkedList<String> codeFileLines) throws WarningException {
        int lastReturnLineNumber = -1;
        for (int i = 0; i < codeFileLines.size(); i++) {
            String line = codeFileLines.get(i);

            //== comment line ==//
            if (line.contains("//")) {
                checkCommentLine(line);
            }

            //== ignore an empty line ==//
            else if (ProjectRegex.WHITE_SPACE_PATTERN.matcher(line).matches()) {
            }

            //== line end with ; ==//
            else if (ProjectRegex.SEMICOLON_PATTERN.matcher(line).find()) {

                // = method call = //
                Matcher m = ProjectRegex.METHOD_CALL.matcher(line);
                if (m.find()) {
                    checkMethodLine(m);


                }

                // = return statement = //
                else if (ProjectRegex.RETURN_STATEMENT.matcher(line).matches()) {
                    if (checkReturnStatement()) {
                        lastReturnLineNumber = i;
                    }
                }

                // =  declare on a new local variable line =//
                else if (ProjectRegex.VARIABLE_ROW.matcher(line).matches() && openScopes.peek() != globalScope) {
                    Matcher matcher = ProjectRegex.VARIABLE_ROW.matcher(line);
                    if (matcher.matches()) {
                        createVariable(matcher);

                    }
                }

                // =  declare on a new global variable line =//
                else if (ProjectRegex.VARIABLE_ROW.matcher(line).matches() && openScopes.peek() == globalScope) {
                    //continue
                }

                // = assignment operation =//
                else if (line.contains("=")) {
                    if (!checkAssignment(line, openScopes.peek()))
                        throw new WarningException("1");
                } else
                    throw new WarningException("1");
            }


            //== line end with } ==//
            else if (ProjectRegex.CURLY_BRACKETS_CLOSE_PATTERN.matcher(line).matches()) {
                handleCurlyBrackets(line, i, lastReturnLineNumber);
            }

            //== start of if \ while line ==//
            else if (ProjectRegex.IF_PATTERN.matcher(line).matches() || ProjectRegex.WHILE_PATTERN.matcher(line).matches()) {
                if (openScopes.peek() instanceof MethodScope || openScopes.peek() instanceof IfWhileScope) {
                    Matcher matcher;
                    String condition = "";
                    matcher = ProjectRegex.IF_PATTERN.matcher(line);
                    ifOrWhileLine(matcher, condition, line);

                }
            }

            //== start of method line ==//
            else if (ProjectRegex.METHOD_PATTERN.matcher(line).find()) {
                Matcher m = ProjectRegex.METHOD_PATTERN.matcher(line);
                if (m.find()) {
                    openNewMethodScope(m);
                }
            } else throw new WarningException("1"); //none of the
            // legal option.
        }
    }

    private void createVariable(Matcher matcher) throws WarningException {
        Variable newVar = null;
        if (matcher.group(3).contains(",")) {
            String[] variables = matcher.group(3).split(",");
            for (String string : variables)
                createMethodVariable(string, openScopes.peek(), matcher);
        }
        if (openScopes.peek() instanceof MethodScope) {
            String[] valAndName = matcher.group(3).split("=");
            if (valAndName.length == 2) {
                String name = valAndName[0].trim();
                String value = valAndName[1].trim();
                for (Variable variable : ((MethodScope) openScopes.peek()).getArgumentsList()) {
                    if (variable.getVarName().equals(value)) {
                        newVar = VariableFactory.createVarObject(matcher.group(1),
                                matcher.group(2), name, openScopes.peek());
                        newVar.setInitialized(true);
                    }
                }
            }
        }
        if (newVar == null) {
            newVar = VariableFactory.createVarObject(matcher.group(1), matcher.group
                    (2), matcher.group(3), openScopes.peek());
            if (newVar.isInitialized())
                newVar.setInitialized(true);
            if (openScopes.peek() instanceof MethodScope) {
                for (Variable variable : ((MethodScope) openScopes.peek()).getArgumentsList())
                    if (newVar.getVarName().equals(variable.getVarName()))
                        throw new WarningException("1");
            }
            openScopes.peek().addLocalVariables(newVar);
        }
    }


    /*
    create a variable under method scope
     */
    private void createMethodVariable(String string, Scope currentScope, Matcher matcher) throws
            WarningException {
        if (currentScope instanceof MethodScope) {
            String[] valAndName = string.split("=");
            if (valAndName.length == 2) {
                String name = valAndName[0].trim();
                String value = valAndName[1].trim();
                for (Variable variable : ((MethodScope) currentScope).getArgumentsList()) {
                    if (variable.getVarName().equals(value)) {
                        Variable newVar = VariableFactory.createVarObject(matcher.group(1),
                                matcher.group(2), name, currentScope);
                        newVar.setInitialized(true);

                    }
                }
            }
        }
    }

    /*
    add scope to stack
     */
    private void openNewMethodScope(Matcher m) {
        MethodScope method = null;
        for (MethodScope methodScope : globalScope.getMethodList()) {
            if (methodScope.getMethodName().equals(m.group(1)))
                method = methodScope;
        }
        openScopes.add(method);
    }

    /*
    handle if or while scope
     */
    private void ifOrWhileLine(Matcher matcher, String condition, String line) throws WarningException {
        if (matcher.matches()) {
            condition = matcher.group(1).trim();
        }
        matcher = ProjectRegex.WHILE_PATTERN.matcher(line);
        if (matcher.matches()) {
            condition = matcher.group(1).trim();
        }
        if (!IfWhileSyntax.isValidCondition(openScopes.peek(), condition))
            throw new WarningException("1");
        else {
            IfWhileScope ifWhile = new IfWhileScope(openScopes.peek());
            openScopes.add(ifWhile);
        }
    }

    private void handleCurlyBrackets(String line, int i, int lastReturnLineNumber) throws WarningException {
        if (openScopes.empty() || openScopes.peek() == globalScope) {
            throw new WarningException("1");
        } else {
            if (openScopes.peek() instanceof MethodScope) {
                if (i - 1 == lastReturnLineNumber)
                    openScopes.pop();
                else {
                    throw new WarningException("1");
                }
            } else {
                openScopes.pop();
            }
        }
    }

    /*
    returns true if there is no problem with return location
     */
    private boolean checkReturnStatement() throws WarningException {
        if (openScopes.peek() == globalScope) {
            throw new WarningException("1");
        }
        return true;
    }

    /*
    checks if there are mistakes with method call line.
     */
    private void checkMethodLine(Matcher m) throws WarningException {
        if (openScopes.peek() == globalScope) {
            throw new WarningException("1");
        } else if (!methodCallCheck(m.group(1), m.group(2), openScopes.peek())) {
            throw new WarningException("1");
        }
    }

    /*
    makes sure a comment line is legal
     */
    private void checkCommentLine(String line) throws WarningException {
        if (line.indexOf("//") != 0) {
            throw new WarningException("1");
        }
    }

    /**
     * the function checks if the assignment is valid.
     *
     * @param line  the line with the assigemnt
     * @param scope the scope that contains this line
     * @return true if it is a valid assignment, otherwise false.
     */
    private boolean checkAssignment(String line, Scope scope) throws WarningException {
        String[] tempString = line.split("=");
        if (tempString.length != 2)
            return false;
        String name = tempString[0].trim();
        String value = tempString[1].trim().replaceAll(";", "");
        Variable variable1 = Syntax.findVariable(name, scope);
        if (scope instanceof MethodScope) {
            if (assignVariable(scope, name))
                return true;
        }
        Variable variable2 = Syntax.findVariable(value, scope);
        if (variable1 == null || variable1.isFinal()) //A final variable or a variable that has not been
            // declared.
            return false;
        if (openScopes.peek() != globalScope) {
            return assignmentOutOfGlobalScope(variable1);

        }
        String typeVar1 = variable1.getVarType();
        String typeVar2 = VariablesSyntax.getValueType(value);
        if (VariablesSyntax.checkType(typeVar1, typeVar2)) {
            variable1.setInitialized(true);
            return true;
        } else {
            return checkVariableType(variable2, typeVar1, variable1);
        }
    }

    /*
    assign variable value
     */
    private boolean assignmentOutOfGlobalScope(Variable variable1) throws WarningException {
        String isFinal = "false";
        if (variable1.isFinal())
            isFinal = "final";
        Variable newVar = VariableFactory.createVarObject(isFinal, variable1.getVarType(),
                variable1.getVarName(), openScopes.peek());
        newVar.setInitialized(true);
        return true;
    }

    /*
    assign variable value
     */
    private boolean assignVariable(Scope scope, String name) {
        for (Variable variable : ((MethodScope) scope).getArgumentsList()) {
            if (variable.getVarName().equals(name))
                return true;
        }
        return false;
    }

    /*
    check variable type
     */
    private boolean checkVariableType(Variable variable2, String typeVar1, Variable variable1) {
        if (variable2 != null) {
            String type = variable2.getVarType();
            if (VariablesSyntax.checkType(typeVar1, type)) {
                variable1.setInitialized(true);
                return true;
            } else
                return false;
        } else
            return false;
    }


    /**
     * the function checks if calling a method is valid.
     *
     * @param name       method name to call
     * @param parameters the parameters to call with
     * @param scope      the scope of this calling
     * @return true if valid, otherwise return false.
     */
    private boolean methodCallCheck(String name, String parameters, Scope scope) {
        String[] paramList = parameters.split(",", -1);
        LinkedList<String> parametersType = new LinkedList<>();
        MethodScope method = null;
        for (MethodScope methodScope : globalScope.getMethodList()) {
            if (methodScope.getMethodName().equals(name)) {
                method = methodScope;
                if (parameters.equals("")) //If method doesn't require arguments, code doesn't have to
                    // check for matching types.
                    break;
                for (String parameter : paramList) {
                    Variable variable;
                    if (!parameter.equals("")) {
                        variable = Syntax.findVariable(parameter, scope);
                        if (variable == null) {
                            parametersType.add(VariablesSyntax.getValueType(parameter));
                        } else {
                            parametersType.add(variable.getVarType());
                        }
                        if (variable != null && !variable.isInitialized())
                            return false;
                    } else
                        return false; //after the last param has ','.
                }
            }
        }
        if (method == null) //no such a function
            return false;
        if (method.getArgumentsList().size() == parametersType.size()) {
            for (int i = 0; i < parametersType.size(); i++) {
                if (!VariablesSyntax.checkType(method.getArgumentsList().get(i).getVarType(), parametersType.get(i)))
                    return false; //The types of variables do not match.
            }
            return true;
        }
        return false; //The quantity of parameters is invalid.
    }
}