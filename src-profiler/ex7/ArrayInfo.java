package ex7;

import java.util.ArrayList;
import java.util.List;

public class ArrayInfo  {
    private String component; 
    private List<Integer> dimensions;

    public ArrayInfo() {}

    public ArrayInfo(String component, int singleDimension) {
        this.component = component.replaceAll("[\\[\\]:;]+", "").trim();
        dimensions = new ArrayList<>();
        dimensions.add(singleDimension);
    }

    public ArrayInfo(String component, List<Integer> dimensions) {
        // is multi array so remove all the brackets [[L in the beginning and the substring(1) is for the L
        this.component = component.replaceAll("[\\[\\]:;]+", "").trim().substring(1);
        this.dimensions = dimensions;
    }

    public String getComponent() {
        return this.component;
    }

    public List<Integer> getDims() {
        return this.dimensions;
    }

    @Override
    public int hashCode() {
        int result = component.hashCode();

        result = 31 * result + (dimensions != null ? dimensions.hashCode() : 0);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayInfo)) return false;
        ArrayInfo arrayInfo = (ArrayInfo) o;

        if (!this.component.equals(arrayInfo.component)) return false;

        if (this.dimensions.size() != arrayInfo.dimensions.size()) return false;

        for (int i = 0; i < this.dimensions.size(); i++) {
            if (this.dimensions.get(i) != arrayInfo.dimensions.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return this.component + ", " + this.dimensions.toString().replaceAll("[\\[\\]]", "").trim() + ":";
    }
}