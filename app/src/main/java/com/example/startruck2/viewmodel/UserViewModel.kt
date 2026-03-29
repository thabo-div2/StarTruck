package com.example.startruck2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startruck2.data.User
import com.example.startruck2.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    fun registerUser(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    suspend fun login(email: String, password: String): User? {
        return repository.login(email, password)
    }
}