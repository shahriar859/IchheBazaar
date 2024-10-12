package com.shahriar.ichhebazaar.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.databinding.ActivityLoginBinding
import com.shahriar.ichhebazaar.ui.MainActivity
import com.shahriar.ichhebazaar.ui.register.RegistrationActivity
import com.shahriar.ichhebazaar.utils.Utility.isValidEmail
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Setup text listeners for email and password input
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (!isValidEmail(email)) {
                    binding.emailInputLayout.error = "Please enter a valid email"
                    binding.emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    binding.emailInputLayout.error = null
                    binding.emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                    viewModel.onEmailChanged(email)
                }
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.isEmpty()) {
                    binding.passwordInputLayout.error = "Password is required"
                    binding.passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else if (password.length < 6) {
                    binding.passwordInputLayout.error = "Password must be at least 6 characters"
                    binding.passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    binding.passwordInputLayout.error = null
                    binding.passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                    viewModel.onPasswordChanged(password)
                }
            }
        })

        observeViewModel()

        // Login button click listener
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty() || password.length < 6) {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.userLogin(this)
            }
        }

        // Registration text click listener
        binding.register.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Set initial text for the email and password fields
        binding.emailEditText.setText(viewModel.emailStateFlow.value)
        binding.passwordEditText.setText(viewModel.passwordStateFlow.value)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginResponse.collect { response ->
                if (response != null) {
                    navigateToHome()
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
