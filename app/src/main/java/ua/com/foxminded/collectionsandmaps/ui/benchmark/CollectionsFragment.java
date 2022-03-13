package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import ua.com.foxminded.collectionsandmaps.R;
import ua.com.foxminded.collectionsandmaps.ui.benchmark.adapter.CollectionsRecyclerAdapter;

public class CollectionsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_TYPE = "arg_type";
    private final CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter();

    private CollectionsViewModel viewModel;

    private Button startButton;
    private TextInputLayout sizeOperations;
    private TextInputEditText editSizeOperations;

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
                new CollectionsViewModelFactory(getArguments().getInt(ARG_TYPE)))
                .get(CollectionsViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_benchmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getItemsList().observe(getViewLifecycleOwner(), collectionsAdapter::setItems);
        viewModel.getSizeErrorStatus().observe(getViewLifecycleOwner(), integer -> {
            if (integer == null) {
                sizeOperations.setError(null);
            } else {
                sizeOperations.setError(getString(integer));
            }
        });
        viewModel.getToastStatus().observe(getViewLifecycleOwner(), integer -> {
            if (integer == 0) {
                return;
            }
            if (integer == R.string.startingCalc) {
                Toast.makeText(getContext(), integer, Toast.LENGTH_SHORT).show();
                startButton.setText(R.string.stop);
            } else if (integer == R.string.endingCalc | integer == R.string.stopCalc) {
                Toast.makeText(getContext(), integer, Toast.LENGTH_SHORT).show();
                startButton.setText(R.string.start);
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(collectionsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), viewModel.getSpanCount()));

        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        sizeOperations = view.findViewById(R.id.textInputLayoutOperations);
        editSizeOperations = view.findViewById(R.id.textInputEditTextOperations);
    }

    @Override
    public void onClick(View v) {
        String collectionSize = editSizeOperations.getText().toString().trim();
        viewModel.calculateTime(collectionSize);
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
