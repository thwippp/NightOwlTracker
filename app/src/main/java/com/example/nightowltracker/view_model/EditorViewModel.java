package com.example.nightowltracker.view_model;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.AppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<AcademicSessionEntity> mLiveAs =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int sessionId;

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication()); // get context?

    }

    public void loadData(final int sessionId) {
        //needs to be wrapped inside a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AcademicSessionEntity academicSession = mRepository.getAcademicSessionById(sessionId);  // make sessionId final???
                mLiveAs.postValue(academicSession);
            }
        });
    }

    public void saveData(String dataText) {
        AcademicSessionEntity academicSession = mLiveAs.getValue();

        if (academicSession == null) {
            // new data
            if (TextUtils.isEmpty(dataText.trim())) {
                // if not just spaces or empty
                return;
            }
            academicSession = new AcademicSessionEntity(dataText.trim(), new Date(), new Date());  // creates new AS with title and dates
        } else {
            academicSession.setTitle(dataText.trim());
        }
        mRepository.insertAcademicSession(academicSession);
    }

    public void deleteData() {
        mRepository.deleteData(mLiveAs.getValue());  // reference wrapped in live data
    }
}
