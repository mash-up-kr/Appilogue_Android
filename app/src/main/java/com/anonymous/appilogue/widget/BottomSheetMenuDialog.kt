package com.anonymous.appilogue.widget

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentBottomSheetMenuDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetMenuDialog(
    private val isOwner: Boolean,
    private val onMenuClicked: () -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetMenuDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(
            STYLE_NORMAL,
            R.style.TransparentBottomSheetDialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bottom_sheet_menu_dialog,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)

        binding.apply {
            menuTextView.apply {
                text = if (isOwner) {
                    resources.getString(R.string.menu_text_delete)
                } else {
                    resources.getString(R.string.menu_text_report)
                }
                setOnClickListener {
                    onMenuClicked()
                    dismiss()
                }
            }

            menuCancelView.setOnClickListener {
                dismiss()
            }
        }
    }
}