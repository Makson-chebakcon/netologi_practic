package com.example.netologiproject_xml.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.netologiproject_xml.databinding.FragmentStartViewBinding
import com.example.netologiproject_xml.domain.AppDataBase
import com.example.netologiproject_xml.domain.UserRepository
import com.example.netologiproject_xml.domain.UserViewModel
import com.example.netologiproject_xml.domain.UserViewModelFactory
import com.example.netologiproject_xml.domain.dto.UserDto
import com.example.netologiproject_xml.presentation.dilog.GameDifficultyFragment
import com.example.netologiproject_xml.presentation.dilog.NameDialogFragment
import com.example.netologiproject_xml.presentation.dilog.StatisticDialogFragment

class StartViewFragment : Fragment(), NameDialogFragment.OnNamePassListener {

    private val userViewModel: UserViewModel by activityViewModels()
    private var navigateToGame: Boolean = false

    private lateinit var binding: FragmentStartViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStartViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Установка OnClickListener для кнопки "Начать игру"
        binding.ButtonStartGameStartFragment.setOnClickListener {
            handleStartGameClick()
        }

        // Установка OnClickListener для кнопки статистики
        binding.ButtonStatisticStartFragment.setOnClickListener {
            val dialog = StatisticDialogFragment()
            dialog.show(parentFragmentManager, "StatisticDialog")
        }

        // Установка OnClickListener для кнопки настроек
        binding.ButtonSettingsFragment.setOnClickListener {
            navigateToGame = false
            val dialog = NameDialogFragment()
            dialog.setTargetFragment(this, 0)
            dialog.show(parentFragmentManager, "NameDialog")
        }
    }

    private fun handleStartGameClick() {
        if (userViewModel.user.value == null) {
            // Если пользователь не установлен, открываем диалог для ввода имени
            val dialog = NameDialogFragment()
            navigateToGame = true
            dialog.setTargetFragment(this, 0)
            dialog.show(parentFragmentManager, "NameDialog")
        } else {
            // Если пользователь установлен, открываем диалог с выбором сложности игры
            val dialog = GameDifficultyFragment()
            dialog.setTargetFragment(null, 0)
            dialog.show(parentFragmentManager, "GameDifficulty")
        }
    }

    override fun onNamePass(name: String) {
        userViewModel.setUser(UserDto(name = name, max_point = 0))
        if (navigateToGame) {
            val dialog = GameDifficultyFragment()
            dialog.setTargetFragment(null, 0)
            dialog.show(parentFragmentManager, "GameDifficulty")
        }
    }
}
