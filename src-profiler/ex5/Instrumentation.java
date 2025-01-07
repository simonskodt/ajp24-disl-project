package ex5;

import java.util.Arrays;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;

public class Instrumentation {

    static final String MAIN_THREAD = "ex5.MainThread.*";

    @Before(marker = BytecodeMarker.class, args="invokevirtual", scope=MAIN_THREAD)
    static void onFooInvokation(DynamicContext dynamicContext) {
        // Track all inputs passed to foo()
        // The 0th element is the top of the stack
        int parameterValue = dynamicContext.getStackValue(0, int.class);
        Profiler.parameterValues.add(parameterValue);
    }

    @After(marker = BytecodeMarker.class, args="invokevirtual", scope=MAIN_THREAD)
    static void onFooReturn(DynamicContext dynamicContext) {
        // Track all return values from calling foo()
        int[] returnValueArr = (int[]) dynamicContext.getStackValue(0, Object.class);
        Profiler.returnValues.add(returnValueArr);
    }
}