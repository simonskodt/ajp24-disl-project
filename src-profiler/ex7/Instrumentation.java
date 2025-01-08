package ex7;

import java.util.concurrent.ConcurrentMap;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.SyntheticLocal;
import ch.usi.dag.disl.annotation.ThreadLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BasicBlockMarker;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.BasicBlockStaticContext;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class Instrumentation {
    
    // Update to include the whole runtime
    private static final String SCOPE = "ex7.MainThread.*";

    @ThreadLocal
    private static int[] arrLength;

    @ThreadLocal
    private static String[] componentType;

    // @SyntheticLocal
    // ConcurrentMap<
    
    @Before(marker=BytecodeMarker.class , args="newarray, anewarray", scope=SCOPE)
    static void findArrayLength(DynamicContext dynamicContext, BasicBlockStaticContext basicBlockStaticContext) {
        int stackTop = dynamicContext.getStackValue(0, int.class);
        // arrLength
        basicBlockStaticContext.
        // System.err.println("Length: " + arrLength);

    }

    @After(marker=BytecodeMarker.class, args="newarray, anewarray", scope=SCOPE)
    static void findArrayType(DynamicContext dynamicContext) {
        // if (dynamicContext.getStackValue(0, int.class)) {
        //     int componentType = 
        // }
        componentType = (String) dc.getStackValue(0, Object.class).getClass().getName();
        System.err.println("Array type: " + componentType);
    }

    @After(marker = BasicBlockMarker.class, scope="ex7.MainThread.run")
    static void onThreadExit(DynamicContext dynamicContext) {
        if (dynamicContext.getThis() instanceof Thread) {  
            Profiler.registerThreadEnd();
        }
    } 
}