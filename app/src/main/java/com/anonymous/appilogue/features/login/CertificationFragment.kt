package com.anonymous.appilogue.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_certification, container, false)
    }

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
        certificationNumber1.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                certificationNumber2.requestFocus()
            }
        }
        certificationNumber2.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                certificationNumber3.requestFocus()
            }
        }
        certificationNumber3.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                certificationNumber4.requestFocus()
            }
        }
        certificationNumber4.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                certificationNumber5.requestFocus()
            }
        }
        certificationNumber5.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                certificationNumber6.requestFocus()
            }
        }
        certificationNumber6.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                with(certificationMoveNextButton) {
                    isEnabled = true
                    setTextColor(resources.getColor(R.color.white))
                    setBackgroundColor(resources.getColor(R.color.purple_01))
                }
            } else {
                with(certificationMoveNextButton) {
                    isEnabled = false
                    setTextColor(resources.getColor(R.color.gray_01))
                    setBackgroundColor(resources.getColor(R.color.black_01))
                }
            }
        }
    }
}
