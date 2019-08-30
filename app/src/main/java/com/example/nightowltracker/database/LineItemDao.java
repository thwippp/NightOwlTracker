package com.example.nightowltracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LineItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLineItem(LineItemEntity lineItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllLineItems(List<LineItemEntity> lineItem);

    @Delete
    void deleteLineItem(LineItemEntity lineItem);

    @Query("SELECT * FROM LineItem WHERE lineItemId = :id")
    LineItemEntity getLineItemById(int id);

    @Query("SELECT * FROM lineitem ORDER BY lineItemId")
    LiveData<List<LineItemEntity>> getAll();

    @Query("DELETE FROM LineItem")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM lineitem")
    int getCount();
}

