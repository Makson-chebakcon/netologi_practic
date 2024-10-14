package com.example.netologiproject_xml.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.netologiproject_xml.domain.dao.TopUserDao
import com.example.netologiproject_xml.domain.dao.UserDao
import com.example.netologiproject_xml.domain.dto.TopUsersDto
import com.example.netologiproject_xml.domain.dto.UserDto

@Database(entities = [UserDto::class, TopUsersDto::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun topUserDao() : TopUserDao


    companion object{
        @Volatile
        private var INSTANCE:AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance

            }
        }
    }
}