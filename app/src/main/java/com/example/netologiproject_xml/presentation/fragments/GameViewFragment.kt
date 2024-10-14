package com.example.netologiproject_xml.presentation.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netologiproject_xml.R
import com.example.netologiproject_xml.databinding.FragmentGameViewBinding
import com.example.netologiproject_xml.domain.UserViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewFragment : Fragment() {
    private lateinit var binding: FragmentGameViewBinding
    private val mouseList: MutableList<Int> = mutableListOf()
    private val userViewModel: UserViewModel by activityViewModels()
    private var pointUser: Int = 0
    private var speed: Int = 0
    private var countMouse: Int = 0
    private var speedMouse: Float = (0).toFloat()
    private lateinit var countdownTimer: CountDownTimer
    private val totalTime = 10000L // Время в миллисекундах (например, 10 секунд)
    private val interval = 100L
    private var job: Job? = null // Добавлено для управления корутинами

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        speed = userViewModel.speedMouse.value?.toInt()!!
        countMouse = userViewModel.countMouse.value?.toInt()!!

        binding.userPoint.text = "Твои очки: $pointUser"
        speedMouse = (1f / (speed + 1))

        Log.e("Speed","$speedMouse")
        Log.e("Count", "$countMouse")

        // Генерация мышей
        repeat(countMouse) {
            constructorMouse()
        }

        binding.progressBar.max = (totalTime / interval).toInt()
        startTimer()
    }

    private fun constructorMouse() {
        val mouse = ImageButton(requireContext())

        binding.apply {
            gameView.post {
                val viewWidth = gameView.width
                val viewHeight = gameView.height
                val randomX = (0 until viewWidth).random()
                val randomY = (0 until viewHeight).random()


                val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                params.leftMargin = randomX - 48
                params.topMargin = randomY - 48
                mouse.layoutParams = params
                mouse.background = ContextCompat.getDrawable(requireContext(), R.drawable.mouse)

                gameView.addView(mouse)
                mouseList.add(mouse.id)

                job = lifecycleScope.launch {
                    animateMouse(mouse, randomX.toFloat(), randomY.toFloat())
                }

                mouse.setOnClickListener {
                    gameView.removeView(mouse)
                    mouseList.remove(mouse.id)
                    pointUser += 10
                    binding.userPoint.text = "Твои очки: $pointUser"
                    constructorMouse()
                }
            }
        }
    }

    private suspend fun animateMouse(mouse: ImageButton, x: Float, y: Float) {
        val viewWidth = binding.gameView.width
        val viewHeight = binding.gameView.height

        mouse.x = x
        mouse.y = y
        val randomX: Int = (0 until viewWidth).random()
        val randomY: Int = (0 until viewHeight).random()

        val animX = ObjectAnimator.ofFloat(mouse, "x", randomX.toFloat())
        val animY = ObjectAnimator.ofFloat(mouse, "y", randomY.toFloat())
        animX.duration = (10000 * speedMouse).toLong()
        animY.duration = (10000 * speedMouse).toLong()

        animX.start()
        animY.start()

        // Ожидание завершения анимации
        animX.doOnEnd {
            // Запускаем новую корутину для продолжения анимации
            lifecycleScope.launch {
                animateMouse(mouse, randomX.toFloat(), randomY.toFloat())
            }
        }
    }


    private fun startTimer() {
        countdownTimer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((totalTime - millisUntilFinished) / interval).toInt()
                binding.progressBar.progress = progress
            }

            override fun onFinish() {
                userViewModel.updateUser(max_point = pointUser)
                findNavController().navigate(R.id.gameEndFragment)
            }
        }

        countdownTimer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countdownTimer.cancel() // Останавливаем таймер при уничтожении фрагмента
        job?.cancel() // Отменяем все корутины
        mouseList.forEach { mouseId ->
            val mouseView = binding.gameView.findViewById<Button>(mouseId)
            mouseView?.let { view ->
                view.animate().cancel() // Остановить анимации для всех мышей
                binding.gameView.removeView(view) // Удалить мышей из представления
            }
        }
    }
}
