package calculator;

import java.util.Arrays;
import java.util.Queue;

/**
 * Created by anton on 12.04.15.
 */
public class Main {
    public static void main(String[] args) {
        Queue<String> reversePolishNotation = ShuntingYard.convert(
                args.length > 0 ?
                        Arrays.asList(args).stream().reduce((x, xs) -> x + xs).get() :
                        args[0].replaceAll("\\s", "") );
        System.out.println(reversePolishNotation);
        System.out.println(Calculator.calculate(reversePolishNotation));
    }
}
