package ex6;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class Instrumentation {

    static final String MAIN_THREAD = "ex6.MainThread.*";

    @Before(marker = BytecodeMarker.class, args="anewarray", scope=MAIN_THREAD)
    static void findStringArrLength(DynamicContext dynamicContext, MethodStaticContext methodStaticContext) {
        if (!methodStaticContext.thisMethodName().equals("init")) {
            return;
        }

        Profiler.arrLength = dynamicContext.getStackValue(0, int.class);
    }

    @Before(marker = BytecodeMarker.class, args="ireturn", scope=MAIN_THREAD)
    static void findBoolean(DynamicContext dynamicContext, MethodStaticContext methodStaticContext) {
        if (!methodStaticContext.thisMethodName().equals("foo")) {
            return;
        }

        String uuid = dynamicContext.getMethodArgumentValue(0, String.class);

        int result = (int) dynamicContext.getStackValue(0, int.class);
        Profiler.strTests.add(new Profiler.Tuple<String,Integer>(uuid, result));
    }
}