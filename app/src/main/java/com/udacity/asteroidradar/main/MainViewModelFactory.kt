package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidRadarDatabase

class MainViewModelFactory(
    private val asteroidRadarDatabase: AsteroidRadarDatabase
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(asteroidRadarDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}