package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    // get all asteroids with close approach date starting today in ascending order
    @Query("SELECT * FROM asteroid ORDER BY close_approach_date ASC")
    fun getAllOrderedByCloseApproachDateASC(): LiveData<List<DatabaseAsteroid>>

    // get today asteroids
    @Query("SELECT * FROM asteroid WHERE close_approach_date = DATE('now')")
    fun getToday(): LiveData<List<DatabaseAsteroid>>

    // get week asteroids
    @Query("SELECT * FROM asteroid WHERE close_approach_date BETWEEN DATE('now') AND DATE('now', '7 days') ORDER BY close_approach_date ASC")
    fun getWeekOrderedByCloseApproachDateASC(): LiveData<List<DatabaseAsteroid>>

    // upsert list of asteroids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids: List<DatabaseAsteroid>)
}