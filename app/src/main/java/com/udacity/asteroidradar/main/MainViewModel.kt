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

    // initial asteroids are all obtained from API via worker
    private var _asteroids = Transformations.map(
        asteroidRadarDatabase.asteroidDao.getAllOrderedByCloseApproachDateASC()
    ) {
        it.asDomainModel()
    }
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    init {
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            val response = NasaApi.retrofitService.getPictureOfTheDay(Constants.API_KEY)
            Log.d(TAG, response.toString())
            _pictureOfDay.value = response
        }
    }

    // events
    fun onShowTodayAsteroids() {

    }

}