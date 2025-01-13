package ex6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler {

    public static int arrLength;
    public static List<Tuple> strTests = new ArrayList<>();

    public static Map<String, Integer> strTests2 = new HashMap<>();

    public static class Tuple {
        final String uuid; 
        final int result; 
        public Tuple(String uuid, int result) {
            this.uuid = uuid;
            this.result = result;
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Profiler::printAtShutdown));
    }

    private static void printAtShutdown() {
        System.err.println("Length of the String array: " + arrLength);
        
        for (var tuple : strTests) {
            System.err.format("String: %s - Result: %s",
                tuple.uuid,
                tuple.result == 1 ? "Valid\n" : "Not valid\n"
            );
        }
    }
}