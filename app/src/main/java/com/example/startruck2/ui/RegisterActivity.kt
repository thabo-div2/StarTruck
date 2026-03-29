package com.example.startruck2.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.startruck2.data.AppDatabase
import com.example.startruck2.data.User
import com.example.startruck2.repository.UserRepository
import com.example.startruck2.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(this).userDao()
        val repository = UserRepository(dao)
        userViewModel = UserViewModel(repository)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val existingUser = repository.login(email, password)
                if (existingUser != null) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "User already exists", Toast.LENGTH_LONG).show()

                    }
                } else {
                    val user = User(username = username, email = email, password = password)
                    userViewModel.registerUser(user)
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "User Registered", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}