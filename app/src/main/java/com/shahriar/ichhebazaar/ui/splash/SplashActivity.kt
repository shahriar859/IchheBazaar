package com.shahriar.ichhebazaar.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.ui.MainActivity
import com.shahriar.ichhebazaar.ui.slider.SliderActivity
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        initialLogic()
    }

    fun initialLogic() {
        Handler(Looper.getMainLooper()).postDelayed({
            checkLogin()
        }, 3000)
    }

    fun checkLogin() {
        lifecycleScope.launch {
            viewModel.tokenFlow.collect { token ->
                if (token != null) {
                    navigateToHome()
                } else {
                    navigateToSliderActivity()
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSliderActivity() {
        val intent = Intent(this, SliderActivity::class.java)
        startActivity(intent)
        finish()
    }

}