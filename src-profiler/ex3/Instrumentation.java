package ex3;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;

public class Instrumentation {

    @Before(marker = BytecodeMarker.class, args = "monitorenter", scope = "ex3.MainThread.*") 
    static void onLockAquisition(DynamicContext dynamicContext) {
        // Obtain the 9 MainThreads
        Thread thread = Thread.currentThread();
        long hashCode = thread.hashCode();
        String className = thread.getClass().getCanonicalName();
        Profiler.updateMonitor(hashCode, className);

        // Obtain the DoubleCounter and FloatCounter
        Object stackTop = dynamicContext.getStackValue(0, Object.class); // understand this better
        long hashCode2 = System.identityHashCode(stackTop);
        String className2 = stackTop.getClass().getCanonicalName();
        Profiler.updateMonitor(hashCode2, className2);
    }
}