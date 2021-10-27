package com.anonymous.appilogue.features.login

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

class CertificationFragment :
    BaseFragment<FragmentCertificationBinding, LoginViewModel>(R.layout.fragment_certification) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        with(binding) {
            with(certificationMoveNextButton) {
                isEnabled = false
                // 처음 Fragment 시작시에는 비활성화
                context?.let { ctx ->
                    setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                    setBackgroundColor(ContextCompat.getColor(ctx, R.color.black_01))
                }
            }
            // 포커스 자동 넘김
            setAddTextChangeListener()

            certificationMoveNextButton.setOnClickListener {
                viewModel?.stopTimer()
                it.findNavController().navigate(R.id.action_certificationFragment_to_passwordFragment)
            }
            certificationBackButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_certificationFragment_to_emailFragment)
            }
            certificationCloseButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_certificationFragment_to_loginFragment)
            }

        }

    }

    private fun FragmentCertificationBinding.setAddTextChangeListener() {
        listOf(
            certificationNumber1, certificationNumber2,
            certificationNumber3, certificationNumber4,
            certificationNumber5, certificationNumber6
        ).setFocusAndChangeButtonState(focusNext(), certificationCheck())
    }

    private fun <S> Iterable<S>.setFocusAndChangeButtonState(operation: (S, S) -> Unit, lastOperation: (S) -> Unit) {
        var iterator = this.iterator()
        if (!iterator.hasNext()) throw UnsupportedOperationException("EmptyList can't be reduced")

        var value: S = iterator.next()
        var nextValue: S = value

        while (iterator.hasNext()) {
            nextValue = iterator.next()
            operation(value, nextValue)
            value = nextValue
        }
        lastOperation(nextValue)
    }

    private fun focusNext(): (EditText, EditText) -> Unit = { editText1, editText2 ->
        editText1.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                editText2.requestFocus()
                if (checkAllData()) {
                    buttonClickEnable()
                }
            } else {
                buttonClickUnEnable()
            }
        }
    }

    private fun certificationCheck(): (EditText) -> Unit = { it ->
        it.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                buttonClickEnable()
            } else {
                buttonClickUnEnable()
            }
        }
    }

    private fun checkAllData(): Boolean {
        with(binding) {
            listOf(
                certificationNumber1, certificationNumber2, certificationNumber3,
                certificationNumber4, certificationNumber5, certificationNumber6
            )
        }.forEach { if (it.text.toString().isEmpty()) return false }

        return true
    }

    private fun buttonClickEnable() {
        with(binding.certificationMoveNextButton) {
            isEnabled = true
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.white))
                background = ContextCompat.getDrawable(ctx, R.drawable.border_radius_12_purple)
                background.setTint(ContextCompat.getColor(ctx, R.color.purple_01))
            }
        }
    }

    private fun buttonClickUnEnable() {
        with(binding.certificationMoveNextButton) {
            isEnabled = false
            context?.let { ctx ->
                setTextColor(ContextCompat.getColor(ctx, R.color.gray_01))
                background.setTint(ContextCompat.getColor(ctx, R.color.gray_02))
            }
        }
    }
}
