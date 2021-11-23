package ua.com.foxminded.collectionsandmaps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CollectionsFragment extends Fragment {

    RecyclerView recyclerView;
    String[] arrayTypes;
    EditText sizeOperations;
    Button startButton;


    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CollectionsFragment() {
           }


    // TODO: Rename and change types and number of parameters
    public static CollectionsFragment newInstance(String param1, String param2) {
        CollectionsFragment fragment = new CollectionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        startButton = view.findViewById(R.id.startButton);
        sizeOperations = view.findViewById(R.id.operationsInput);

        Message msg = new Message();

        arrayTypes = getResources().getStringArray(R.array.strArrayTypes);
        CollectionsRecyclerAdapter collectionsAdapter = new CollectionsRecyclerAdapter(arrayTypes, msg);
        recyclerView.setAdapter(collectionsAdapter);
        
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(), "Starting calculations", Toast.LENGTH_SHORT).show();

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
        });

        

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return view;
    }
}