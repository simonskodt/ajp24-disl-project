package ex7;

import java.util.concurrent.ConcurrentHashMap;

public class Profiler {

    // public static ConcurrentHashMap<Tuple, Integer> allocations = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Integer> allocations = new ConcurrentHashMap<>();

    public static class Tuple {
        public final String x;
        public final int y;

        public Tuple(String x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple)) {
                return false;
            }

            Tuple t = (Tuple) o;
            return this.x.equals(t.x) && this.y == t.y;
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
	
        public void run() {
            for (var entry : allocations.entrySet()) {
                // Tuple key = entry.getKey();
                String key = entry.getKey();
                
                // System.err.format("%s, %d: %d allocations\n", 
                //     key.x, key.y, entry.getValue()
                // );
                System.err.format("%s: %d allocations\n",
                key, entry.getValue());
            }
        }
    }
}