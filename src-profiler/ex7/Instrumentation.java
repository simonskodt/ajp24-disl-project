package ex7;

import java.util.List;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.ThreadLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;

public class Instrumentation {
    
    private static final String SCOPE = "ex7.MainThread.*";

    @ThreadLocal
    private static int arrLength;

    @ThreadLocal
    private static String componentType;

    @Before(marker=BytecodeMarker.class , args="newarray, anewarray", scope=SCOPE)
    static void findArrayLength(DynamicContext dynamicContext) {
        arrLength = dynamicContext.getStackValue(0, int.class);
    }

    @After(marker=BytecodeMarker.class, args="newarray, anewarray", scope=SCOPE)
    static void findArrayType(DynamicContext dynamicContext) {
        Object array = dynamicContext.getStackValue(0, Object.class);
        if (array != null && array.getClass().isArray()) {
            componentType = array.getClass().getComponentType().getName();
            Profiler.registerArrayAllocation(componentType, arrLength);
        }
    }

    @After(marker=BytecodeMarker.class , args="multianewarray", scope=SCOPE)
    static void findMultiArrayLength(DynamicContext dynamicContext) {
        Object array = dynamicContext.getStackValue(0, Object.class);
        if (array != null && array.getClass().isArray()) {
            List<Integer> dims = Profiler.getArrayDimension(array);

            // String res = "";
            // for (int i = 0; i < dims.size(); i++) {
            //     res += dims.get(i) + " ";
            // }
            // System.out.println(res);
        }
    }

    
}