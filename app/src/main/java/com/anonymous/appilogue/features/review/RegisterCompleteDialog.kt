package com.anonymous.appilogue.features.review

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.databinding.DataBindingUtil
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.DialogReviewRegisterBinding
import com.anonymous.appilogue.utils.handleSelectEvent

class RegisterCompleteDialog (
    context: Context,
    private val isBlackHole: Boolean
) : Dialog(context) {

    private lateinit var binding: DialogReviewRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_review_register, null, false)
        setContentView(binding.root)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val width = (context.resources.displayMetrics.widthPixels * 0.8).toInt()
            setLayout(width, WRAP_CONTENT)
        }

        initView()
    }

    private fun initView() {
        initTitleView()

        with(binding) {
            buttonView.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun initTitleView() {
        val holeTextRes = if (isBlackHole) {
            R.string.black_hole_text
        } else {
            R.string.white_hole_text
        }
        val holeText = context.getString(holeTextRes)
        val titleText = context.getString(R.string.review_register_complete_title, holeText)
        val titleSpanColor = if (isBlackHole) {
            R.color.purple_01
        } else {
            R.color.mint
        }
        val subTitleText = context.getString(R.string.review_register_complete_sub_title, holeText)

        with(binding) {
            titleView.handleSelectEvent(titleText, holeTextRes, titleSpanColor)
            subTitleView.text = subTitleText
        }
    }
}