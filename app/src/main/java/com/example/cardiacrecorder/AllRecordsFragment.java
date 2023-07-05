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

    /**
     * sets up the RecyclerView to display the user's records in descending order based on the timestamp.
     * If the user is not authenticated (not logged in), the "createRecordFabBtn" button will direct
     *      the user to the RegisterActivity to sign in or register.
     * Otherwise, it will direct the user to the CreateNote activity to create a new record.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     *      UI should be attached to.  The fragment should not add the view itself,
     *      but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *      from a previous saved state as given here.
     *
     * @return
     */
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
                if(FirebaseAuth.getInstance().getUid()==null) {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                else {
                    startActivity(new Intent(getContext(), CreateNote.class));
                }
            }
        });

        return view;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * Starts listening for data changes in the recordAdapter to update the RecyclerView.
     */
    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getUid()!=null) {
            recordAdapter.startListening();
        }

    }

    /**
     * Called when the fragment is no longer visible to the user and not actively running.
     * Stops listening for data changes in the recordAdapter to release resources.
     */
    @Override
    public void onStop() {
        super.onStop();
        if(FirebaseAuth.getInstance().getUid()!=null) {
            recordAdapter.stopListening();
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * Notifies the recordAdapter of any data set changes to update the UI.
     */
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