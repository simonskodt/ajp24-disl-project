package ex2;

import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ex1.Profiler;

public class Instrumentation {

    @AfterReturning(marker = BytecodeMarker.class, 
        args="invokestatic, invokespecial, invokevirtual",
        scope="ex2.MainThread")
    static void onThreadExit(DynamicContext dynamicContext) {
        if (dynamicContext.getThis() instanceof Thread) {  
            // String bytecode = dynamicContext.
	        // Profiler.atThreadEnd();
        }
    }
}
