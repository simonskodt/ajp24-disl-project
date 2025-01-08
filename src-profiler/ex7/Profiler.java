package ex7;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Comparator;


public class Profiler {

    private static ConcurrentHashMap<Tuple<String, Integer>, Integer> allocations = new ConcurrentHashMap<>();

    public static class Tuple<X, Y> {
        final X x;
        final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x.hashCode() ^ y.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple)) return false;
            Tuple tuple = (Tuple) o;
            return this.x.equals(tuple.x) && this.y.equals(tuple.y);
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
        public void run() {
            allocations.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(new Comparator<Tuple<String, Integer>>() {
                    @Override
                    public int compare(Tuple<String, Integer> o1, Tuple<String, Integer> o2) {
                        int cmp = o1.x.compareTo(o2.x);
                        if (cmp != 0) {
                            return cmp;
                        }
                        return o1.y.compareTo(o2.y);
                    }
                }))
                .forEach(entry -> {
                    Tuple<String, Integer> key = entry.getKey();
                    System.err.format("%s, %d: %d allocations\n", key.x, key.y, entry.getValue());
                });
        }
    }

    public static void registerArrayAllocation(String componentType, int length) {
        Tuple<String, Integer> key = new Tuple<>(componentType, length);
        allocations.putIfAbsent(key, 0);
        allocations.computeIfPresent(key, (k, v) -> v + 1);
    }

    public static void registerThreadEnd() {
        // No additional actions needed for thread end in this context
    }
}