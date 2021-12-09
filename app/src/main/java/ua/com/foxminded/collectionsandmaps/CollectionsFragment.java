package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CollectionsFragment extends Fragment implements View.OnClickListener {

    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();

    EditText sizeOperations;

    public CollectionsFragment() {
        collectionsAdapter.setItems(generateCollectionItems());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        sizeOperations = view.findViewById(R.id.operationsInput);

        Message msg = new Message();
    }

    @Override
    public void onClick(View v) {

        if (sizeOperations.getText().toString().isEmpty() | sizeOperations.getText().toString().equals(null)) {
            sizeOperations.setError(getString(R.string.invalidInput));
        } else {

            Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.startingCalc), Toast.LENGTH_SHORT).show();

            int size = Integer.parseInt(sizeOperations.getText().toString());

            Thread t1 = new Thread(new Runnable() {
                final  List<Integer> arrayList = new ArrayList<>();
                @Override
                public void run() {
                    int time = (int) CollectionsOperations.calcAddingToStart(size, arrayList);
                }
            });
            t1.start();
        }

    }

    public List<Items> generateCollectionItems() {
        List<Items> items = new ArrayList<>();
        String[] arrayTypes = getContext().getResources().getStringArray(R.array.strArrayTypes);
        for (int i = 0; i < 7; i++) {
            for (String arrayType : arrayTypes) {
                items.add(new Items(arrayType, getContext().getResources().getString(R.string.NAms), false));
            }
        }
        return items;
    }

    public static void setBooleanVisibility(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sizeOperations.setError(null);
    }
}