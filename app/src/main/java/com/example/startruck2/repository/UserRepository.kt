package com.example.startruck2.repository

import com.example.startruck2.data.User
import com.example.startruck2.data.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }
}