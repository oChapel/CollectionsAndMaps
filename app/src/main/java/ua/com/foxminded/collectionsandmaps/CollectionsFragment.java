package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_TYPE = "arg_type";

    public static CollectionsFragment newInstance(int type) {
        final Bundle b = new Bundle();
        b.putInt(ARG_TYPE, type);
        final CollectionsFragment f = new CollectionsFragment();
        f.setArguments(b);
        return f;
    }

    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();
    private final Handler handler = new Handler(Looper.myLooper());

    private ExecutorService es;
    private Button startButton;
    private TextInputLayout sizeOperations;
    private TextInputLayout sizeThreads;
    private TextInputEditText editSizeOperations;
    private TextInputEditText editSizeThreads;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionsAdapter.setItems(generateCollectionItems(false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_benchmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(collectionsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount()));

        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        sizeOperations = view.findViewById(R.id.textInputLayoutOperations);
        sizeThreads = view.findViewById(R.id.textInputLayoutThreads);
        editSizeOperations = view.findViewById(R.id.textInputEditTextOperations);
        editSizeThreads = view.findViewById(R.id.textInputEditTextThreads);
    }

    private int getSpanCount() {
        final int type = getArguments().getInt(ARG_TYPE);
        if (type == 0) {
            return 3;
        } else if (type == 1) {
            return 2;
        } else throw new RuntimeException("Unsupported type: " + type);
    }

    @Override
    public void onClick(View v) {
        calculateTime(
                editSizeOperations.getText().toString().trim(),
                editSizeThreads.getText().toString().trim()
        );
    }

    public void calculateTime(String collectionSize, String poolSize) {
        if (es == null) {
            int threads;
            try {
                threads = Integer.parseInt(poolSize);
                sizeThreads.setError(null);
                es = Executors.newFixedThreadPool(threads);
            } catch (NullPointerException | NumberFormatException e) {
                sizeThreads.setError(getString(R.string.invalidInput));
                threads = -1;
            } catch (IllegalArgumentException e) {
                sizeThreads.setError(getString(R.string.invalidNumber));
                threads = -1;
            }
            int size;
            try {
                size = Integer.parseInt(collectionSize);
                sizeOperations.setError(null);
            } catch (NumberFormatException e) {
                sizeOperations.setError(getString(R.string.invalidInput));
                size = -1;
            }
            if (threads > 0 && size > 0) {
                startButton.setText(R.string.stop);

                Toast.makeText(getContext(), R.string.startingCalc, Toast.LENGTH_SHORT).show();

                final List<Items> list = generateCollectionItems(true);
                collectionsAdapter.setItems(list);
                final AtomicInteger counter = new AtomicInteger(list.size());
                final int type = getArguments().getInt(ARG_TYPE);
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
            }
        } else {
            stopPool(true);
            collectionsAdapter.setItems(generateCollectionItems(false));
        }
    }

    private void stopPool(boolean forced) {
        if (forced) {
            es.shutdownNow();
        } else {
            es.shutdown();
        }

        es = null;
        startButton.setText(R.string.start);

        Toast.makeText(
                getContext(), forced ? R.string.stopCalc : R.string.endingCalc, Toast.LENGTH_SHORT
        ).show();
    }

    public List<Items> generateCollectionItems(boolean visibilityFlag) {
        final int type = getArguments().getInt(ARG_TYPE);
        final List<Items> items = new ArrayList<>();
        final String naMS = getContext().getResources().getString(R.string.NAms);
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
                items.add(new Items(operation, listType, naMS, visibilityFlag));
            }
        }
        return items;
    }

    public void updateList(List<Items> list, int position, Items item) {
        list.set(position, item);
        collectionsAdapter.setItems(new ArrayList<>(list));
    }
}