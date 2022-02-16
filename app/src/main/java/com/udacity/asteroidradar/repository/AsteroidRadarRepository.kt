package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import retrofit2.Response.error

class AsteroidRadarRepository(private val db: AsteroidRadarDatabase) {

    // a list of asteroids on screen
    val asteroids: LiveData<List<Asteroid>> = Transformations.map(
        db.asteroidDao.getAllOrderedByCloseApproachDateASC()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids(startDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            val response = NasaApi.retrofitService.getAsteroids(
                Constants.API_KEY,
                startDate,
                endDate
            )
        }
    }
}