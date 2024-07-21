package com.example.taskmanager.data.lightrepository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

// dependencies created in the TaskApplication class is being used and inject into LightRepository

interface AppContainer {
    val lightDataContainer: LightRepository
}

class LightDataContainer(dataStore: DataStore<Preferences>): AppContainer  {
    override val lightDataContainer: LightRepository = LightRepository(dataStore)
}