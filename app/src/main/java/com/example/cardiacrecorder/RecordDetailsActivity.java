package com.example.cardiacrecorder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecordDetailsActivity extends AppCompatActivity {
    TextView heartRate, comment, systolic, diastolic;
    String hr, comm, sys, dias, docID;
    Button editBtn, deleteBtn;
    ImageView statusIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        Toolbar toolbar = findViewById(R.id.toolbarOfCreateNote);
        toolbar.setTitle("Record Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        heartRate = findViewById(R.id.heartRate);
        comment = findViewById(R.id.comment);
        systolic = findViewById(R.id.systolic);
        diastolic = findViewById(R.id.diastolic);

        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        statusIcon = findViewById(R.id.statusIcon);

        hr = getIntent().getStringExtra("heartRate");
        comm = getIntent().getStringExtra("comment");
        sys = getIntent().getStringExtra("systolic");
        dias = getIntent().getStringExtra("diastolic");
        docID = getIntent().getStringExtra("docId");

        heartRate.setText(hr);
        systolic.setText(sys);
        diastolic.setText(dias);
        comment.setText(comm);

        if(Integer.parseInt(dias)<60 || Integer.parseInt(dias)>80 || Integer.parseInt(sys)<90 || Integer.parseInt(sys)>120) {
            statusIcon.setColorFilter(Color.rgb(255, 0, 0));
        }

       // editBtn.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
        //        Intent intent = new Intent(RecordDetailsActivity.this, EditRecordActivity.class);
         //       intent.putExtra("heartRate", hr);
         //       intent.putExtra("systolic", sys);
         //       intent.putExtra("diastolic", dias);
          //      intent.putExtra("comment", comm);
         //       intent.putExtra("docId", docID);
          //      startActivity(intent);
         //   }
       // });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("Records").
                        document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MyRecords")
                        .document(docID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RecordDetailsActivity.this, "Record Deleted Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RecordDetailsActivity.this, MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RecordDetailsActivity.this, "Failed to Delete Record", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}