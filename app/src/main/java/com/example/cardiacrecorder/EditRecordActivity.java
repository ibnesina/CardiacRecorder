package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditRecordActivity extends AppCompatActivity {
    EditText heartRate, comment, systolic, diastolic;
    String hr, comm, sys, dias, docID;
    FloatingActionButton saveBtn;
    boolean editMode;

    /**
     * Edits the current record and store it to Firebase Firestore
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Toolbar toolbar = findViewById(R.id.toolbarOfCreateNote);
        toolbar.setTitle("Edit Record");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        heartRate = findViewById(R.id.heartRate);
        comment = findViewById(R.id.comment);
        systolic = findViewById(R.id.systolic);
        diastolic = findViewById(R.id.diastolic);
        saveBtn = findViewById(R.id.saveNoteFabBtn);

        hr = getIntent().getStringExtra("heartRate");
        comm = getIntent().getStringExtra("comment");
        sys = getIntent().getStringExtra("systolic");
        dias = getIntent().getStringExtra("diastolic");
        docID = getIntent().getStringExtra("docId");

        heartRate.setText(hr);
        systolic.setText(sys);
        diastolic.setText(dias);
        comment.setText(comm);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordModel recordModel = new RecordModel(heartRate.getText().toString(), diastolic.getText().toString(),
                        systolic.getText().toString(), comment.getText().toString(), Timestamp.now());
                FirebaseFirestore.getInstance().collection("Records").
                        document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MyRecords")
                        .document(docID).set(recordModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EditRecordActivity.this, "Record Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditRecordActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditRecordActivity.this, "Failed to Update record", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}