package com.muchbeer.data

import org.jetbrains.exposed.sql.Table

data class User(
    val email : String,
    val userName : String,
    val hashPassword :  String,
    val name : String
)

data class ATMessage(
    val email : String,
    val recipients: List<SMSRecipient>
)


data class SMSRecipient(
    val email : String,
    val message: String,
    val cost: String,
    val messageId: String,
    val messageParts: Int,
    val number: String,
    val status: String,
    val statusCode: Int
)

data class ATAirtime(
val email: String,
val airtimes : List<Airtime>
)

data class Airtime(
    val email: String,
    val amount: String,
    val discount: String,
    val errorMessage: String,
    val phoneNumber: String,
    val requestId: String,
    val status: String
)

data class SenderID(
    val senderPK : String,
    val senderEmail : String,
    val senderName : String,
    val sidStatus : Boolean = false
)

data class AccountBalance(
    val accountEmail : String,
    val accBalance : Long ,
    val deductBalance : Long = 0L
)