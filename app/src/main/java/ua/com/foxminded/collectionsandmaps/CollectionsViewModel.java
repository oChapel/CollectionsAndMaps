package ua.com.foxminded.collectionsandmaps;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionsViewModel extends ViewModel {

    private final int type;

    private final Handler handler = new Handler(Looper.myLooper());
    private ExecutorService es;

    private final MutableLiveData<List<Items>> itemsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> toastStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> sizeErrorStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> poolErrorStatus = new MutableLiveData<>();

    public CollectionsViewModel(int type) {
        this.type = type;
    }

    public void init() {
        toastStatus.setValue(null);
        if (itemsList.getValue() != null) {
            return;
        }
        itemsList.setValue(generateCollectionItems(false));
    }

    public void calculateTime(String collectionSize, String poolSize) {
        int threads;
        try {
            threads = Integer.parseInt(poolSize);
            poolErrorStatus.setValue(null);
        } catch (NullPointerException | NumberFormatException e) {
            poolErrorStatus.setValue(R.string.invalidInput);
            threads = -1;
        }
        if (threads == 0){
            poolErrorStatus.setValue(R.string.invalidNumber);
        }
        int size;
        try {
            size = Integer.parseInt(collectionSize);
            sizeErrorStatus.setValue(null);
        } catch (NumberFormatException e) {
            sizeErrorStatus.setValue(R.string.invalidInput);
            size = -1;
        }
        if (threads > 0 && size > 0) {

            if (es == null) {
                es = Executors.newFixedThreadPool(threads);
                toastStatus.setValue(R.string.startingCalc);

                final List<Items> list = generateCollectionItems(true);
                itemsList.setValue(list);
                final AtomicInteger counter = new AtomicInteger(list.size());
                final int benchmarkSize = size;
                for (int i = 0; i < list.size(); i++) {
                    final int position = i;
                    es.submit(() -> {
                        final Items item;
                        if (type == 0) {
                            item = CollectionsOperations.measureTime(
                                    list.get(position), benchmarkSize
                            );
                        } else if (type == 1) {
                            item = MapsOperations.measureTime(
                                    list.get(position), benchmarkSize
                            );
                        } else throw new RuntimeException("Unsupported type: " + type);
                        counter.getAndDecrement();
                        handler.post(() -> updateList(list, position, item));
                        if (counter.get() == 0) {
                            handler.post(() -> stopPool(false));
                        }
                    });
                }

            } else {
                stopPool(true);
                itemsList.setValue(generateCollectionItems(false));
            }
        }
    }

    private void stopPool(boolean forced) {
        if (forced) {
            es.shutdownNow();
        } else {
            es.shutdown();
        }
        es = null;
        toastStatus.setValue(forced ? R.string.stopCalc : R.string.endingCalc);
    }

    public List<Items> generateCollectionItems(boolean visibilityFlag) {
        final List<Items> items = new ArrayList<>();
        //final String naMS = getContext().getResources().getString(R.string.NAms);
        int[] idArrOperations;
        int[] idArrType;
        if (type == 0) {
            idArrOperations = new int[]{R.string.addToStart, R.string.addToMiddle,
                    R.string.addToEnd, R.string.searchByValue, R.string.remFromStart,
                    R.string.remFromMiddle, R.string.remFromEnd};
            idArrType = new int[]{R.string.arrayList, R.string.linkedList, R.string.copyOnWriterList};
        } else if (type == 1) {
            idArrOperations = new int[]{R.string.addToMap, R.string.searchByKey,
                    R.string.remFromMap};
            idArrType = new int[]{R.string.treeMap, R.string.hashMap};
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }
        for (int operation : idArrOperations) {
            for (int listType : idArrType) {
                items.add(new Items(operation, listType, "N/A"/*naMS*/, visibilityFlag));
            }
        }
        return items;
    }

    public void updateList(List<Items> list, int position, Items item) {
        list.set(position, item);
        itemsList.setValue(new ArrayList<>(list));
    }

    public int getSpanCount() {
        if (type == 0) {
            return 3;
        } else if (type == 1) {
            return 2;
        } else throw new RuntimeException("Unsupported type: " + type);
    }

    public LiveData<List<Items>> getItemsList() {
        return itemsList;
    }

    public LiveData<Integer> getToastStatus() {
        return toastStatus;
    }

    public LiveData<Integer> getSizeErrorStatus() {
        return sizeErrorStatus;
    }

    public LiveData<Integer> getPoolErrorStatus() {
        return poolErrorStatus;
    }
}