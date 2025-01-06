package ex3;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.ClassStaticContext;

public class Instrumentation {

    @Before(marker = BytecodeMarker.class, args = "monitorenter", scope = "ex3.MainThread.*") 
    static void onLockAquisition(ClassStaticContext classStaticContext, DynamicContext dynamicContext) {
        Thread thread = Thread.currentThread();
        long hashCode = thread.hashCode();
        String className = thread.getClass().getCanonicalName();

        Profiler.initilizeMonitor(hashCode, className);
        Profiler.updateMonitor(hashCode);
    }
}