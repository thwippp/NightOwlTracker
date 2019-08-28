package com.example.nightowltracker.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.nightowltracker.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<AcademicSessionEntity>> mAcademicSession;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();  // don't run multiple DB operations at the same time!  This queues them.

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mAcademicSession = getAllAcademicSessions();
    }

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.academicSessionDao().insertAllAcademicSessions(SampleData.getAcademicSessions());
            }
        });

    }

    private LiveData<List<AcademicSessionEntity>> getAllAcademicSessions() {
        return mDb.academicSessionDao().getAll();
    }

    public void deleteAllData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.academicSessionDao().deleteAll();
            }
        });
    }

    public AcademicSessionEntity getAcademicSessionById(int sessionId) {
        return mDb.academicSessionDao().getAcademicSessionById(sessionId);
    }

    public void insertAcademicSession(final AcademicSessionEntity academicSession) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.academicSessionDao().insertAcademicSession(academicSession);
            }
        });
    }

    public void deleteData(final AcademicSessionEntity academicSession) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.academicSessionDao().deleteAcademicSession(academicSession);  // referenced from within another object, make it final
            }
        });
    }
}