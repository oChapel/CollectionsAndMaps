package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectionsFragment extends Fragment implements View.OnClickListener {

    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();
    private final String TAG1 = "Exception occurred";

    TextInputLayout sizeOperations;
    TextInputEditText editSizeOperations;

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

        Button startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        sizeOperations = view.findViewById(R.id.textInputLayout);
        editSizeOperations = view.findViewById(R.id.textInputEditText);

        Message msg = new Message();
    }

    @Override
    public void onClick(View v) {

        try {

            int size = Integer.parseInt(editSizeOperations.getText().toString());
            sizeOperations.setError(null);
            Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.startingCalc), Toast.LENGTH_SHORT).show();

            Thread t1 = new Thread(new Runnable() {
                final List<Integer> arrayList = new ArrayList<>();

                @Override
                public void run() {
                    int time = (int) CollectionsOperations.calcAddingToStart(size, arrayList);
                }
            });
            t1.start();

        } catch (NumberFormatException e) {
            sizeOperations.setError(getString(R.string.invalidInput));
            Log.i(TAG1, "Error: " + e);
        }

    }

    public List<Items> generateCollectionItems() {
        List<Items> items = new ArrayList<>();
        String arrayList = getContext().getResources().getString(R.string.arrayList);
        String linkedList = getContext().getResources().getString(R.string.linkedList);
        String copyOnWriterList = getContext().getResources().getString(R.string.copyOnWriterList);
        String naMS = getContext().getResources().getString(R.string.NAms);
        for (int i = 0; i < 7; i++) {
            items.add(new Items(arrayList, naMS, false));
            items.add(new Items(linkedList, naMS, false));
            items.add(new Items(copyOnWriterList, naMS, false));
        }
        return items;
    }

}