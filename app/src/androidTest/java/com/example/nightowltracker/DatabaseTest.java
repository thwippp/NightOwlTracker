package com.example.nightowltracker;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.nightowltracker.database.AcademicSessionDao;
import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.AppDatabase;
import com.example.nightowltracker.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private AcademicSessionDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();  // IR.getTargetConte
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.academicSessionDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveAcademicSessions() {

        mDao.insertAllAcademicSessions(SampleData.getAcademicSessions());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveAcademicSessions: count" + count);
        assertEquals(SampleData.getAcademicSessions().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAllAcademicSessions(SampleData.getAcademicSessions());
        AcademicSessionEntity original = SampleData.getAcademicSessions().get(0);
        AcademicSessionEntity fromDb = mDao.getAcademicSessionById(1);
        assertEquals(original.getTitle(), fromDb.getTitle());
        assertEquals(1, fromDb.getTitle());
    }
}
