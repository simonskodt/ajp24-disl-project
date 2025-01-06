package ex3;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public class Profiler {

    static class Monitor {
        String className;
        LongAdder nLockAcquisitions = new LongAdder();

        public Monitor(String className) {
            this.className = className;
        }
    }

    static ConcurrentMap<Long, Monitor> monitorMap = new ConcurrentHashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Profiler::printAtShutdown));
    }

    private static void printAtShutdown() {
        for (long hashCode : monitorMap.keySet()) {
            Monitor m = monitorMap.get(hashCode);
            
            if (m.nLockAcquisitions.sum() == 0) {
                continue;
            }

            System.err.format("%d - %s - #Locks: %d\n",
                hashCode,
                m.className,
                m.nLockAcquisitions.sum()
            );
        }
    }

    public static void initilizeMonitor(long hashCode, String className) {
        monitorMap.computeIfAbsent(hashCode, m -> new Monitor(className));
    }

    public static void updateMonitor(long hashCode, String className) {
        monitorMap.computeIfAbsent(hashCode, m -> new Monitor(className));
        monitorMap.computeIfPresent(hashCode, (n1, n2) -> {
            n2.nLockAcquisitions.increment();
            return n2;
        });
    }
}
