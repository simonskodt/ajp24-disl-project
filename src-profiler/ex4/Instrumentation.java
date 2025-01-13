package ex4;

import ch.usi.dag.disl.annotation.AfterThrowing;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;

public class Instrumentation {

    @AfterThrowing(marker = BytecodeMarker.class, args="athrow")
    static void throwException(DynamicContext dynamicContext) {

        Throwable athrow = dynamicContext.getException();
        if (athrow.equals(null)) return;

        long stackDepth = athrow.getStackTrace().length;
        Profiler.updateNewExceptionThrown(athrow.getClass(), stackDepth);
    }
}