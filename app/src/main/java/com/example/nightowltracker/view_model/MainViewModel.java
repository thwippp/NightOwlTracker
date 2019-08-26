package com.example.nightowltracker.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.nightowltracker.database.AcademicSessionEntity;
import com.example.nightowltracker.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<AcademicSessionEntity> mAcademicSession = SampleData.getAcademicSessions();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
