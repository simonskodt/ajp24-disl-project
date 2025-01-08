package ex5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * The method foo() computes the fibonacci sequence.
 * It takes a single int as input that decides on the amount of
 * number in the fibonacci sequence that is wanted. The return
 * is of type int[].
 */
public class Profiler {
    // The parameterValues and returnValues are assumed to be of
    // the same length
    public static List<Integer> parameterValues = new ArrayList<>();
    public static List<int[]> returnValues = new ArrayList<>(); 

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Profiler::printAtShutdown));
    }

    private static void printAtShutdown() {
        for (int i = 0; i < parameterValues.size(); i++) {
            System.err.format("Input: %d - Output: %s\n", 
                parameterValues.get(i),
                Arrays.toString(returnValues.get(i))
            );
        }
    } 
}
