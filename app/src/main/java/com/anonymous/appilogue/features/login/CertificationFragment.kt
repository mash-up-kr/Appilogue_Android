package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentCertificationBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import java.util.*
import kotlin.concurrent.timer

class CertificationFragment :
    BaseFragment<FragmentCertificationBinding, LoginViewModel>(R.layout.fragment_certification) {
    override val viewModel: LoginViewModel by activityViewModels()
    private var time = 600000
    private var timerTask: Timer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startTimer()                            // 프래그먼트 도착하자 마자 타이머 시작
        with(binding) {
            with(certificationMoveNextButton) {
                isEnabled = false
                setTextColor(resources.getColor(R.color.gray_01))
                setBackgroundColor(resources.getColor(R.color.black_01))
            }

            certificationNumber1.requestFocus()
            setAddTextChangeListener()          // 포커스 자동 넘김
            setRestartClickListener()           // 재전송 눌렀을 때 다시 타이머 시작

            certificationMoveNextButton.setOnClickListener {
                // 서버의 인증 값과 동일하다면 버튼 활성화 !
//                it.findNavController()
//                    .navigate(R.id.action_certificationFragment_to_passwordFragment)
                cancelTimer()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
            certificationBackButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_certificationFragment_to_emailFragment)
            }
            certificationCloseButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_certificationFragment_to_loginFragment)
            }

        }

    }

    private fun setRestartClickListener() {
        binding.restart.setOnClickListener {
            time = 600000
            startTimer()
        }
    }

    private fun startTimer() {
        timerTask = timer(period = 1000) {
            time -= 1000
            val min = time / 60000
            val sec = (time % 60000) / 1000
            activity?.runOnUiThread {
                when (sec) {
                    in 0..9 -> binding.timer.text = "${min}:0${sec}"
                    else -> binding.timer.text = "${min}:${sec}"
                }
            }
            if (time == 0) {
                cancelTimer()
            }
        }
    }

    private fun cancelTimer() {
        timerTask?.cancel()
    }

    private fun FragmentCertificationBinding.setAddTextChangeListener() {
        val certificationNumberList =
            listOf(certificationNumber1, certificationNumber2, certificationNumber3, certificationNumber4, certificationNumber5, certificationNumber6)

        fun setAddTextChangedListener() {
            for (index in 0..4) {
                certificationNumberList[index].addTextChangedListener {
                    if (it!!.isNotEmpty()) {
                        certificationNumberList[index + 1].requestFocus()
                    }
                }
            }
            certificationNumberList[5].addTextChangedListener {
                if (it!!.isNotEmpty()) {
                    with(certificationMoveNextButton) {
                        isEnabled = true
                        context?.let { ctx ->
                            setTextColor(ContextCompat.getColor(ctx, R.color.white))
                            setBackgroundColor(ContextCompat.getColor(ctx, R.color.purple_01))
                        }
                    }
                } else {
                    with(certificationMoveNextButton) {
                        isEnabled = false
                        context?.let { ctx ->
                            setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                            setBackgroundColor(ContextCompat.getColor(ctx, R.color.black_01))
                        }
                    }
                }
            }
        }
        setAddTextChangedListener()
    }
}
