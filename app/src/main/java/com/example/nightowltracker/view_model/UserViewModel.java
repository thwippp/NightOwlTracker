package com.example.nightowltracker.view_model;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nightowltracker.database.AppRepository;
import com.example.nightowltracker.database.UserEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserViewModel extends AndroidViewModel {

    public LiveData<List<UserEntity>> mUser;
    public MutableLiveData<UserEntity> mLiveU =
            new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int userId;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication()); // get context?
        mUser = mRepository.mUser;

    }

    public void loadData(final int userId) {
        //needs to be wrapped inside a background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                UserEntity userEntity = mRepository.getUserById(userId);
                mLiveU.postValue(userEntity);
            }
        });
    }

    public void saveData(String username, String givenname, String familyname, String role, String email, String phone, String sms) {
        UserEntity userEntity = mLiveU.getValue();

        if (userEntity == null) {
            // new data
            if (TextUtils.isEmpty(username.trim())) {
                // if not just spaces or empty
                return;
            }
            userEntity = new UserEntity(username.trim(), givenname.trim(), familyname.trim(), role, email.trim(), phone.trim(), sms.trim());  // creates new User
        } else {
            userEntity.setUsername(username.trim());
            userEntity.setGivenName(givenname.trim());
            userEntity.setFamilyName(familyname.trim());
            userEntity.setRole(role);
            userEntity.setEmail(email.trim());
            userEntity.setPhone(phone.trim());
            userEntity.setSms(sms.trim());
        }
        mRepository.insertUser(userEntity);
    }

    public void deleteData() {
        mRepository.deleteUser(mLiveU.getValue());  // reference wrapped in live data
    }

}
