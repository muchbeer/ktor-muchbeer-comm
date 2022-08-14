package com.muchbeer.data.db

import com.muchbeer.data.*
import com.muchbeer.data.db.AccBalanceTable.accBalance
import com.muchbeer.data.db.AccBalanceTable.accountEmail
import com.muchbeer.data.db.AccBalanceTable.deductBalance
import com.muchbeer.data.db.AirtimeTable.airtimeEmail
import com.muchbeer.data.db.AirtimeTable.airtimeStatus
import com.muchbeer.data.db.AirtimeTable.amount
import com.muchbeer.data.db.AirtimeTable.discount
import com.muchbeer.data.db.AirtimeTable.errorMessage
import com.muchbeer.data.db.AirtimeTable.phoneNumber
import com.muchbeer.data.db.AirtimeTable.requestId
import com.muchbeer.data.db.SMSTable.smsCost
import com.muchbeer.data.db.SMSTable.smsEmail
import com.muchbeer.data.db.SMSTable.smsMessage
import com.muchbeer.data.db.SMSTable.smsMessageId
import com.muchbeer.data.db.SMSTable.smsMessageParts
import com.muchbeer.data.db.SMSTable.smsNumber
import com.muchbeer.data.db.SMSTable.smsStatus
import com.muchbeer.data.db.SMSTable.smsStatusCode
import com.muchbeer.data.db.SenderIDTable.senderEmail
import com.muchbeer.data.db.SenderIDTable.senderPK
import com.muchbeer.data.db.SenderIDTable.sidStatus
import com.muchbeer.data.db.UserTable.hashPassword
import com.muchbeer.data.db.UserTable.name
import com.muchbeer.data.db.UserTable.userEmail
import com.muchbeer.data.db.UserTable.userName
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){
        synchronized(this) {
            val hikari2 = DatabaseConnect.getDataSource()
            Database.connect(hikari2)
            transaction {
                SchemaUtils.create(UserTable)
            }
        }

    }


  @Synchronized   fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("JDBC_DRIVER") // 1
        config.jdbcUrl = System.getenv("DATABASE_URL_TAP") // 2
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }


}
suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }

fun mapToUserModel(rowItem: ResultRow) = User(
    email = rowItem[userEmail],
    userName = rowItem[userName],
    hashPassword = rowItem[hashPassword],
    name = rowItem[name]
)

fun mapToSMSModel(rowItem: ResultRow) = SMSRecipient(
    email = rowItem[smsEmail],
    message = rowItem[smsMessage],
    messageId = rowItem[smsMessageId],
    messageParts = rowItem[smsMessageParts],
    number = rowItem[smsNumber],
    cost = rowItem[smsCost],
    status = rowItem[smsStatus],
    statusCode = rowItem[smsStatusCode]
)

fun mapToAirtime(rowItem: ResultRow) = Airtime (
    email = rowItem[airtimeEmail],
    amount = rowItem[amount],
    discount = rowItem[discount],
    errorMessage = rowItem[errorMessage],
    phoneNumber = rowItem[phoneNumber],
    requestId = rowItem[requestId],
    status = rowItem[airtimeStatus]
        )

fun mapToSenderID(rowItem: ResultRow) = SenderID (
    senderPK = rowItem[senderPK],
    senderEmail = rowItem[senderEmail],
    senderName = rowItem[senderEmail],
    sidStatus = rowItem[sidStatus]
        )

fun mapToAccBalance(rowItem: ResultRow) = AccountBalance(
    accountEmail = rowItem[accountEmail],
    accBalance = rowItem[accBalance],
    deductBalance = rowItem[deductBalance]
)