package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroid ORDER BY close_approach_date ASC")
    fun getAllOrderedByCloseApproachDateASC(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids: List<DatabaseAsteroid>)
}