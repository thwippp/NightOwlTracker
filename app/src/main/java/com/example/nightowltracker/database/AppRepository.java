package com.example.nightowltracker.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.nightowltracker.activities.AcademicSessionMainActivity;
import com.example.nightowltracker.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<AcademicSessionEntity>> mAcademicSession;
    public LiveData<List<ClassEntity>> mClass;
    public LiveData<List<LineItemEntity>> mLineItem;
    public LiveData<List<UserEntity>> mUser;

    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();  // don't run multiple DB operations at the same time!  This queues them.

    ///////////////////////////////////////////////////
    // All Classes
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mAcademicSession = getAllAcademicSessions();
        mClass = getAllClasses();
        mLineItem = getAllLineItems();
        mUser = getAllUsers();
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
                try {
                    mDb.academicSessionDao().deleteAcademicSession(academicSession);  // referenced from within another object, make it final
                } catch (Exception e) {
                    AcademicSessionMainActivity.isFKViolation = true;
                    System.out.println("isFKViolation");
                    e.printStackTrace();
                }
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

    public ClassEntity getClassBySessionId(int sessionId) {
        return mDb.classDao().getClassBySessionId(sessionId);
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

    ///////////////////////////////////////////////////
    // Line Item
    private LiveData<List<LineItemEntity>> getAllLineItems() {
        return mDb.lineItemDao().getAll();
    }

    public void deleteAllLineItemData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.lineItemDao().deleteAll();
            }
        });
    }

    public LineItemEntity getLineItemById(int lineItemId) {
        return mDb.lineItemDao().getLineItemById(lineItemId);
    }

    public void insertLineItem(final LineItemEntity lineItemEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.lineItemDao().insertLineItem(lineItemEntity);
            }
        });
    }

    public void deleteLineItem(final LineItemEntity lineItemEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.lineItemDao().deleteLineItem(lineItemEntity);
            }
        });
    }

    ///////////////////////////////////////////////////
    // User
    private LiveData<List<UserEntity>> getAllUsers() {
        return mDb.userDao().getAll();
    }

    public void deleteAllUserData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.userDao().deleteAll();
            }
        });
    }

    public UserEntity getUserById(int userId) {
        return mDb.userDao().getUserById(userId);
    }

    public void insertUser(final UserEntity userEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.userDao().insertUser(userEntity);
            }
        });
    }

    public void deleteUser(final UserEntity userEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.userDao().deleteUser(userEntity);
            }
        });
    }





}