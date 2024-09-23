package com.shahriar.ichhebazaar.ui.register

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
import com.shahriar.ichhebazaar.ui.MainViewModel
import com.shahriar.ichhebazaar.ui.login.LoginActivity
import com.shahriar.ichhebazaar.utils.Utility.isValidEmail
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        val nameInputLayout = findViewById<TextInputLayout>(R.id.nameInputLayout)
        val nameEditText = findViewById<TextInputEditText>(R.id.nameEditText)
        val phoneInputLayout = findViewById<TextInputLayout>(R.id.phoneInputLayout)
        val phoneEditText = findViewById<TextInputEditText>(R.id.phoneEditText)
        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val name = s.toString()
                if (name.isEmpty()) {
                    nameInputLayout.error = "Please enter your name"
                    nameInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    nameInputLayout.error = null
                    nameInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val phone = s.toString()
                if (phone.isEmpty() || !phone.startsWith("+880")) {
                    phoneInputLayout.error = "Phone number must start with +880"
                    phoneInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else if (phone.length < 14) {
                    phoneInputLayout.error = "Phone number is too short"
                    phoneInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    phoneInputLayout.error = null
                    phoneInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (!isValidEmail(email)) {
                    emailInputLayout.error = "Invalid email address"
                    emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    emailInputLayout.error = null
                    emailInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.length < 6) {
                    passwordInputLayout.error = "Password must be at least 6 characters"
                    passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.red))
                } else {
                    passwordInputLayout.error = null
                    passwordInputLayout.setBoxStrokeColor(resources.getColor(R.color.green))
                }
            }
        })

        observeViewModel()

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

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

        val login = findViewById<TextView>(R.id.login)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //finish()
        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.registrationResponse.collect { response ->
                if(response != null) {
                    if(response.status == 200) {
                        navigateToLogin()
                    }else{
                        Toast.makeText(this@RegistrationActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}