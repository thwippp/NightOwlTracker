package com.example.nightowltracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(List<UserEntity> user);

    @Delete
    void deleteUser(UserEntity user);

    @Query("SELECT * FROM user WHERE userId = :id")
    UserEntity getUserById(int id);

    @Query("SELECT * FROM user ORDER BY userId")
    LiveData<List<UserEntity>> getAll();

    @Query("DELETE FROM user")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM user")
    int getCount();



}
