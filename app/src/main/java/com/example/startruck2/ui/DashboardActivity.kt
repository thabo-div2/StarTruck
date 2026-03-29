package com.example.startruck2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.startruck2.R
import com.example.startruck2.data.AppDatabase
import com.example.startruck2.utils.SessionManager
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity {

    private lateinit var db: AppDatabase
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        db = AppDatabase.getDatabase(this)
        session = SessionManager(this)

        val txtUser = findViewById<TextView>(R.id.txtUser)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val userId = session.getUserId()

        // Check login session
        if (userId == -1) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        lifecycleScope.launch {
            val user = db.userDao().getUserById(userId)
            txtUser.text = "Welcome, ${user?.username ?: "User"}"
        }

        // Logout
        btnLogout.setOnClickListener {
            session.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}