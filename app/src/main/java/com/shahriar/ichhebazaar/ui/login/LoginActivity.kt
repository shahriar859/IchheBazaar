package com.shahriar.ichhebazaar.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.ui.MainActivity
import com.shahriar.ichhebazaar.ui.MainViewModel
import com.shahriar.socialmedia.datasource.DatastoreManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (!isValidEmail(email)) {
                    emailInputLayout.error = "Please enter a valid email"
                    emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    emailInputLayout.error = null
                    emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.isEmpty()) {
                    passwordInputLayout.error = "Password is required"
                    passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else if (password.length < 6) {
                    passwordInputLayout.error = "Password must be at least 6 characters"
                    passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    passwordInputLayout.error = null
                    passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty() || password.length < 6) {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            } else {
                // API call
                viewModel.userLogin(password, email)
                lifecycleScope.launch {
                    viewModel.loginResponse.collect { response ->
                        if (response != null) {
                            Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                            saveCredentials()
                            navigateToHome()
                        }else{
                            Toast.makeText(this@LoginActivity, "Login failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }

    }

    suspend fun saveCredentials() {
        val dataStoreManager = DatastoreManager(this)
        dataStoreManager.saveBoolean("IS_LOGGED_IN", true)
    }

    fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}