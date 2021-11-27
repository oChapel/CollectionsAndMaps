package ua.com.foxminded.collectionsandmaps;

import android.widget.ProgressBar;

import java.util.List;

public class Items {

    public final String name;
    public final String calcResults;
    public final ProgressBar progressBar;

    public Items(String name, String calcResults, ProgressBar progressBar) {
        this.name = name;
        this.calcResults = calcResults;
        this.progressBar = progressBar;
    }

    public static List<Items> generateCollectionItems(List<Items> items, ProgressBar progressBar) {

        String[] arrayTypes = {"in ArrayList","in LinkedList","in CopyOnWriteList"};

        for (int i = 0; i < 7; i++) {
            for (String arrayType : arrayTypes) {
                items.add(new Items(arrayType, "N/A ms", progressBar));
            }
        }
        return items;
    }

}