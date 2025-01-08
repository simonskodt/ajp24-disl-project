package ex6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler {

    public static int arrLength;
    public static List<Tuple<String, Integer>> strTests = new ArrayList<>();

    public static Map<String, Integer> strTests2 = new HashMap<>();

    public static class Tuple<X, Y> {
        final X x; // string representation
        final Y y; // results as a boolean value
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Profiler::printAtShutdown));
    }

    private static void printAtShutdown() {
        System.err.println("Length of the String array: " + arrLength);
        
        for (var tuple : strTests) {
            System.err.format("String: %s - Result: %s",
                tuple.x,
                tuple.y == 1 ? "Valid\n" : "Not valid\n"
            );
        }
    }
}