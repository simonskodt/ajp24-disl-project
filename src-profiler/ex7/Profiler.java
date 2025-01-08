package ex7;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

public class Profiler {

    public static ConcurrentHashMap<Tuple, Integer> allocations = new ConcurrentHashMap<>();

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
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Tuple)) return false;

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
                Tuple key = entry.getKey();
                
                System.err.format("%s, %d: %d allocations\n", 
                    key.x, key.y, entry.getValue()
                );
            }
        }
    }
}