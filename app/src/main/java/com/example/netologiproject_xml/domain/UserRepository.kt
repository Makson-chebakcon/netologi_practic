package com.example.netologiproject_xml.domain

import android.util.Log
import com.example.netologiproject_xml.domain.dao.TopUserDao
import com.example.netologiproject_xml.domain.dao.UserDao
import com.example.netologiproject_xml.domain.dto.TopUsersDto
import com.example.netologiproject_xml.domain.dto.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao, private val topUserDao: TopUserDao) {

    // Вставка пользователя
    suspend fun insertUser(user: UserDto) {
        userDao.insert(user)
    }

    // Получение пользователя
    suspend fun getUser(): UserDto? {
        return try {
            withContext(Dispatchers.IO) {
                userDao.getUser()
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error getting user", e)
            null
        }
    }

    // Обновление пользователя
    suspend fun updateUser(user: UserDto) {
        userDao.update(user)
    }

    // Вставка топ пользователя
    suspend fun insertTopUser(topUser: TopUsersDto) {
        try {
            withContext(Dispatchers.IO) {
                topUserDao.insert(topUser)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error inserting top user", e)
        }
    }

    // Получение всех топ пользователей
    suspend fun getAllTopUsers(): List<TopUsersDto> {
        return withContext(Dispatchers.IO) {
            topUserDao.getAllUsers()
        }
    }


    // Удаление топ пользователя
    suspend fun delTopUser(topUser: TopUsersDto) {
        try {
            topUserDao.delete(topUser)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error deleting top user", e)
        }
    }
}
