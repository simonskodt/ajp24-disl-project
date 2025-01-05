package ex2;

import java.util.concurrent.atomic.LongAdder;

public class Profiler {

    public static LongAdder nInvokeStatic  = new LongAdder();
    public static LongAdder nInvokeSpecial = new LongAdder();
    public static LongAdder nInvokeVirtual = new LongAdder();
    public static LongAdder nInvokeDynamic = new LongAdder();

    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
	
        public void run() {
            System.err.format("Thread: %s - #static: %d - #special: %d" +
                "- #virtual: %d - #dynamic: %d",
                Thread.currentThread().getName(),
                nInvokeStatic.sum(),
                nInvokeSpecial.sum(),
                nInvokeVirtual.sum(),
                nInvokeDynamic.sum()
            );
        }
    }
}
