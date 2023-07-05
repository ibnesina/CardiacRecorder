package com.example.cardiacrecorder;

import java.util.ArrayList;

public class RecordList {
    public static ArrayList<RecordModel> arrayList = new ArrayList<>();

    /**
     * Adds a record to the list of records.
     * @param recordModel The RecordModel object to be added.
     * @throws IllegalArgumentException If the recordModel already exists in the list.
     */
    public void addRecord(RecordModel recordModel) {
        if(arrayList.contains(recordModel)) {
            throw new IllegalArgumentException();
        }
        arrayList.add(recordModel);
    }

    /**
     * Deletes a record from the list at the specified position.
     * @param position The position to be deleted
     * @throws IllegalArgumentException If the position is invalid.
     */
    public void deleteRecord(int position){
        if(position>=0 && position<arrayList.size()) {
            arrayList.remove(position);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the counts of the records
     * @return The number of records.
     */
    public int count() {
        return arrayList.size();
    }
}
