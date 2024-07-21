package com.example.taskmanager.data

import android.content.Context

interface AppContainer{
    val itemsRepository: ItemsRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val itemsRepository: ItemsRepository by lazy{
        OfflineItemsRepository(TaskDatabase.getDatabase(context).itemDao())
    }
}