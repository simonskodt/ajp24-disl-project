package ex7;

import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Field;

public class Profiler {

    private static ConcurrentHashMap<Tuple<String, Integer>, Integer> allocations = new ConcurrentHashMap<>();

    public static class Tuple<X, Y> {
        final X x;
        final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
	
        public void run() {
            for (var entry : allocations.entrySet()) {
                Tuple<String, Integer> key = entry.getKey();
                
                System.err.format("%s, %d: %d allocations\n", 
                    key.x, key.y, entry.getValue()
                );
            }
        }
    }

    public static void registerThreadEnd() {
        try {
            Field comp = Thread.class.getDeclaredField("componentType");
            String component = (String) comp.get(Thread.currentThread());

            Field l = Thread.class.getDeclaredField("arrLength");
            Integer arrLength = l.getInt(Thread.currentThread());

            Tuple<String, Integer> key = new Tuple<>(component, arrLength);
            allocations.putIfAbsent(key, 1);
            allocations.computeIfPresent(key, (k, v) -> v + 1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}