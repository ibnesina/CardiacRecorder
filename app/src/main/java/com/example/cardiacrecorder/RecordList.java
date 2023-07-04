package com.example.cardiacrecorder;

import java.util.ArrayList;

public class RecordList {
    public static ArrayList<RecordModel> arrayList = new ArrayList<>();

    public void addRecord(RecordModel recordModel) {
        if(arrayList.contains(recordModel)) {
            throw new IllegalArgumentException();
        }
        arrayList.add(recordModel);
    }

    public void deleteRecord(int position){
        if(position>=0 && position<arrayList.size()) {
            arrayList.remove(position);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public int count() {
        return arrayList.size();
    }
}
