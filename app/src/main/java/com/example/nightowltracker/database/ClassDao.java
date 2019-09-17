package com.example.nightowltracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClass(ClassEntity classEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllClasses(List<ClassEntity> classEntities);

    @Delete
    void deleteClass(ClassEntity classEntity);

    @Query("SELECT * FROM class WHERE classId = :id")
    ClassEntity getClassById(int id);

    @Query("SELECT * FROM class WHERE sessionId = :id ORDER BY sessionId")
    ClassEntity getClassBySessionId(int id);

    @Query("SELECT * FROM class ORDER BY classId")
    LiveData<List<ClassEntity>> getAll();

    @Query("DELETE FROM class")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM class")
    int getCount();
}
