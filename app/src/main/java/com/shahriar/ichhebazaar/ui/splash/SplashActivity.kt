package com.shahriar.ichhebazaar.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.ui.MainActivity
import com.shahriar.ichhebazaar.ui.login.LoginActivity
import com.shahriar.ichhebazaar.ui.slider.SliderActivity
import com.shahriar.socialmedia.datasource.DatastoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            initialLogic()
        }, 3000)

    }

    fun initialLogic() {
        val dataStoreManager = DatastoreManager(this)
        lifecycleScope.launch {
            val isLoggedIn = dataStoreManager.getBoolean("IS_LOGGED_IN", false).first()
            if (isLoggedIn == true)
                navigateToHome()
            else
                navigateToSliderActivity()
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