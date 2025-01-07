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
        System.err.format("Length of the String array: %d\n", 
            "String: to instance fields: %d\n" +
            "Unique static fields accessed: %d\n",
            nInstanceFieldReads.sum(),
            nInstanceFieldWrites.sum(),
            uniqueStaticFieldsAccessed.size());
    }
}
