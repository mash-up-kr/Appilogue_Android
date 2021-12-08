package com.anonymous.appilogue.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anonymous.appilogue.features.login.LoginActivity
import com.anonymous.appilogue.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.fetchMyInformation(
                {
                    Intent(this@SplashActivity, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                },
                {
                    Intent(this@SplashActivity, LoginActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
            )
        }
    }
}
