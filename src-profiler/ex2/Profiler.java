package ex2;

import java.util.concurrent.ConcurrentHashMap;

public class Profiler {

    static class ThreadOperations {
        // These will be local to each thread that is run.
        long N_STATIC, N_SPECIAL, N_VIRTUAL, N_DYNAMIC;

        public ThreadOperations() {
            N_STATIC = 0;
            N_SPECIAL = 0;
            N_VIRTUAL = 0;
            N_DYNAMIC = 0;
        }
    }

    private static ConcurrentHashMap<String, ThreadOperations> threadMap = new ConcurrentHashMap<>();


    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
	
        public void run() {
            for (String threadName : threadMap.keySet()) {
                ThreadOperations to = threadMap.get(threadName);
                
                System.err.format("Thread: %s - #static: %d - #special: %d " +
                "- #virtual: %d - #dynamic: %d\n",
                threadName,
                to.N_STATIC, to.N_SPECIAL, to.N_VIRTUAL, to.N_DYNAMIC
                );
            }
        }
    }

    public static void incrementCounter(String threadName, String opiCode) throws IllegalArgumentException {
        ThreadOperations op = threadMap.computeIfAbsent(threadName, t -> new ThreadOperations());
        switch (opiCode) {
            case "static":
                op.N_STATIC++;
                break;
            case "special":
                op.N_SPECIAL++;
                break;
            case "virtual":
                op.N_VIRTUAL++;
                break;
            case "dynamic": 
                op.N_DYNAMIC++;
                break;
            default:
                throw new IllegalArgumentException("Argument cannot be handled.");
        }
    }

    // public static void putInThreadMap(String threadName, String opiCode) {
    //     switch (threadName) {
    //         case "static":
    //             threadMap.putIfAbsent(threadName, )
    //             break;
    //         case ""
    //         default:
    //             break;
    //     }
    //     threadMap.putIfAbsent(threadName, vall)
        
    //     threadMap.computeIfAbsent(threadName, t -> new ThreadOperations());
    // }

    // public static void registerThreadEnd(String _threadName) {
	
    //     try {
    //         threadName = _threadName;
    //         Field nInvokeStaticField = Thread.class.getDeclaredField("nInvokeStatic");
    //         N_STATIC = nInvokeStaticField.getLong(Thread.currentThread());
            
    //         Field nInvokeSpecialField = Thread.class.getDeclaredField("nInvokeSpecial");
    //         N_SPECIAL = nInvokeSpecialField.getLong(Thread.currentThread());

    //         Field nInvokeVirtualField = Thread.class.getDeclaredField("nInvokeVirtual");
    //         N_VIRTUAL = nInvokeVirtualField.getLong(Thread.currentThread());

    //         Field nInvokeDynamicField = Thread.class.getDeclaredField("nInvokeDynamic");
    //         N_DYNAMIC = nInvokeDynamicField.getLong(Thread.currentThread());            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
