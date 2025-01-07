package ex2;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.marker.BytecodeMarker;


public class Instrumentation {

    // @ThreadLocal
    // private static long nInvokeStatic;

    // @ThreadLocal
    // private static long nInvokeSpecial;
    
    // @ThreadLocal
    // private static long nInvokeVirtual;

    // @ThreadLocal
    // private static long nInvokeDynamic;

    @Before(marker = BytecodeMarker.class, args="invokestatic", scope="ex2.MainThread.*")
    static void onInvokeStatic() {
        String threadName = Thread.currentThread().getName();
        // Profiler.putInThreadMap(threadName, 0);
        // nInvokeStatic++;

        Profiler.incrementCounter(threadName, "static");
    }

    @Before(marker = BytecodeMarker.class, args="invokespecial", scope="ex2.MainThread.*")
    static void onInvokeSpecial() {
        // nInvokeSpecial++;
        String threadName = Thread.currentThread().getName();
        Profiler.incrementCounter(threadName, "special");
    }

    @Before(marker = BytecodeMarker.class, args="invokevirtual", scope="ex2.MainThread.*")
    static void onInvokeVirtual() {
        // nInvokeVirtual++;
        String threadName = Thread.currentThread().getName();
        Profiler.incrementCounter(threadName, "virtual");
    }

    @Before(marker = BytecodeMarker.class, args="invokedynamic", scope="ex2.MainThread.*")
    static void onInvokeDinamic() {
        // nInvokeDynamic++;
        String threadName = Thread.currentThread().getName();
        Profiler.incrementCounter(threadName, "dynamic");
    }

    // @After(marker = BasicBlockMarker.class, scope="void run()") //guard=IsThreadGuard.class)
    // static void onThreadExit(DynamicContext dynamicContext) {
    //     if (dynamicContext.getThis() instanceof Thread) {
    //         String threadName = Thread.currentThread().getName();
	//         Profiler.registerThreadEnd(threadName);
    //     }
    // }
}