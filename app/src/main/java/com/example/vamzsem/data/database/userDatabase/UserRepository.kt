package com.example.vamzsem


import kotlinx.coroutines.flow.Flow

class UserRepository(private val dao: UserDao) {
    suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        dao.deleteUser(user)
    }

    fun getUserById(userId: Int): Flow<User> {
        return dao.getUserById(userId)
    }
}
