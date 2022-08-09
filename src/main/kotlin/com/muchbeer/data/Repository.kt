package com.muchbeer.data

interface Repository {

    suspend fun insertUser(mUser: User) : User?
    suspend fun retrieveAllUsers() : List<User>
}