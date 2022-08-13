package com.muchbeer.data.db

import org.jetbrains.exposed.sql.Table

object UserTable : Table("user") {
    val userEmail = varchar("email", 512)
    val userName = varchar("userName", 200)
    val name = varchar("name", 300)
    val hashPassword = varchar("hashPassword", 512)

    override val primaryKey = PrimaryKey(userEmail)
}

object SMSTable : Table("sms") {
    val smsEmail = varchar("email", 200)
    val smsMessage = varchar("message", 700)
    val smsCost = varchar("cost", 100)
    val smsMessageId = varchar("messageId", 200)
    val smsMessageParts = integer("messageParts")
    val smsNumber = varchar("number", 100)
    val smsStatus = varchar("status", 100)
    val smsStatusCode = integer("statusCode")

    override val primaryKey = PrimaryKey(smsMessageId)
}

object AirtimeTable : Table("airtime") {
    val airtimeEmail = varchar("email", 200)
    val amount = varchar("amount", 100)
    val discount = varchar("discount", 100)
    val errorMessage = varchar("errorMessage", 200)
    val phoneNumber = varchar("phoneNumber", 200)
    val requestId = varchar("requestId", 200)
    val airtimeStatus = varchar("status", 200)

    override val primaryKey = PrimaryKey(requestId)
}

object SenderIDTable : Table("sender_id") {
    val senderPK = varchar("senderPK", 300)
    val senderEmail = varchar("senderEmail", 200)
    val senderName = varchar("senderName", 50)
    val sidStatus = bool("sidStatus")

    override val primaryKey = PrimaryKey(senderPK)
}

object AccBalanceTable : Table("account_balance") {
    val accountEmail = varchar("accountEmail",200)
    val accBalance = long("accBalance")
    val deductBalance = long("deductBalance")

    override val primaryKey = PrimaryKey(accountEmail)
}