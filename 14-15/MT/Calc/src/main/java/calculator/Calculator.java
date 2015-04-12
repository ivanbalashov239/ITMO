package calculator;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

/**
 * Created by anton on 12.04.15.
 */
public class Calculator {
    private static final List<String> BIN = new ArrayList<String>(){{
        add("+");
        add("-");
        add("^");
        add("/");
        add("*");
        add("log");
    }};
    private static final List<String> UN = new ArrayList<String>(){{
        add("sin");
        add("cos");
        add("exp");
        add("ln");
        add("lg");
        add("tg");
        add("ctg");
        add("arctg");
        add("arcctg");
    }};

    private static boolean isUn(String op) {
        return UN.contains(op);
    }

    private static boolean isBin(String op) {
        return BIN.contains(op);
    }

    static private Double compute(String op, double r, double l) {
        switch (op) {
            case "+":
                return l + r;
            case "-":
                return l - r;
            case "*":
                return l * r;
            case "/":
                return l / r;
            case "^":
                return Math.pow(l,r);
            case "log":
                return Math.log(r) / Math.log(l);
        }
        return Double.NaN;
    }

    static private Double compute(String op, double arg) {
        switch (op) {
            case "sin":
                return Math.sin(arg);
            case "cos":
                return Math.cos(arg);
            case "exp":
                return Math.exp(arg);
            case "ln":
                return Math.log(arg);
            case "lg":
                return Math.log10(arg);
            case "tg":
                return Math.tan(arg);
            case "ctg":
                return 1 / Math.tan(arg);
            case "arctg":
                return Math.atan(arg);
            case "arcctg":
                return Math.PI/2 - Math.atan(arg);
        }
        return Double.NaN;
    }

    static Double calculate(Queue<String> tokens) {
        Stack<Double> stack = new Stack<>();
        while (!tokens.isEmpty()) {
            String token = tokens.poll();
            if (NumberUtils.isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (isBin(token)) {
                    stack.push(compute(token, stack.pop(), stack.pop()));
                } else if (isUn(token)) {
                    stack.push(compute(token, stack.pop()));
                }
            }
        }
        return stack.lastElement();
    }

}
