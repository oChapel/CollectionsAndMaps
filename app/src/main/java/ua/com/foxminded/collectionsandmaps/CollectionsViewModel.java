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

    private final CalcTypeDependence calcTypeDependence;
    private final Handler handler = new Handler(Looper.myLooper());
    private ExecutorService es;

    private final MutableLiveData<List<Items>> itemsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> toastStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> sizeErrorStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> poolErrorStatus = new MutableLiveData<>();

    public CollectionsViewModel(CalcTypeDependence calcTypeDependence) {
        this.calcTypeDependence = calcTypeDependence;
    }

    public void init() {
        toastStatus.setValue(null);
        if (itemsList.getValue() != null) {
            return;
        }
        itemsList.setValue(calcTypeDependence.generateCollectionItems(false));
    }

    public void calculateTime(String collectionSize, String poolSize) {
        int threads;
        try {
            threads = Integer.parseInt(poolSize);
            poolErrorStatus.setValue(null);
        } catch (NumberFormatException e) {
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

                final List<Items> list = calcTypeDependence.generateCollectionItems(true);
                itemsList.setValue(list);
                final AtomicInteger counter = new AtomicInteger(list.size());
                final int benchmarkSize = size;
                for (int i = 0; i < list.size(); i++) {
                    final int position = i;
                    es.submit(() -> {
                        final Items item = calcTypeDependence.measureTime(list.get(position), benchmarkSize);
                        counter.getAndDecrement();
                        handler.post(() -> updateList(list, position, item));
                        if (counter.get() == 0) {
                            handler.post(() -> stopPool(false));
                        }
                    });
                }

            } else {
                stopPool(true);
                itemsList.setValue(calcTypeDependence.generateCollectionItems(false));
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

    public void updateList(List<Items> list, int position, Items item) {
        list.set(position, item);
        itemsList.setValue(new ArrayList<>(list));
    }

    public int getSpanCount() {
        return calcTypeDependence.getSpanCount();
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