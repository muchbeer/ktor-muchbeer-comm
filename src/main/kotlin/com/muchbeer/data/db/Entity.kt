package com.muchbeer.data.db

import org.jetbrains.exposed.sql.Table

object UserTable : Table("user") {
    val email = varchar("email", 512)
    val userName = varchar("userName", 200)
    val name = varchar("name", 300)
    val hashPassword = varchar("hashPassword", 512)
}