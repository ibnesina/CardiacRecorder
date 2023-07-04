package com.example.cardiacrecorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.annotation.SuppressLint;

import com.google.firebase.Timestamp;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RecordAddDeleteUnitTest {
    @Test
    public void testAddRecord() {
        RecordList recordList = new RecordList();

        RecordModel recordModel1 = new RecordModel("65", "80", "120", "Walking", Timestamp.now());
        recordList.addRecord(recordModel1);
        assertEquals(1, recordList.arrayList.size());

        RecordModel recordModel2 = new RecordModel("70", "60", "90", "Resting", Timestamp.now());
        recordList.addRecord(recordModel2);
        assertEquals(2, recordList.arrayList.size());
    }

    @Test
    public void testAddRecordException() {
        RecordList recordList = new RecordList();

        RecordModel recordModel1 = new RecordModel("65", "80", "120", "Walking", Timestamp.now());
        recordList.addRecord(recordModel1);
        assertThrows(IllegalArgumentException.class, ()-> recordList.addRecord(recordModel1));
    }

    @Test
    public void testDeleteRecord() {
        RecordList recordList = new RecordList();

        RecordModel recordModel1 = new RecordModel("70", "80", "120", "Walking", Timestamp.now());
        recordList.addRecord(recordModel1);
        assertEquals(1, recordList.arrayList.size());
        assertTrue(recordList.arrayList.contains(recordModel1));

        RecordModel recordModel2 = new RecordModel("65", "60", "90", "Resting", Timestamp.now());
        recordList.addRecord(recordModel2);
        assertEquals(2, recordList.arrayList.size());
        assertTrue(recordList.arrayList.contains(recordModel2));

        RecordModel recordModel3 = new RecordModel("75", "80", "130", "Working", Timestamp.now());
        recordList.addRecord(recordModel3);
        assertEquals(3, recordList.arrayList.size());
        assertTrue(recordList.arrayList.contains(recordModel3));

        recordList.deleteRecord(1);
        assertEquals(2, recordList.arrayList.size());
        assertTrue(!recordList.arrayList.contains(recordModel2));

        recordList.deleteRecord(0);
        assertEquals(1, recordList.arrayList.size());
        assertTrue(!recordList.arrayList.contains(recordModel1));
    }

    @Test
    public void testDeleteRecordException() {
        RecordList recordList = new RecordList();

        RecordModel recordModel1 = new RecordModel("70", "80", "120", "Walking", Timestamp.now());
        recordList.addRecord(recordModel1);
        assertEquals(1, recordList.arrayList.size());
        assertTrue(recordList.arrayList.contains(recordModel1));

        assertThrows(IllegalArgumentException.class, ()-> recordList.deleteRecord(1));
    }

    @Test
    public void testCountRecord() {
        RecordList recordList = new RecordList();

        RecordModel recordModel1 = new RecordModel("70", "80", "120", "Walking", Timestamp.now());
        recordList.addRecord(recordModel1);
        assertEquals(1, recordList.count());

        RecordModel recordModel2 = new RecordModel("65", "60", "90", "Resting", Timestamp.now());
        recordList.addRecord(recordModel2);
        assertEquals(2, recordList.count());
    }
}