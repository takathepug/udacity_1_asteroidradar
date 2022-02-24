package com.udacity.asteroidradar

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.repository.AsteroidRadarRepository
import java.text.SimpleDateFormat
import java.util.*


class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val TAG: String = javaClass.simpleName

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val asteroidRadarDatabase: AsteroidRadarDatabase =
            AsteroidRadarDatabase.getInstance(applicationContext)
        val asteroidRadarRepository = AsteroidRadarRepository(asteroidRadarDatabase)

        return try {
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)

            val now: Calendar = Calendar.getInstance()
            val todayAsString = dateFormat.format(now.time)

            // get 7 days of asteroids
            now.add(Calendar.DATE, 7)
            val inSevenDaysAsString = dateFormat.format(now.time)

            asteroidRadarRepository.refreshAsteroids(todayAsString, inSevenDaysAsString)

            Log.d(TAG, "Refreshed asteroids between $todayAsString and $inSevenDaysAsString")

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
