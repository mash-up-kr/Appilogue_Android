package com.anonymous.coffin.features.main

import android.os.Bundle
import com.anonymous.coffin.R
import com.anonymous.coffin.databinding.ActivityMainBinding
import com.anonymous.coffin.features.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}