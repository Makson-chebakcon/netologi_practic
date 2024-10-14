package com.example.netologiproject_xml.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netologiproject_xml.domain.dto.TopUsersDto
import com.example.netologiproject_xml.domain.dto.UserDto
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<UserDto?>()
    private val _topUser = MutableLiveData<List<TopUsersDto>>(emptyList())
    private val _speedMouse = MutableLiveData<Int>()
    private val _countMouse = MutableLiveData<Int>()

    val user: LiveData<UserDto?> = _user
    val topUser: LiveData<List<TopUsersDto>> = _topUser
    val speedMouse: LiveData<Int> = _speedMouse
    val countMouse: LiveData<Int> = _countMouse

    init {
        loadUser()
        loadTopUsers()
    }

    fun setCount(count: Int) {
        _countMouse.value = count
        Log.d("UserViewModel", "Count set to: $count")
    }

    fun setSpeed(speed: Int) {
        _speedMouse.value = speed
        Log.d("UserViewModel", "Speed set to: $speed")
    }

    fun setUser(newUser: UserDto) {
        _user.value = newUser
        viewModelScope.launch {
            try {
                userRepository.insertUser(newUser) // Вставляем пользователя в БД
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error inserting user", e)
            }
        }
    }

    fun updateUser(name: String = user.value?.name.orEmpty(), max_point: Int = 0) {
        _user.value?.let {
            val updatedUser = it.copy(name = name, max_point = max_point)
            _user.value = updatedUser
            viewModelScope.launch {
                try {
                    userRepository.updateUser(updatedUser) // Обновляем пользователя в БД
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error updating user", e)
                }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getUser() // Загружаем пользователя из БД
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading user", e)
            }
        }
    }

    fun setTopUser(topUser: TopUsersDto) {
        _topUser.value = _topUser.value?.plus(topUser)
        viewModelScope.launch {
            try {
                userRepository.insertTopUser(topUser) // Вставляем топ-пользователя в БД
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error inserting top user", e)
            }
        }
    }

    private fun loadTopUsers() {
        viewModelScope.launch {
            try {
                _topUser.value = userRepository.getAllTopUsers()
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error getting all top users", e) // Логирование ошибки
            }
        }
    }

    fun delTopUser(topUser: TopUsersDto) {
        viewModelScope.launch {
            try {
                userRepository.delTopUser(topUser) // Удаляем топ-пользователя из БД
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error deleting top user", e)
            }
        }
    }
}
