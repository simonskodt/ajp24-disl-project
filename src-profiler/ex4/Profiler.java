package ex4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Profiler {

    public static final ConcurrentMap<Class<? extends Throwable>, ExceptionInfo> exceptionData = new ConcurrentHashMap<>();

    static {
	    Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
	
        public void run() {
            for (Map.Entry<Class<? extends Throwable>, ExceptionInfo> entry : exceptionData.entrySet()) {
                String exceptionClassName = entry.getKey().getName();
                ExceptionInfo ei = entry.getValue();

                System.out.printf("Exception class: %s - occurrences: %d - avg calling context depth: %4.2f \n",
                    exceptionClassName, ei.occurences, ei.getAvgCallingContextDepth());
            }
        }
    }

    private static class ExceptionInfo {

        long occurences;
        long totalCallingContextDepth;

        public ExceptionInfo(long occurences, long totalCallingContextDepth) {
            this.occurences = occurences;
            this.totalCallingContextDepth = totalCallingContextDepth;
        }

        public void update(long stackDepth) {
            occurences++;
            totalCallingContextDepth += stackDepth;
        }

        public double getAvgCallingContextDepth() {
            return (double) totalCallingContextDepth / occurences;
        }
    }

    public static void updateNewExceptionThrown(Class<? extends Throwable> clazz, long stackDepth) {
        exceptionData
            .computeIfAbsent(clazz, t -> new ExceptionInfo(0, 0))
            .update(stackDepth);
    }
}