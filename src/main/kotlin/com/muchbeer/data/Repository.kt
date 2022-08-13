package com.muchbeer.data

interface Repository {

    suspend fun insertUser(mUser: User) : User?
    suspend fun insertSMS(recipient : SMSRecipient) : SMSRecipient?
    suspend fun insertAirtime(mAirtime: Airtime) : Airtime?

    suspend fun insertAcctBalance(mAcctBalance : AccountBalance) : String

    suspend fun insertSenderID(mSenderID: SenderID) : String
    suspend fun retrieveAllUsers() : List<User>
    suspend fun retrieveAllSMSByEmail(email : String) : List<SMSRecipient>
    suspend fun retrieveAllAirtimeByEmail(email: String) : List<Airtime>
    suspend fun retrieveAllAcctBalance() : List<AccountBalance>
    suspend fun retrieveAllSenderIDByEmail(email : String) : List<SenderID>
    suspend fun retrieveAllSMSes() : List<SMSRecipient>
    suspend fun retrieveAllAirtimes() : List<Airtime>
    suspend fun updateBalance(acctBalance : AccountBalance)


}