package com.example.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    //if you try to insert an item with a duplicate primary key, the insert operation will be ignored,
    // and no exception will be thrown.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item): Long

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun update(item: Item)

    @Query("SELECT reminderTime FROM Tasks WHERE id = :taskId")
     fun getReminderTime(taskId: Long): Long

    @Query("SELECT * FROM Tasks")
    fun getItems(): Flow<List<Item>>
     // if I delete an item from the table the Flow will automatically update the ui when the underlying data changes
}