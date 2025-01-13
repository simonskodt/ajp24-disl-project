package ex1;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.staticcontext.FieldAccessStaticContext;

public class Instrumentation {

    @Before(marker = BytecodeMarker.class, args="getfield")
    static void onInstanceFieldRead(FieldAccessStaticContext fieldContext) {
        Profiler.nInstanceFieldReads.increment();
    }

    @Before(marker = BytecodeMarker.class, args="putfield")
    static void onInstanceFieldWrite(FieldAccessStaticContext fieldContext) {
        Profiler.nInstanceFieldWrites.increment();
    }

    @Before(marker = BytecodeMarker.class, args="getstatic, putstatic")
    static void onStaticFieldAccess(FieldAccessStaticContext fieldContext) {
        Profiler.uniqueStaticFieldsAccessed.add(fieldContext.getName());
    }
}