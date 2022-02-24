package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    // get all asteroids with close approach date starting today in ascending order
    @Query("SELECT * FROM asteroid WHERE close_approach_date >= DATE('now') ORDER BY close_approach_date ASC")
    fun getAllOrderedByCloseApproachDateASC(): LiveData<List<DatabaseAsteroid>>

    // upsert list of asteroids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids: List<DatabaseAsteroid>)
}