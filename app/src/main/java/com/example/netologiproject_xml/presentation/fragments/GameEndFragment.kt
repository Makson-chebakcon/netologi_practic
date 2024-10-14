package com.example.netologiproject_xml.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netologiproject_xml.R
import com.example.netologiproject_xml.databinding.FragmentGameEndBinding
import com.example.netologiproject_xml.domain.UserViewModel
import com.example.netologiproject_xml.domain.dto.TopUsersDto
import com.example.netologiproject_xml.presentation.dilog.StatisticDialogFragment
import kotlinx.coroutines.launch


class GameEndFragment : Fragment() {

    private lateinit var binding: FragmentGameEndBinding
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameEndBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pointFragment.text = userViewModel.user.value?.max_point.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.setTopUser(
                TopUsersDto(
                    game_id = 0, // Если game_id автоинкрементное, просто оставьте 0
                    name = userViewModel.user.value?.name.toString(),
                    point = userViewModel.user.value?.max_point!!.toInt()
                )
            )

            // Здесь получаем список и выполняем сортировку
            val topUserList: List<TopUsersDto>? = userViewModel.topUser?.value // Используйте suspend-функцию
            if (topUserList?.size!! > 10) {
                userViewModel.delTopUser(topUserList?.get(10)!!)
            }
        }

        binding.ButtonGoToHomeFragment.setOnClickListener {
            findNavController().navigate(R.id.startViewFragment)
        }

        binding.ButtonStatisticFragment.setOnClickListener {
            val dialog = StatisticDialogFragment()
            dialog.show(parentFragmentManager, "StatisticDialog")
        }

        binding.ButtonOneMoreTimeFragment.setOnClickListener {
            findNavController().navigate(R.id.gameViewFragment)
        }



    }

    companion object {


        @JvmStatic
        fun newInstance() = GameEndFragment()

    }
}