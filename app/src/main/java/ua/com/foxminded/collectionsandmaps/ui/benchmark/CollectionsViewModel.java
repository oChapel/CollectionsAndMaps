package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ua.com.foxminded.collectionsandmaps.R;
import ua.com.foxminded.collectionsandmaps.models.benchmark.Benchmark;
import ua.com.foxminded.collectionsandmaps.models.benchmark.Items;

public class CollectionsViewModel extends ViewModel {

    private final Benchmark benchmark;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<Items>> itemsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> toastStatus = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> sizeErrorStatus = new MutableLiveData<>();

    public CollectionsViewModel(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public void init() {
        if (itemsList.getValue() != null) {
            return;
        }
        itemsList.setValue(benchmark.generateCollectionItems(false));
    }

    public void calculateTime(String collectionSize) {
        int size;
        try {
            size = Integer.parseInt(collectionSize);
            sizeErrorStatus.setValue(null);
        } catch (NumberFormatException e) {
            sizeErrorStatus.setValue(R.string.invalidInput);
            size = -1;
        }
        if (size > 0) {

            if (R.string.startingCalc == toastStatus.getValue()) {
                toastStatus.setValue(R.string.stopCalc);
                compositeDisposable.clear();
                itemsList.setValue(benchmark.generateCollectionItems(false));
            } else {
                final List<Items> list = benchmark.generateCollectionItems(true);
                final int benchmarkSize = size;
                final AtomicReference<Items> calcItem = new AtomicReference<>();

                compositeDisposable.add(Observable.just(list)
                        .doOnSubscribe(items -> {
                            toastStatus.setValue(R.string.startingCalc);
                            itemsList.setValue(list);
                        })
                        .flatMap(items -> Observable.fromIterable(items)
                                .subscribeOn(Schedulers.computation()))
                        .doOnNext(item -> calcItem.set(benchmark.measureTime(item, benchmarkSize)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> toastStatus.setValue(R.string.endingCalc))
                        .subscribe(item -> updateList(list, list.indexOf(item), calcItem.get())));
            }
        }
    }

    private void updateList(List<Items> list, int position, Items item) {
        list.set(position, item);
        itemsList.setValue(new ArrayList<>(list));
    }

    public int getSpanCount() {
        return benchmark.getSpanCount();
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

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}