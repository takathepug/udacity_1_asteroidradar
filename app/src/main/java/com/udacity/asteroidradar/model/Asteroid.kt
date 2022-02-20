package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

// transformation to database object
fun Asteroid.asDomainModel(): DatabaseAsteroid {
    return DatabaseAsteroid(
        id,
        codename,
        closeApproachDate,
        absoluteMagnitude,
        estimatedDiameter,
        relativeVelocity,
        distanceFromEarth,
        isPotentiallyHazardous
    )
}

fun List<Asteroid>.asDomainModel(): List<DatabaseAsteroid> {
    return map {
        it.asDomainModel()
    }
}