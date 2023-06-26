package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class AllRecordsFragment extends Fragment {
    FloatingActionButton createRecordFabBtn;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    List<RecordModel> recordModelList;
    RecordAdapter recordAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_records,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        createRecordFabBtn = view.findViewById(R.id.createNoteFabBtn);

        if(FirebaseAuth.getInstance().getUid()!=null) {
            Query query = FirebaseFirestore.getInstance().collection("Records")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MyRecords")
                    .orderBy("timestamp", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<RecordModel> options = new FirestoreRecyclerOptions.Builder<RecordModel>()
                    .setQuery(query, RecordModel.class).build();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recordAdapter = new RecordAdapter(options, getContext());
            recyclerView.setAdapter(recordAdapter);
        }


        createRecordFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateNote.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getUid()!=null) {
            recordAdapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(FirebaseAuth.getInstance().getUid()!=null) {
            recordAdapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getUid()!=null) {
            recordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    
}