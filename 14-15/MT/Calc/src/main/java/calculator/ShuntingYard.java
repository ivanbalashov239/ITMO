package calculator;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anton on 12.04.15.
 */
public class ShuntingYard {
    final static private String DIVIDER = ",";
    final static private List<String> OPERATORS = new ArrayList<String>(){{
        add("-");
        add("+");
        add("/");
        add("*");
        add("^");
    }};
    final static private List<String> FUNCTIONS = new ArrayList<String>(){{
        add("sin");
        add("cos");
        add("exp");
        add("ln");
        add("lg");
        add("tg");
        add("ctg");
        add("arctg");
        add("arcctg");
        add("log");
    }};

    //Split input to tokens
    static List<String> split(String input) {
        Matcher m = Pattern.compile("(\\d+\\.?\\d*|" +
                "\\(|\\)|\\" +
                OPERATORS.stream().reduce((x,xs)->xs+"|\\"+x).get() + "|" +
                FUNCTIONS.stream().reduce((x,xs)->xs+"|"+x).get() + "|\\" +
                DIVIDER +
                ")").matcher(input);
        List<String> inputSplit = new LinkedList<>();
        while (m.find()) {
            inputSplit.add(m.group());
        }
        return inputSplit;
    }

    private static boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    private static boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    private static boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    private static boolean isFunc(String token) {
        return FUNCTIONS.contains(token);
    }

    private static boolean isDivider(String token) {
        return token.equals(DIVIDER);
    }

    private static int getOpPriority(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "/":
            case "*":
                return 2;
            case "^":
                return 4;
        }
        return 3;
    }

    static Queue<String> convert(String input) {
        Queue<String> tokens = new ArrayDeque<>(split(input));
        Queue<String> output = new ArrayDeque<>();
        Stack<String> stack = new Stack<>();

        while (!tokens.isEmpty()) {
            String token = tokens.poll();

            // If number put on output queue
            if (NumberUtils.isNumber(token)) {
                output.add(token);
            }
            // If operator
            else if (isOperator(token)) {
                // Put operators from stack in output while we can
                while (!stack.isEmpty() &&
                        isOperator(stack.lastElement()) &&
                        getOpPriority(token) <= getOpPriority(stack.lastElement())) {
                    output.add(stack.pop());
                }
                // Put token to stack
                stack.push(token);
            }
            // If token is open bracket then pot it in stack
            else if (isOpenBracket(token)) {
                stack.push(token);
            }
            // If close bracket
            else if (isCloseBracket(token)) {
                // Put everything belongs to '(' in output
                while(!isOpenBracket(stack.lastElement())) {
                    output.add(stack.pop());
                }
                // Drop '(' from stack
                stack.pop();
                // If we'v got func on the top of a stack put it it output
                if (isFunc(stack.lastElement())) {
                    output.add(stack.pop());
                }
            }
            // If token is function put it in stack
            else if (isFunc(token)) {
                stack.push(token);
            }
            // If token is divider then push in out all besides (
            else if (isDivider(token)) {
                while (!isOpenBracket(stack.lastElement())) {
                    output.add(stack.pop());
                }
            }
        }

        // Put tail of a stack in output
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }
}
