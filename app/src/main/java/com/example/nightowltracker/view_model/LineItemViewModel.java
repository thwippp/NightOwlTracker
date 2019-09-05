package com.example.nightowltracker.view_model;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nightowltracker.database.AppRepository;
import com.example.nightowltracker.database.LineItemEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LineItemViewModel extends AndroidViewModel {

    public LiveData<List<LineItemEntity>> mLineItem;
    public MutableLiveData<LineItemEntity> mLiveLI =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int classId;

    public LineItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication()); // get context?
        mLineItem = mRepository.mLineItem;
    }

    public void loadData(final int lineItemId) {
        //needs to be wrapped inside a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                LineItemEntity lineItemEntity = mRepository.getLineItemById(lineItemId);
                mLiveLI.postValue(lineItemEntity);
            }
        });
    }

    public void saveData(String title, String description, String category, Date assignDate, Date dueDate, int classId) {
        LineItemEntity lineItemEntity = mLiveLI.getValue();

        if (lineItemEntity == null) {
            // new data
            if (TextUtils.isEmpty(title.trim())) {
                // if not just spaces or empty
                return;
            }
            lineItemEntity = new LineItemEntity(title.trim(), description.trim(), category.trim(), assignDate, dueDate, classId);  // creates new Entity with title and dates
        } else {
            lineItemEntity.setTitle(title.trim());
            lineItemEntity.setDescription(description.trim());
            lineItemEntity.setCategory(category.trim());
            lineItemEntity.setAssignDate(assignDate);
            lineItemEntity.setDueDate(dueDate);
            lineItemEntity.setClassId(classId);
        }
        mRepository.insertLineItem(lineItemEntity);
    }

    public void deleteData() {
        mRepository.deleteLineItem(mLiveLI.getValue());  // reference wrapped in live data
    }

}
