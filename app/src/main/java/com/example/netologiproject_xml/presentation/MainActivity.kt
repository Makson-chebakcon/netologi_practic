package com.example.netologiproject_xml.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.netologiproject_xml.databinding.ActivityMainBinding
import com.example.netologiproject_xml.domain.AppDataBase
import com.example.netologiproject_xml.domain.UserRepository
import com.example.netologiproject_xml.domain.UserViewModel
import com.example.netologiproject_xml.domain.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDataBase
    private lateinit var adapter: TopUsersAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDataBase.getDatabase(this)

        val userDao = database.userDao()
        val topUserDao = database.topUserDao()
        val userRepository = UserRepository(userDao, topUserDao)

        // Используем UserViewModelFactory для создания UserViewModel
        val factory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]







    }




}