package com.anonymous.appilogue.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.anonymous.appilogue.features.login.LoginActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }, 3000)
    }
}
