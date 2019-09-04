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
    public LiveData<List<ClassEntity>> mClass;


    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();  // don't run multiple DB operations at the same time!  This queues them.

    ///////////////////////////////////////////////////
    // All Classes
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mAcademicSession = getAllAcademicSessions();
        mClass = getAllClasses();
    }

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    ///////////////////////////////////////////////////
    // Adds sample data for AS
    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.academicSessionDao().insertAllAcademicSessions(SampleData.getAcademicSessions());
            }
        });

    }

    ///////////////////////////////////////////////////
    // Academic Session
    private LiveData<List<AcademicSessionEntity>> getAllAcademicSessions() {
        return mDb.academicSessionDao().getAll();
    }

    public void deleteAllASData() {
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

    public void deleteAcademicSession(final AcademicSessionEntity academicSession) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.academicSessionDao().deleteAcademicSession(academicSession);  // referenced from within another object, make it final
            }
        });
    }

    ///////////////////////////////////////////////////
    // Class
    private LiveData<List<ClassEntity>> getAllClasses() {
        return mDb.classDao().getAll();
    }

    public void deleteAllClassData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.classDao().deleteAll();
            }
        });
    }

    public ClassEntity getClassById(int classId) {
        return mDb.classDao().getClassById(classId);
    }

    public void insertClass(final ClassEntity classEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.classDao().insertClass(classEntity);
            }
        });
    }

    public void deleteClass(final ClassEntity classEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.classDao().deleteClass(classEntity);
            }
        });
    }
}