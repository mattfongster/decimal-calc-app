package server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A calculator that evaluates arithmetic expressions. The calculator can handle 
 * addition (+), subtraction (-), multiplication (*), division (/), and modulus (%).
 */
public final class Calculator {
    
    public static final Calculator INSTANCE = new Calculator();
    
    private static final Collection<String> OPERATORS;
    private static final Map<String, Integer> PRECEDENCE;
    static {
        OPERATORS = new ArrayList<>();
        OPERATORS.add("+");
        OPERATORS.add("-");
        OPERATORS.add("*");
        OPERATORS.add("/");
        OPERATORS.add("mod");
        
        PRECEDENCE = new HashMap<>();
        PRECEDENCE.put("+", 1);
        PRECEDENCE.put("-", 1);
        PRECEDENCE.put("*", 2);
        PRECEDENCE.put("/", 2);
        PRECEDENCE.put("mod", 2);
    }
    
    /**
     * Do not directly instantiate.
     */
    private Calculator() {
        
    }
    
    /**
     * Check if a string is numeric.
     * 
     * @param str the string to check.
     * @return true if the string is numeric, and false otherwise.
     */
    private boolean isNumeric(String str) {
        return str.matches("[-]?[0-9]+[.]?[0-9]*");
    }
    
    /**
     * Check if a string is an arithmetic operator.
     * 
     * @param str the string to check.
     * @return true if the string is an arithmetic operator, and false otherwise.
     */
    private boolean isOperator(String str) {
        return OPERATORS.contains(str);
    }
        
    /**
     * Format a given double value into a rounded string.
     * 
     * @param value the double value to format.
     * @return the formatted double value.
     */
    private String format(double value) {
        if (value - (int) value > 0) 
            return value + "";
        else 
            return (int) value + "";
    }
    
    /**
     * Scan a given string expression into a list of tokens.
     * 
     * @param expr the string expression to scan.
     * @return the string expression converted into tokens.
     */
    private List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        for (String token : expr.split("[ ]+")) {
            while (token.startsWith("(")) {
                tokens.add("(");
                token = token.substring(1);
            }
            if (token.endsWith(")")) {
                int count = 0;
                while (token.endsWith(")")) {
                    count++;
                    token = token.substring(0, token.length() - 1);
                }
                tokens.add(token);
                while (count > 0) {
                    tokens.add(")");
                    count--;
                }
            }
            else {
                tokens.add(token);
            }
        }
        return tokens;
    }
    
    /**
     * Parse a given string expression into tokens in Reverse polish notation.
     * 
     * @param expr the string expression to parse.
     * @return the parsed string expression.
     */
    private List<String> parse(String expr) {
        List<String> output = new ArrayList<>();        // output queue
        Stack<String> operators = new Stack<>();        // operator stack
        
        // Tokenize the expression and go through each token
        for (String token : this.tokenize(expr)) {
            // If the token is a number, simply add it to the output queue
            if (this.isNumeric(token)) {
                output.add(token);
            }
            else if (this.isOperator(token)) {
                while (!operators.isEmpty()) {
                    String operator = operators.peek();
                    if (!operator.equals("(") && PRECEDENCE.get(token) < PRECEDENCE.get(operator)) {
                        output.add(operators.pop());
                        continue;
                    }
                    break;
                }
                operators.push(token);
            }
            // If the token is a left parenthesis, simply push it to the operator stack
            else if (token.equals("(")) {
                operators.push(token);
            }
            // If the token is a right parenthesis, pop from the operator stack into
            // the output queue until a left parenthesis is found
            else if (token.equals(")")) {
                while (true) {
                    String operator = operators.pop();
                    if (operator.equals("(")) {
                        break;
                    }
                    output.add(operator);
                }
            }
            // A bad character
            else {
                throw new IllegalArgumentException("Bad input expression");
            }
        }
        
        // Drop remaining operators to output
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
        return output;
    }
    
    /**
     * Arithmetically evaluate a given string expression.
     * 
     * @param expr the string expression to evaluate.
     * @return the output of the evaluation.
     */
    public String evaluate(String expr) {
        Stack<Double> output = new Stack<>();
        for (String token : this.parse(expr)) {
            if (this.isNumeric(token)) {
                output.add(Double.parseDouble(token));
            }
            else if (this.isOperator(token)) {
                double right = output.pop();
                double left = output.pop();
                switch (token) {
                    case "+":
                        output.push(left + right);
                        break;
                    case "-":
                        output.push(left - right);
                        break;
                    case "*":
                        output.push(left * right);
                        break;
                    case "/":
                        output.push(left / right);
                        break;
                    case "mod":
                        output.push(left % right);
                        break;
                }
            }
        }
        return this.format(output.pop());
    }
}
