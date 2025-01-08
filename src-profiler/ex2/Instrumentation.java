package ex2;

import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.ThreadLocal;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.marker.BytecodeMarker;


public class Instrumentation {

    @ThreadLocal
    private static long nInvokeStatic;

    @ThreadLocal
    private static long nInvokeSpecial;
    
    @ThreadLocal
    private static long nInvokeVirtual;

    @ThreadLocal
    private static long nInvokeDynamic;

    private static final String SCOPE = "ex2.MainThread.*";

    @Before(marker = BytecodeMarker.class, args="invokestatic", scope=SCOPE)
    static void onInvokeStatic() {
        nInvokeStatic++;
    }

    @Before(marker = BytecodeMarker.class, args="invokespecial", scope=SCOPE)
    static void onInvokeSpecial() {
        nInvokeSpecial++;
    }

    @Before(marker = BytecodeMarker.class, args="invokevirtual", scope=SCOPE)
    static void onInvokeVirtual() {
        nInvokeVirtual++;
    }

    @Before(marker = BytecodeMarker.class, args="invokedynamic", scope=SCOPE)
    static void onInvokeDinamic() {
        nInvokeDynamic++;
    }

    @After(marker = BodyMarker.class, scope="ex2.MainThread.run")
    static void onThreadExit(DynamicContext dynamicContext) {
        if (dynamicContext.getThis() instanceof Thread) {
            final String threadName = Thread.currentThread().getName();
	        Profiler.registerThreadEnd(threadName);
        }
    }
}