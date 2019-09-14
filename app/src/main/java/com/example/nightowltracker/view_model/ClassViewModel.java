package com.example.nightowltracker.view_model;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nightowltracker.database.AppRepository;
import com.example.nightowltracker.database.ClassEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClassViewModel extends AndroidViewModel {

    public LiveData<List<ClassEntity>> mClass;
    public MutableLiveData<ClassEntity> mLiveC =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int classId;

    public ClassViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication()); // get context?
        mClass = mRepository.mClass;

    }

    public void loadData(final int classId) {
        //needs to be wrapped inside a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ClassEntity classEntity = mRepository.getClassById(classId);
                mLiveC.postValue(classEntity);
            }
        });
    }

    public void saveData(String title, String classCode, String status, int sessionId, int userId) {
        ClassEntity classEntity = mLiveC.getValue();

        if (classEntity == null) {
            // new data
            if (TextUtils.isEmpty(title.trim())) {
                // if not just spaces or empty
                return;
            }
            classEntity = new ClassEntity(title.trim(), classCode.trim(), status.trim(), sessionId, userId);  // creates new AS with title and dates
        } else {
            classEntity.setTitle(title.trim());
            classEntity.setClassCode(classCode.trim());
            classEntity.setStatus(status.trim());
            classEntity.setSessionId(sessionId);
            classEntity.setUserId(userId);
//            if (sessionId == 0) {
//                classEntity.setSessionId(1);
//            } else {
//                classEntity.setSessionId(sessionId);
//            }
        }
        mRepository.insertClass(classEntity);
    }

    public void deleteData() {
        mRepository.deleteClass(mLiveC.getValue());  // reference wrapped in live data
    }

}
