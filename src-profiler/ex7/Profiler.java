package ex7;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.lang.reflect.Array;


public class Profiler {

    private static ConcurrentHashMap<ArrayInfo, Integer> allocations = new ConcurrentHashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    static class ShutdownThread extends Thread {
        public void run() {
            allocations.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(new Comparator<ArrayInfo>(){
                    @Override
                    public int compare(ArrayInfo a1, ArrayInfo a2) {
                        // (1) Compare by component name
                        int componentComparison = a1.getComponent().compareTo(a2.getComponent());
                        if (componentComparison != 0) {
                            return componentComparison;
                        }

                        // (2) Compare by number of dimensions (ascending)
                        int dimensionsComparison = Integer.compare(a1.getDims().size(), a2.getDims().size());
                        if (dimensionsComparison != 0) {
                            return dimensionsComparison;
                        }

                        // (3) Compare by length of each dimension (ascending)
                        for (int i = 0; i < Math.min(a1.getDims().size(), a2.getDims().size()); i++) {
                            int dimensionComparison = Integer.compare(a1.getDims().get(i), a2.getDims().get(i));
                            if (dimensionComparison != 0) {
                                return dimensionComparison;
                            }
                        }

                        // All comparisons are equal
                        return 0;
                    }
                }))
                .forEach((Map.Entry<ArrayInfo, Integer> entry) -> {
                    System.err.format("%s %d allocations\n", entry.getKey().toString(), entry.getValue());
                });
        }
    }

    public static void registerArrayAllocation(String componentType, int length) {
        ArrayInfo key = new ArrayInfo(componentType, length);
        allocations.putIfAbsent(key, 0);
        allocations.computeIfPresent(key, (k, v) -> v + 1);
    }

    public static void registerArrayAllocation(String componentType, List<Integer> dims) {
        ArrayInfo key = new ArrayInfo(componentType, dims);
        allocations.putIfAbsent(key, 0);
        allocations.computeIfPresent(key, (k, v) -> v + 1);
    }

    public static List<Integer> getArrayDimension(Object arr) {
        List<Integer> dims = new ArrayList<>();
        return getArrayDimensionRec(arr, dims, 0);
    }

    public static List<Integer> getArrayDimensionRec(Object arr, List<Integer> dims, int idx) {
        if (arr == null || !arr.getClass().isArray() || idx >= 4) {
            return dims;
        }

        dims.add(Array.getLength(arr));

        return getArrayDimensionRec(Array.get(arr, 0), dims, idx+1);
    }
}