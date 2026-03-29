package com.example.startruck2.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.startruck2.data.AppDatabase
import com.example.startruck2.databinding.ActivityLoginBinding
import com.example.startruck2.repository.UserRepository
import com.example.startruck2.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Database & Repository & ViewModel
        val dao = AppDatabase.getDatabase(this).userDao()
        val repository = UserRepository(dao)
        userViewModel = UserViewModel(repository)

        // Login btn Click
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check credentials in background
            CoroutineScope(Dispatchers.IO).launch {
                val user = userViewModel.login(email, password)
                runOnUiThread {
                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        // Navigate to MainActivity or Dashboard here
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}