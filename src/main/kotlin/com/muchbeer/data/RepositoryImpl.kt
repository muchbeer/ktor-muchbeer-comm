package com.muchbeer.data

import com.muchbeer.data.db.UserTable
import com.muchbeer.data.db.UserTable.email
import com.muchbeer.data.db.UserTable.hashPassword
import com.muchbeer.data.db.UserTable.name
import com.muchbeer.data.db.UserTable.userName
import com.muchbeer.data.db.dbQuery
import com.muchbeer.data.db.mapToUserModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class RepositoryImpl : Repository {
    override suspend fun insertUser(mUser: User): User? {
    val t =    dbQuery {
            UserTable.insert { user ->
                user[email] = mUser.email
                user[userName] = mUser.userName
                user[name] = mUser.name
                user[hashPassword] = mUser.hashPassword
            }
        }
     return   if (t.insertedCount > 0) mUser else null
    }

    override suspend fun retrieveAllUsers(): List<User> {
        val listUser =  dbQuery {
            UserTable.selectAll().map { userRow->
                mapToUserModel(userRow)
            }
        }
        return listUser
    }


}