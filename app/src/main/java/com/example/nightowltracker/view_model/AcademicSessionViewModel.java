package com.example.nightowltracker.view_model;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.AppRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AcademicSessionViewModel extends AndroidViewModel {

    public LiveData<List<AcademicSessionEntity>> mAcademicSession;
    public MutableLiveData<AcademicSessionEntity> mLiveAs =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int sessionId;

    private ClassViewModel cViewModel;

    public AcademicSessionViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication()); // get context?
        mAcademicSession = mRepository.mAcademicSession;
    }

    public void loadData(final int sessionId) {
        //needs to be wrapped inside a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AcademicSessionEntity academicSession = mRepository.getAcademicSessionById(sessionId);
                mLiveAs.postValue(academicSession);
            }
        });
    }

    public void saveData(String title, Date startDate, Date endDate) {
        AcademicSessionEntity academicSession = mLiveAs.getValue();

        if (academicSession == null) {
            // new data
            if (TextUtils.isEmpty(title.trim())) {
                // if not just spaces or empty
                return;
            }
            academicSession = new AcademicSessionEntity(title.trim(), startDate, endDate);  // creates new AS with title and dates
        } else {
            academicSession.setTitle(title.trim());
            academicSession.setStartDate(startDate);
            academicSession.setEndDate(endDate);
        }
        mRepository.insertAcademicSession(academicSession);
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllASData();
    }

    public void deleteData() {
        mRepository.deleteAcademicSession(mLiveAs.getValue());  // reference wrapped in live data
    }
}
