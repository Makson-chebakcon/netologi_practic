package com.example.netologiproject_xml.presentation.dilog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netologiproject_xml.R
import com.example.netologiproject_xml.domain.AppDataBase
import com.example.netologiproject_xml.domain.UserRepository
import com.example.netologiproject_xml.domain.dto.TopUsersDto
import com.example.netologiproject_xml.presentation.TopUsersAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticDialogFragment : DialogFragment() {

    private lateinit var adapter: TopUsersAdapter
    private var topUsersList: List<TopUsersDto> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistic_dilog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.dialogRecyclerView)

        adapter = TopUsersAdapter(topUsersList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()
    }

    private fun loadData() {
        val userDao = AppDataBase.getDatabase(requireContext()).userDao()
        val topUserDao = AppDataBase.getDatabase(requireContext()).topUserDao()
        val userRepository = UserRepository(userDao, topUserDao)

        CoroutineScope(Dispatchers.IO).launch {
            try {

                topUsersList = userRepository.getAllTopUsers() // Получите данные из репозитория
                Log.e("s","${topUsersList[topUsersList.size-1]}")
                withContext(Dispatchers.Main) {
                    adapter.updateData(topUsersList) // Метод для обновления данных в адаптере
                }
            } catch (e: Exception) {

            }
        }
    }

}
