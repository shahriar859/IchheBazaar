package com.shahriar.ichhebazaar.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.databinding.ActivityRegistrationBinding
import com.shahriar.ichhebazaar.ui.login.LoginActivity
import com.shahriar.ichhebazaar.utils.Utility.isValidEmail
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        // Set up TextWatchers for validation
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val name = s.toString()
                if (name.isEmpty()) {
                    binding.nameInputLayout.error = "Please enter your name"
                    binding.nameInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    binding.nameInputLayout.error = null
                    binding.nameInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val phone = s.toString()
                if (phone.isEmpty() || !phone.startsWith("+880")) {
                    binding.phoneInputLayout.error = "Phone number must start with +880"
                    binding.phoneInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else if (phone.length < 14) {
                    binding.phoneInputLayout.error = "Phone number is too short"
                    binding.phoneInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    binding.phoneInputLayout.error = null
                    binding.phoneInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (!isValidEmail(email)) {
                    binding.emailInputLayout.error = "Invalid email address"
                    binding.emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    binding.emailInputLayout.error = null
                    binding.emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.length < 6) {
                    binding.passwordInputLayout.error = "Password must be at least 6 characters"
                    binding.passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    binding.passwordInputLayout.error = null
                    binding.passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        // Observe the ViewModel
        observeViewModel()

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else if (phone.isEmpty() || !phone.startsWith("+880") || phone.length < 14) {
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                // API call
                viewModel.registerProfile(name, phone, email, password, this)
            }
        }

        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.registrationResponse.collect { response ->
                if(response != null) {
                    if(response.status == 200) {
                        navigateToLogin()
                    } else {
                        Toast.makeText(this@RegistrationActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
