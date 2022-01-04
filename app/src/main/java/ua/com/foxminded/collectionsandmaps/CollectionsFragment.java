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

    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();
    private final Handler handler = new Handler(Looper.myLooper());

    private ExecutorService es;
    private TextInputLayout sizeOperations;
    private TextInputLayout sizeThreads;
    private TextInputEditText editSizeOperations;
    private TextInputEditText editSizeThreads;
    private int status = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionsAdapter.setItems(generateCollectionItems(false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(collectionsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        Button startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        sizeOperations = view.findViewById(R.id.textInputLayoutOperations);
        sizeThreads = view.findViewById(R.id.textInputLayoutThreads);
        editSizeOperations = view.findViewById(R.id.textInputEditTextOperations);
        editSizeThreads = view.findViewById(R.id.textInputEditTextThreads);

    }

    @Override
    public void onClick(View v) {

        Button button = (Button) v;

        if (status == 1) {

            try {

                int threads = Integer.parseInt(editSizeThreads.getText().toString());
                sizeThreads.setError(null);
                es = Executors.newFixedThreadPool(threads);

                if (!es.isShutdown()) {

                    try {

                        int size = Integer.parseInt(editSizeOperations.getText().toString());
                        sizeOperations.setError(null);

                        button.setText(getContext().getResources().getString(R.string.stop));
                        status = 0;

                        Toast.makeText(getActivity().getApplicationContext(),
                                getResources().getText(R.string.startingCalc), Toast.LENGTH_SHORT).show();

                        List<Items> list = generateCollectionItems(true);
                        collectionsAdapter.setItems(list);
                        AtomicInteger counter = new AtomicInteger(list.size());

                        for (int i = 0; i < list.size(); i++) {
                            int position = i;
                            es.submit(() -> {
                                Items item = CollectionsOperations.measureTime(position, size);
                                counter.getAndDecrement();
                                handler.post(() -> updateList(list, position, item));
                                if (counter.get() == 0) {
                                    handler.post(() -> {
                                        button.setText(getContext().getResources().getString(R.string.start));
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                getResources().getText(R.string.endingCalc), Toast.LENGTH_SHORT).show();
                                    });
                                    status = 1;
                                }
                            });
                        }

                        es.shutdown();

                    } catch (NumberFormatException e) {
                        sizeOperations.setError(getString(R.string.invalidInput));
                    }
                }

            } catch (NumberFormatException e) {
                sizeThreads.setError(getString(R.string.invalidInput));
            } catch (IllegalArgumentException e) {
                sizeThreads.setError(getString(R.string.invalidNumber));
            }


        } else {

            es.shutdownNow();
            button.setText(getContext().getResources().getString(R.string.start));
            collectionsAdapter.setItems(generateCollectionItems(false));
            status = 1;
            Toast.makeText(getActivity().getApplicationContext(),
                    getResources().getText(R.string.stopCalc), Toast.LENGTH_SHORT).show();

        }

    }

    public List<Items> generateCollectionItems(boolean visibilityFlag) {
        List<Items> items = new ArrayList<>();
        int[] idArrCollectionsOperations = {R.string.addToStart, R.string.addToMiddle, R.string.addToEnd,
                R.string.search, R.string.remFromStart, R.string.remFromMiddle, R.string.remFromEnd};
        int[] idArrCollectionsList = {R.string.arrayList, R.string.linkedList, R.string.copyOnWriterList};
        String naMS = getContext().getResources().getString(R.string.NAms);
        for (int i = 0; i < idArrCollectionsOperations.length; i++) {
            for (int j : idArrCollectionsList) {
                items.add(new Items(j, naMS, visibilityFlag));
            }
        }
        return items;
    }

    public void updateList(List<Items> list, int position, Items item) {
        list.set(position, item);
        collectionsAdapter.setItems(list);
    }
}