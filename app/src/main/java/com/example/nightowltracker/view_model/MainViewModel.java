package com.example.nightowltracker.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.database.AppRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<AcademicSessionEntity>> mAcademicSession;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mAcademicSession = mRepository.mAcademicSession;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllASData();
    }
}
