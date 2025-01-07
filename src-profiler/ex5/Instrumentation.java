package ex5;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class Instrumentation {

    @Before(marker = BytecodeMarker.class, args="invokevirtual")
    static void onFooInvokation(DynamicContext dynamicContext) {
        // Integer parameterValue = dynamicContext.getStackValue(10, Integer.class);
        // Integer[] returnValues = dynamicContext.getStackValue(11, )
    }

}
