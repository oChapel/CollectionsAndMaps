package ua.com.foxminded.collectionsandmaps;

import java.util.Objects;

public class Items {

    public final int operation;
    public final int name;
    public final String calcResults;
    public final boolean progressBarFlag;

    public Items(int operation, int name, String calcResults, boolean progressBarFlag) {
        this.operation = operation;
        this.name = name;
        this.calcResults = calcResults;
        this.progressBarFlag = progressBarFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return operation == items.operation &&
                name == items.name &&
                progressBarFlag == items.progressBarFlag &&
                calcResults.equals(items.calcResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, name, calcResults, progressBarFlag);
    }
}