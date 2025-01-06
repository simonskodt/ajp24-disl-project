package ex3;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.ClassStaticContext;

public class Instrumentation {

    //should be able to get class name and opCode, and update the map
    @Before(marker = BytecodeMarker.class, args = "monitorexit", scope = "ex3.MainThread.*") 
    static void onLockAquisition(ClassStaticContext classStaticContext, DynamicContext dynamicContext) {
        Object monitor = dynamicContext.getStackValue(0, Object.class);
        long hashCode = System.identityHashCode(monitor);
        // long hashCode = classStaticContext.hashCode();
        String className = classStaticContext.getName();
        Profiler.updateMonitor(hashCode, className);
    }

    // @Before(marker = BytecodeMarker.class, args = "new", scope = "ex3.MainThread.*")
    // static void beforeObjectCreation(ClassStaticContext classStaticContext, DynamicContext dynamicContext) {
    //     long hashCode = classStaticContext.hashCode();
    //     String className = classStaticContext.getName();
    //     Profiler.initilizeMonitor(hashCode, className);
    // }
}
