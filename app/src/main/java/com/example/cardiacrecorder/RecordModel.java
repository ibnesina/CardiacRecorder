package com.example.cardiacrecorder;

import com.google.firebase.Timestamp;

public class RecordModel {
    private String heartRate, diastolic, systolic, comment;
    Timestamp timestamp;

    public RecordModel() {
    }

    /**
     * Constructs a new RecordModel object with the provided data.
     *
     * @param heartRate  The heart rate value of the record.
     * @param diastolic  The diastolic blood pressure value of the record.
     * @param systolic   The systolic blood pressure value of the record.
     * @param comment    The comment associated with the record.
     * @param timestamp  The timestamp indicating the time of the record.
     */
    public RecordModel(String heartRate, String diastolic, String systolic, String comment, Timestamp timestamp) {
        this.heartRate = heartRate;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
