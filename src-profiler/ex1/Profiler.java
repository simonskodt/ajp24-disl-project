package ex1;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class Profiler {

    public static LongAdder nInstanceFieldReads = new LongAdder();
    public static LongAdder nInstanceFieldWrites = new LongAdder();
    public static Set<String> uniqueStaticFieldsAccessed = ConcurrentHashMap.newKeySet();

    static class ShutdownThread extends Thread {
	
        public void run() {
            System.err.format("Reads from instance fields: %d\n" +
                "Writes to instance fields: %d\n" +
                "Unique static fields accessed: %d\n",
                nInstanceFieldReads.sum(),
                nInstanceFieldWrites.sum(),
                uniqueStaticFieldsAccessed.size());
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }
}
