package com.example.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 4, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object{

        //@Volatile and the synchronized block work together to guarantee that the Singleton pattern is correctly implemented and
        // that the database instance is initialized and accessed safely in a multi-threaded environment
        @Volatile
        private var Instance: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase{

            //n Kotlin (and Java), synchronized(this) is a construct used to create a synchronized block.
            // It ensures that only one thread can execute the code within that block at a time,
            // effectively preventing multiple threads from accessing and modifying shared resources concurrently.
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,TaskDatabase::class.java,"items_database")
                //Its purpose is to handle situations where a normal migration is not possible or not feasible.
                    // When Room detects that the current database schema version does not match the expected version
                    // (e.g., when the app is upgraded), it checks if a normal migration is defined. If no migration is defined,
                    // Room will resort to a destructive migration by dropping and recreating the database.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{Instance=it}
            }
        }
    }
}