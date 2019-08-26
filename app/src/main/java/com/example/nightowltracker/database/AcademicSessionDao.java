package com.example.nightowltracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AcademicSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAcademicSession(AcademicSessionEntity academicSessionEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAcademicSessions(List<AcademicSessionEntity> academicSession);

    @Delete
    void deleteAcademicSession(AcademicSessionEntity academicSessionEntity);

    @Query("SELECT * FROM academicsession WHERE sessionId = :id")
    AcademicSessionEntity getAcademicSessionById(int id);

    @Query("SELECT * FROM academicsession ORDER BY startDate DESC")
    LiveData<List<AcademicSessionEntity>> getAll();

    @Query("DELETE FROM academicsession")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM AcademicSession")
    int getCount();
}
