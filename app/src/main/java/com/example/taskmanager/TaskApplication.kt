package com.example.taskmanager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanager.data.AppContainer
import com.example.taskmanager.data.AppDataContainer
import com.example.taskmanager.data.lightrepository.LightDataContainer

// this is the class where all the dependencies needed for the project are created

    private const val PREFERENCE_NAME = "ScreenLightMode"
    private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class TaskApplication: Application() {
    lateinit var container: AppContainer
    lateinit var userPreference: LightDataContainer

    override fun onCreate(){
        super.onCreate()
        container =  AppDataContainer(this)
        userPreference = LightDataContainer(datastore)

    }

}