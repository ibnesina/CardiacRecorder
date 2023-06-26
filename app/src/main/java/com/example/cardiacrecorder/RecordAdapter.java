package com.example.cardiacrecorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;

public class RecordAdapter extends FirestoreRecyclerAdapter<RecordModel, RecordAdapter.RecordViewHolder> {
    Context context;
    public RecordAdapter(@NonNull FirestoreRecyclerOptions<RecordModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RecordViewHolder holder, int position, @NonNull RecordModel recordModel) {
        holder.heartRate.setText(recordModel.getHeartRate());
        holder.systolic.setText(recordModel.getSystolic());
        holder.diastolic.setText(recordModel.getDiastolic());
        holder.comment.setText(recordModel.getComment());
        holder.timeStamp.setText(new SimpleDateFormat("MMM d, yyyy").format(recordModel.getTimestamp().toDate()));

        if(Integer.parseInt(recordModel.getDiastolic())<60 || Integer.parseInt(recordModel.getDiastolic())>80
                || Integer.parseInt(recordModel.getSystolic())<90 || Integer.parseInt(recordModel.getSystolic())>120) {
            holder.statusIcon.setColorFilter(Color.rgb(255, 0, 0));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, RecordDetailsActivity.class);
//                intent.putExtra("heartRate", recordModel.getHeartRate());
//                intent.putExtra("systolic", recordModel.getSystolic());
//                intent.putExtra("diastolic", recordModel.getDiastolic());
//                intent.putExtra("comment", recordModel.getComment());
//                String docId = getSnapshots().getSnapshot(position).getId();
//                intent.putExtra("docId", docId);
//                intent.putExtra("date", new SimpleDateFormat("MMM d, yyyy").format(recordModel.getTimestamp().toDate()));
//                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_layout, parent, false);
        return new RecordViewHolder(view);
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView heartRate, systolic, diastolic, comment, timeStamp;
        ImageView statusIcon;
        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            heartRate = itemView.findViewById(R.id.heartRate);
            systolic = itemView.findViewById(R.id.systolic);
            diastolic = itemView.findViewById(R.id.diastolicTxt);
            comment = itemView.findViewById(R.id.comment);
            timeStamp = itemView.findViewById(R.id.date);
            statusIcon = itemView.findViewById(R.id.statusIcon);
        }
    }
}