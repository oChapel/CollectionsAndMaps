package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollectionsFragment extends Fragment implements View.OnClickListener {

    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();
    private TextInputLayout sizeOperations;
    private TextInputEditText editSizeOperations;
    private Button startButton;
    private Handler handler;

    private final int CALCULATIONS_ENDED = 100;
    private final int CALCULATIONS_STOPPED = 101;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionsAdapter.setItems(generateCollectionItems());
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

        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        startButton.setTag(1);

        sizeOperations = view.findViewById(R.id.textInputLayout);
        editSizeOperations = view.findViewById(R.id.textInputEditText);

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == CALCULATIONS_ENDED) {
                    startButton.setText(getContext().getResources().getString(R.string.start));
                    Toast.makeText(getActivity().getApplicationContext(),
                            getResources().getText(R.string.endingCalc), Toast.LENGTH_SHORT).show();
                } else if (msg.what == CALCULATIONS_STOPPED) {
                    startButton.setText(getContext().getResources().getString(R.string.start));
                    startButton.setEnabled(true);
                    Toast.makeText(getActivity().getApplicationContext(),
                            getResources().getText(R.string.stopCalc), Toast.LENGTH_SHORT).show();
                } else {
                    collectionsAdapter.notifyItemChanged(msg.what);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {

        final int status = (Integer) v.getTag();
        ExecutorService es = Executors.newFixedThreadPool(3);

        if (status == 1) {

            try {

                int size = Integer.parseInt(editSizeOperations.getText().toString());
                sizeOperations.setError(null);
                startButton.setText(getContext().getResources().getString(R.string.stop));
                CollectionsOperations.setStopped(false);
                v.setTag(0);

                Toast.makeText(getActivity().getApplicationContext(),
                        getResources().getText(R.string.startingCalc), Toast.LENGTH_SHORT).show();

                CountDownLatch latch = new CountDownLatch(3);
                List<Items> list = generateCollectionItems();
                String arrList = getContext().getResources().getString(R.string.arrayList);
                String linkList = getContext().getResources().getString(R.string.linkedList);
                String copyOnWrList = getContext().getResources().getString(R.string.copyOnWriterList);
                String ms = getContext().getResources().getString(R.string.ms);

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        updateList(list, 0, new Items(arrList, "", true));
                        int time = (int) CollectionsOperations.calcAddingToStart(size, new ArrayList<>());
                        if (CollectionsOperations.isStopped()) {
                            Thread.currentThread().interrupt();
                        } else {
                            updateList(list, 0, new Items(arrList, time + " " + ms, false));
                        }
                        latch.countDown();
                    }
                });

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        updateList(list, 1, new Items(linkList, "", true));
                        int time = (int) CollectionsOperations.calcAddingToStart(size, new LinkedList<>());
                        if (CollectionsOperations.isStopped()) {
                            Thread.currentThread().interrupt();
                        } else {
                            updateList(list, 1, new Items(linkList, time + " " + ms, false));
                        }
                        latch.countDown();
                    }
                });

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        updateList(list, 2, new Items(copyOnWrList, "", true));
                        int time = (int) CollectionsOperations.calcAddingToStart(size, new CopyOnWriteArrayList<>());
                        if (CollectionsOperations.isStopped()) {
                            Thread.currentThread().interrupt();
                        } else {
                            updateList(list, 2, new Items(copyOnWrList, time + " " + ms, false));
                        }
                        latch.countDown();
                    }
                });

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (CollectionsOperations.isStopped()) {
                            handler.sendEmptyMessage(CALCULATIONS_STOPPED);
                        } else {
                            handler.sendEmptyMessage(CALCULATIONS_ENDED);
                            v.setTag(1);
                        }
                    }
                });

                es.shutdown();

            } catch (NumberFormatException e) {
                sizeOperations.setError(getString(R.string.invalidInput));
            }

        } else {

            es.shutdownNow();
            CollectionsOperations.setStopped(true);
            v.setEnabled(false);

            collectionsAdapter.setItems(generateCollectionItems());
            collectionsAdapter.notifyDataSetChanged();

            v.setTag(1);

        }

    }

    public List<Items> generateCollectionItems() {
        List<Items> items = new ArrayList<>();
        int[] idArrCollectionsOperations = {R.string.addToStart, R.string.addToMiddle, R.string.addToEnd,
                R.string.search, R.string.remFromStart, R.string.remFromMiddle, R.string.remFromEnd};
        int[] idArrCollectionsList = {R.string.arrayList, R.string.linkedList, R.string.copyOnWriterList};
        String naMS = getContext().getResources().getString(R.string.NAms);
        for (int i = 0; i < idArrCollectionsOperations.length; i++) {
            for (int j : idArrCollectionsList) {
                items.add(new Items(getContext().getResources().getString(j), naMS, false));
            }
        }
        return items;
    }

    public void updateList(List<Items> list, int position, Items item) {
        list.set(position, item);
        collectionsAdapter.setItems(list);
        handler.sendEmptyMessage(position);
    }
}