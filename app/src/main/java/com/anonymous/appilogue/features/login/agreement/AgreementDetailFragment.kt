package com.anonymous.appilogue.features.login.agreement

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentAgreementDetailBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.login.agreement.AgreementDetailViewModel.Companion.TERMS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AgreementDetailFragment
    : BaseFragment<FragmentAgreementDetailBinding, AgreementDetailViewModel>(R.layout.fragment_agreement_detail) {

    override val viewModel: AgreementDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        bind {
            titleView.text = if (viewModel.type == TERMS) {
                getString(R.string.terms_title)
            } else {
                getString(R.string.personal_info_title)
            }
            descriptionView.apply {
                setTextFromAssets(viewModel.getDescriptionFileName())
                movementMethod = ScrollingMovementMethod()
            }

            nextButtonView.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun TextView.setTextFromAssets(fileName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val assetManager = resources.assets
            assetManager.open(fileName).use { inputStream ->
                val fileSize = inputStream.available()
                val buf = ByteArray(fileSize)
                inputStream.read(buf)

                withContext(Dispatchers.Main) {
                    text = String(buf)
                }
            }
        }
    }
}
