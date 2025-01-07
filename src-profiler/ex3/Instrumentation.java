package ex3;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BasicBlockMarker;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.ClassStaticContext;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import ch.usi.dag.disl.util.cfg.BasicBlock;

public class Instrumentation {

    @Before(marker = BytecodeMarker.class, args = "monitorenter", scope = "ex3.MainThread.*") 
    static void onLockAquisition(ClassStaticContext classStaticContext, DynamicContext dynamicContext) {
        // Obtain the 9 MainThreads
        Thread thread = Thread.currentThread();
        long hashCode = thread.hashCode();
        String className = thread.getClass().getCanonicalName();
        Profiler.updateMonitor(hashCode, className);

        // Obtain the DoubleCounter and FloatCounter
        // Object stackTop = dynamicContext.getStackValue(0, Object.class);
        // long hashCode2 = System.identityHashCode(stackTop);
        // String className2 = stackTop.getClass().getCanonicalName();
        // Profiler.updateMonitor(hashCode2, className2);
    }

    @Before(marker = BasicBlockMarker.class, scope = "ex3.MainThread.*") 
    static void onMethodLockAquisition(MethodStaticContext methodStaticContext, ClassStaticContext classStaticContext) {
        if (methodStaticContext.isMethodSynchronized()) {
            long hashCode = classStaticContext.getClass().hashCode();
            String className = classStaticContext.getClass().getCanonicalName();
            System.err.println(hashCode + " " + className);
            Profiler.updateMonitor(hashCode, className);
            // long hashCode = methodStaticContext.getClass().hashCode();
            // String className = methodStaticContext.getClass().getCanonicalName();
            // Profiler.updateMonitor(hashCode, className);
        }
    }
}