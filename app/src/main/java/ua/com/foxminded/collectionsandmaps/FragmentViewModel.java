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

public class FragmentViewModel extends ViewModel {

    private final int type;
    private final int CALC_STARTED = 0;
    private final int CALC_ENDED = 1;
    private final int CALC_STOPPED = 2;

    private final Handler handler = new Handler(Looper.myLooper());
    private ExecutorService es;

    private final MutableLiveData<List<Items>> mItemsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> status = new MutableLiveData<>();

    public FragmentViewModel(int type) {
        this.type = type;
    }

    public void init() {
        status.setValue(null);
        if (mItemsList.getValue() != null) {
            return;
        }
        mItemsList.setValue(generateCollectionItems(false));
    }

    public LiveData<List<Items>> getItemsList() {
        return mItemsList;
    }

    public LiveData<Integer> getStatus() {
        return status;
    }

    public void calculateTime(int collectionSize, int poolSize) {
        if (es == null) {

            es = Executors.newFixedThreadPool(poolSize);
            status.setValue(CALC_STARTED);

            final List<Items> list = generateCollectionItems(true);
            mItemsList.setValue(list);
            final AtomicInteger counter = new AtomicInteger(list.size());
            final int benchmarkSize = collectionSize;
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
            mItemsList.setValue(generateCollectionItems(false));
        }
    }

    private void stopPool(boolean forced) {
        if (forced) {
            es.shutdownNow();
        } else {
            es.shutdown();
        }
        es = null;
        status.setValue(forced ? CALC_STOPPED : CALC_ENDED);
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
        mItemsList.setValue(new ArrayList<>(list));
    }
}