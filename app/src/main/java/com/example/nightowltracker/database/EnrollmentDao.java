package com.example.nightowltracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EnrollmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEnrollment(EnrollmentEntity enrollmentEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllEnrollments(List<EnrollmentEntity> enrollmentEntity);

    @Delete
    void deleteEnrollment(EnrollmentEntity enrollmentEntity);

    @Query("SELECT * FROM enrollment WHERE enrollmentId = :id")
    EnrollmentEntity getEnrollmentById(int id);

    @Query("SELECT * FROM enrollment ORDER BY enrollmentId = :id")
    LiveData<List<EnrollmentEntity>> getAll();

    @Query("DELETE FROM enrollment")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM ENROLLMENT")
    int getCount();

}
