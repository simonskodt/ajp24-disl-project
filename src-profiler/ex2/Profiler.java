package ex2;

import java.lang.reflect.Field;

public class Profiler {

    public static void registerThreadEnd(String threadName) {
	
        try {
            long N_STATIC  = nByteCodeInstructions("nInvokeStatic");
            long N_SPECIAL = nByteCodeInstructions("nInvokeSpecial");
            long N_VIRTUAL = nByteCodeInstructions("nInvokeVirtual");
            long N_DYNAMIC = nByteCodeInstructions("nInvokeDynamic");

            System.err.format("Thread: %s - #static: %d - #special: %d " +
                "- #virtual: %d - #dynamic: %d\n",
                threadName,
                N_STATIC, N_SPECIAL, N_VIRTUAL, N_DYNAMIC
            );            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long nByteCodeInstructions(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field nInvokeStaticField = Thread.class.getDeclaredField(fieldName);
        return nInvokeStaticField.getLong(Thread.currentThread());
    }
}