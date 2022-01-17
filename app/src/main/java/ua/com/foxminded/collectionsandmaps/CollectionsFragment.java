package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CollectionsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_TYPE = "arg_type";
    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();

    private FragmentViewModel viewModel;

    private Button startButton;
    private TextInputLayout sizeOperations;
    private TextInputLayout sizeThreads;
    private TextInputEditText editSizeOperations;
    private TextInputEditText editSizeThreads;

    public static CollectionsFragment newInstance(int type) {
        final Bundle b = new Bundle();
        b.putInt(ARG_TYPE, type);
        final CollectionsFragment f = new CollectionsFragment();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                new FragmentViewModelFactory(getArguments().getInt(ARG_TYPE)))
                .get(FragmentViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel.getItemsList().observe(getViewLifecycleOwner(), collectionsAdapter::setItems);
        viewModel.getStatus().observe(getViewLifecycleOwner(), integer -> {
            if (integer == null) {
                return;
            }
            if (integer == 0) {
                Toast.makeText(getContext(), R.string.startingCalc, Toast.LENGTH_SHORT).show();
                startButton.setText(R.string.stop);
            } else if (integer == 1) {
                Toast.makeText(getContext(), R.string.endingCalc, Toast.LENGTH_SHORT).show();
                startButton.setText(R.string.start);
            } else if (integer == 2) {
                Toast.makeText(getContext(), R.string.stopCalc, Toast.LENGTH_SHORT).show();
                startButton.setText(R.string.start);
            }
        });
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
        String collectionSize = editSizeOperations.getText().toString().trim();
        String poolSize = editSizeThreads.getText().toString().trim();
        startCalculations(collectionSize, poolSize);
    }

    public void startCalculations(String collectionSize, String poolSize) {
        int threads;
        try {
            threads = Integer.parseInt(poolSize);
            sizeThreads.setError(null);
        } catch (NullPointerException | NumberFormatException e) {
            sizeThreads.setError(getString(R.string.invalidInput));
            threads = -1;
        }
        if (threads == 0){
            sizeThreads.setError(getString(R.string.invalidNumber));
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
            viewModel.calculateTime(size, threads);
        }
    }
}