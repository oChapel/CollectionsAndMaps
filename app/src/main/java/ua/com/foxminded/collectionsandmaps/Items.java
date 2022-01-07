package ua.com.foxminded.collectionsandmaps;

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

}