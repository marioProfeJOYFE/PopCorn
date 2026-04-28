package com.mrh.popcorn.data.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieLocal::class], version = 1, exportSchema = false)
abstract class PopCornDatabase : RoomDatabase() {

    // Aquí van los DAO que creemos

    abstract fun movieDao(): MovieDao


    // FIN DAOs

    companion object {
        @Volatile
        private var INSTANCE: PopCornDatabase? = null

        fun getDatabase(context: Context): PopCornDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PopCornDatabase::class.java,
                    "popcorn_database"
                ).build()
                INSTANCE = instance
                //Equivalente a poner return, solo nombre de variable
                instance
            }
        }

    }

}