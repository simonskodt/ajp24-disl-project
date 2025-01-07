package ex6;

import java.util.ArrayList;
import java.util.List;

public class Profiler {

    static int arrLength;
    static List<Tuple<String, Boolean>> strTests = new ArrayList<>();

    static class Tuple<X, Y> {
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
                tuple.y ? "Valid" : "Not valid"
            );
        }
    }
}