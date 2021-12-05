package com.anonymous.appilogue.features.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentReviewRegisterBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.main.MainActivity
import com.anonymous.appilogue.utils.showToast
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewRegisterFragment
    : BaseFragment<FragmentReviewRegisterBinding, ReviewRegisterViewModel>(R.layout.fragment_review_register) {

    override val viewModel: ReviewRegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            vm = viewModel
            mainVm = (activity as MainActivity).viewModel
        }

        uploadAppIcon()
        initView()
    }

    private fun uploadAppIcon() {
        binding.mainVm?.selectedApp?.let {
            viewModel.uploadAppIcon(requireContext().cacheDir, it.icon)
        }
    }

    private fun initView() {
        bind {
            hashtagInputView.textChanges()
                .subscribe { text ->
                    if (hashtagsFlexLayout.childCount <= MAX_HASHTAG_COUNT) {
                        hashtagInputView.visibility = View.VISIBLE
                        if (text.contains(" ")) {
                            hashtagsFlexLayout.addNewHashtag(text.toString())
                            hashtagInputView.text.clear()
                        }
                    } else {
                        hashtagInputView.visibility = View.GONE
                    }
                }

            selectButton.setOnClickListener {
                val hashtags = mutableListOf<String>()
                hashtagsFlexLayout.children.iterator().forEach { chip ->
                    if (chip is Chip) {
                        hashtags.add(chip.text.toString())
                    }
                }
                val appName = mainVm?.selectedApp?.name ?: return@setOnClickListener
                viewModel.registerReview(
                    appName = appName,
                    hashtags = hashtags
                ) {
                    context?.showToast("success")
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun FlexboxLayout.addNewHashtag(hashtag: String) {
        val chip = (LayoutInflater.from(context).inflate(R.layout.view_hashtag, null) as Chip).apply {
            text = resources.getString(R.string.hashtag_text_format, hashtag)
            setTextAppearanceResource(R.style.TextStyle_Chip)
        }
        val layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            rightMargin = resources.getDimensionPixelSize(R.dimen.hashtag_margin_end)
        }
        chip.setOnCloseIconClickListener {
            removeView(chip)
            binding.hashtagInputView.visibility = View.VISIBLE
        }
        this.addView(chip, childCount - 1, layoutParams)
    }

    companion object {
        private const val MAX_HASHTAG_COUNT = 5
    }
}