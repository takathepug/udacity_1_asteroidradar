package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Constants

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAsteroid::class], version = 1, exportSchema = false)
abstract class AsteroidRadarDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidRadarDatabase? = null

        fun getInstance(context: Context): AsteroidRadarDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance  = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRadarDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }

        }
    }
}