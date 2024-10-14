package com.example.netologiproject_xml.presentation.dilog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.netologiproject_xml.R
import com.example.netologiproject_xml.databinding.FragmentGameDifficultyBinding
import com.example.netologiproject_xml.domain.AppDataBase
import com.example.netologiproject_xml.domain.UserViewModel
import com.example.netologiproject_xml.domain.UserViewModelFactory
import com.example.netologiproject_xml.domain.UserRepository

class GameDifficultyFragment : DialogFragment() {

    private lateinit var binding: FragmentGameDifficultyBinding

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDifficultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonDifficulty.setOnClickListener {
                val count = countMouseBar.progress.takeIf { it != 0 } ?: 1
                val speed = seekBar3.progress.takeIf { it != 0 } ?: 1

                userViewModel.setCount(count)
                userViewModel.setSpeed(speed)

                Log.d("GameDifficultyFragment", "Count: $count, Speed: $speed")

                findNavController().navigate(R.id.gameViewFragment)
                dismiss()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameDifficultyFragment()
    }
}
