package com.shahriar.ichhebazaar.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.ui.MainActivity
import com.shahriar.ichhebazaar.ui.MainViewModel
import com.shahriar.ichhebazaar.utils.Utility.isValidEmail
import com.shahriar.ichhebazaar.datasource.DatastoreManager
import com.shahriar.ichhebazaar.ui.register.RegistrationActivity
import com.shahriar.ichhebazaar.ui.splash.SplashViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
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
                    viewModel.onEmailChanged(email)
                }
            }
        })

        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        passwordEditText = findViewById(R.id.passwordEditText)
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
                    viewModel.onPasswordChanged(password)
                }
            }
        })

        observeViewModel()

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
                viewModel.userLogin(this)
            }
        }

        val registration = findViewById<TextView>(R.id.register)
        registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            //finish()
        }

    }

    override fun onStart() {
        super.onStart()
        // Set initial text for the email and password fields
        emailEditText.setText(viewModel.emailStateFlow.value)
        passwordEditText.setText(viewModel.passwordStateFlow.value)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginResponse.collect { response ->
                if(response != null) {
                    navigateToHome()
                }
            }
        }
    }

    fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}