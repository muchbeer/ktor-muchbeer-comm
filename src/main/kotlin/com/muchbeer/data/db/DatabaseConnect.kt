package com.muchbeer.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class DatabaseConnect {


    companion object{
        private var INSTANCE : HikariDataSource? = null

        fun getDataSource() : HikariDataSource {
            val config = HikariConfig()
            config.driverClassName = System.getenv("JDBC_DRIVER") // 1
            config.jdbcUrl = System.getenv("DATABASE_URL_TAP") // 2
            config.maximumPoolSize = 3
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()

            return INSTANCE ?: synchronized(this) {
                HikariDataSource(config).also { receivInstance ->
                    INSTANCE = receivInstance
                }
            }

        }
    }
}