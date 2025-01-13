package ex3;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.ThreadLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;

public class Instrumentation {

    @ThreadLocal
    private int classCounter; //!maybe count all the acquired locks, summing all the acquisition for each thread IDK

    @Before(marker = BytecodeMarker.class, args = "monitorenter, monitorexit", scope = "ex3.MainThread.*") 
    static void onLockAquisition(DynamicContext dynamicContext) {
        // Obtain the 9 MainThreads
        Thread thread = Thread.currentThread();
        long hashCode = thread.hashCode();
        String className = thread.getClass().getName();
        Profiler.updateMonitor(hashCode, className);

        // Obtain the DoubleCounter and FloatCounter
        Object stackTop = dynamicContext.getStackValue(0, Object.class); // 0 is index for args+vars (this)
        long hashCode2 = System.identityHashCode(stackTop);
        String className2 = stackTop.getClass().getName();
        Profiler.updateMonitor(hashCode2, className2);
    }
}
