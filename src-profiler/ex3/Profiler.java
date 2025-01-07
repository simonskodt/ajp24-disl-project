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
        for (var entry : monitorMap.entrySet()) {
            Long hashCode = entry.getKey();
            Monitor v = entry.getValue();

            if (v.nLockAcquisitions.sum() == 0) {
                continue;
            }

            System.err.format("%d - %s - #Locks: %d\n",
                hashCode,
                v.className,
                v.nLockAcquisitions.sum()
            );
        }
    }

    public static void updateMonitor(long hashCode, String className) {
        monitorMap.putIfAbsent(hashCode, new Monitor(className));

        monitorMap.computeIfPresent(hashCode, (cName, lockCount) -> {
            lockCount.nLockAcquisitions.increment();
            return lockCount;
        });
    }
}
