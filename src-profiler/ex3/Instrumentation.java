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
        Object thisObject = dynamicContext.getStackValue(0, Object.class); // 0 is index for args+vars (this)
        long hashCode2 = System.identityHashCode(thisObject);
        String className2 = thisObject.getClass().getCanonicalName();
        Profiler.updateMonitor(hashCode2, className2);
    }
}
