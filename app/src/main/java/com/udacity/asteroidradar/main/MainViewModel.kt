package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRadarRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val asteroidRadarDatabase: AsteroidRadarDatabase
) : ViewModel() {
    private val TAG: String = javaClass.simpleName
    private var asteroidsRadarRepository = AsteroidRadarRepository(asteroidRadarDatabase)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _asteroids = Transformations.map(
        asteroidRadarDatabase.asteroidDao.getAllOrderedByCloseApproachDateASC()) {
        it.asDomainModel()
    }
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    init {
        getPictureOfTheDay()
        getAsteroids()
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            val response = NasaApi.retrofitService.getPictureOfTheDay(Constants.API_KEY)
            Log.d(TAG, response.toString())
            _pictureOfDay.value = response
        }
    }

    private fun getAsteroids() {

        val calendar: Calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)

        val today = dateFormat.format(calendar.time)

        calendar.add(Calendar.DATE, 7)
        val nextSevenDay = dateFormat.format(calendar.time)

        viewModelScope.launch {
            try {
                asteroidsRadarRepository.refreshAsteroids(today, nextSevenDay)
            } catch (e: Exception) {
                Log.i("TEST", e.toString())
            }
        }
    }

    fun onAsteroidClicked(clickedAsteroid: Asteroid) {
        Log.d(TAG, "Item clicked: $clickedAsteroid")
    }
}