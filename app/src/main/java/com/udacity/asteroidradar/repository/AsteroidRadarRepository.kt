package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.asDomainModel
import retrofit2.Response.error

class AsteroidRadarRepository(private val db: AsteroidRadarDatabase) {
    private val TAG: String = javaClass.simpleName

    suspend fun refreshAsteroids(startDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = NasaApi.retrofitService.getAsteroids(
                    Constants.API_KEY,
                    startDate,
                    endDate
                )

                // asteroids as domain objects from NASA
                val asteroidsList: List<Asteroid> = parseAsteroidsJsonResult(JSONObject(response))

                Log.d("TAG", "Asteroids obtained: $asteroidsList")

                // feed cache
                db.asteroidDao.insert(asteroidsList.asDomainModel())
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                e.printStackTrace()
            }
        }
    }
}