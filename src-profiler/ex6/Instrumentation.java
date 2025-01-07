package ex6;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class Instrumentation {

    static final String MAIN_THREAD = "ex6.MainThread.*";

    @After(marker = BytecodeMarker.class, args="iadd", scope=MAIN_THREAD)
    static void findStringArrLength(DynamicContext dynamicContext, MethodStaticContext methodStaticContext) {
        if (!methodStaticContext.thisMethodName().equals("init")) {
            return;
        }

        System.err.println(dynamicContext.getStackValue(0, int.class));
        // Profiler.arrLength = dynamicContext.getStackValue(0, int.class);
    }
}