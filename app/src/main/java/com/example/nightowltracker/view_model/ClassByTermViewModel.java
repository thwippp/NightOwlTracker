package com.example.nightowltracker.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.AppRepository;
import com.example.nightowltracker.database.ClassEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClassByTermViewModel extends AndroidViewModel {

    public LiveData<List<ClassEntity>> mClass;
    public MutableLiveData<ClassEntity> mLiveC =
            new MutableLiveData<>();
    public MutableLiveData<AcademicSessionEntity> mLiveAS =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ClassByTermViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication()); // get context?
        mClass = mRepository.mClass;
    }

    public void loadClassByTermData(final int sessionId) {
        //needs to be wrapped inside a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ClassEntity classEntity = mRepository.getClassBySessionId(sessionId);
                mLiveC.postValue(classEntity);
            }
        });
    }

}
