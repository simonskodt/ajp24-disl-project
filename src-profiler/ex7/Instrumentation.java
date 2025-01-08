package ex7;

import java.lang.reflect.Array;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BytecodeMarker;

public class Instrumentation {
    
    private static final String SCOPE = "ex7.MainThread.*";

    @After(marker=BytecodeMarker.class, args="newarray, anewarray", scope=SCOPE)
    static void findArrayType(DynamicContext dynamicContext) {
        Object obj = dynamicContext.getStackValue(0, Object.class);

        if (obj.getClass().isArray()) {
            String component = obj.getClass().getName();
            int length = Array.getLength(obj);


            Profiler.Tuple key = new Profiler.Tuple(component, length);
            Profiler.allocations.putIfAbsent(key, 0);
            Profiler.allocations.put(key, Profiler.allocations.get(key)+ 1);
            // Profiler.allocations.computeIfAbsent(key, (Profiler.Tuple t) -> t.y + 1);
        }
    }
}