package com.muchbeer.data

import com.muchbeer.data.db.*
import com.muchbeer.data.db.AccBalanceTable.accountEmail
import com.muchbeer.data.db.AirtimeTable.airtimeEmail
import com.muchbeer.data.db.SMSTable.smsEmail
import com.muchbeer.utils.hash
import org.jetbrains.exposed.sql.*

class RepositoryImpl : Repository {
    override suspend fun insertUser(mUser: User): User? {
    val t =    dbQuery {
            UserTable.insert { user ->
                user[userEmail] = mUser.email
                user[userName] = mUser.userName
                user[name] = mUser.name
                user[hashPassword] =  hash(mUser.hashPassword)
            }
        }
     return   if (t.insertedCount > 0) mUser else null
    }
    override suspend fun insertSMS(recipient : SMSRecipient): SMSRecipient? {
        val t = dbQuery {
            SMSTable.insert { sms ->
                sms[smsEmail] = recipient.email
                sms[smsCost] = recipient.cost
                sms[smsStatus] = recipient.status
                sms[smsMessage] = recipient.message
                sms[smsMessageId] = recipient.messageId
                sms[smsMessageParts] = recipient.messageParts
                sms[smsNumber] = recipient.number
                sms[smsStatusCode] =recipient.statusCode
            }
        }
        return   if (t.insertedCount > 0) recipient else null
    }
    override suspend fun insertAirtime(mAirtime: Airtime): Airtime? {
       val t = dbQuery {
           AirtimeTable.insert { airtime->
               airtime[airtimeEmail] = mAirtime.email
               airtime[phoneNumber] = mAirtime.phoneNumber
               airtime[amount] = mAirtime.amount
               airtime[airtimeStatus] = mAirtime.status
               airtime[discount] = mAirtime.discount
               airtime[errorMessage] = mAirtime.errorMessage
               airtime[requestId] = mAirtime.requestId

           }
       }
        return   if (t.insertedCount > 0) mAirtime else null
    }
    override suspend fun insertAcctBalance(mAcctBalance: AccountBalance): String {
     return   dbQuery {
            AccBalanceTable.insertIgnore { acct ->
                acct[accBalance] = mAcctBalance.accBalance
                acct[accountEmail] = mAcctBalance.accountEmail
                acct[deductBalance] = mAcctBalance.deductBalance
            } get(accountEmail)
        }
    }
    override suspend fun insertSenderID(mSenderID: SenderID): String {
       return dbQuery {
            SenderIDTable.insertIgnore { sid->
                sid[senderPK] = mSenderID.senderPK
                sid[senderEmail] = mSenderID.senderEmail
                sid[senderName] = mSenderID.senderName
                sid[sidStatus] = mSenderID.sidStatus
            } get(SenderIDTable.senderName)
        }
    }

    override suspend fun retrieveAllUsers(): DataResponse<Any> {
    return DataResponse.SuccessResponse( data= dbQuery {
        UserTable.selectAll().map { userRow->
            mapToUserModel(userRow)
        }
    }, message = "Success")
    }
    override suspend fun retrieveAllSMSByEmail(email: String): List<SMSRecipient> {
      return  dbQuery {
            SMSTable.select {
                smsEmail eq email
            }.map { smsRow->
                mapToSMSModel(smsRow)
            }
        }
    }
    override suspend fun retrieveAllAirtimeByEmail(email: String): List<Airtime> {
      return  dbQuery {
            AirtimeTable.select {
                airtimeEmail eq email
            }.map { airtimeRow->
                mapToAirtime(airtimeRow)
            }
        }
    }
    override suspend fun retrieveAllAcctBalance(): List<AccountBalance> {
     return  dbQuery { AccBalanceTable.selectAll()
           .map { acctRow->
                mapToAccBalance(acctRow)
           }
       }
    }
    override suspend fun retrieveAllSenderIDByEmail(email: String): List<SenderID> {
       return dbQuery {
            SenderIDTable.select {
                SenderIDTable.senderEmail eq email
            }.map { sidRow ->
                mapToSenderID(sidRow)
            }
        }
    }
    override suspend fun retrieveAllSMSes(): List<SMSRecipient> {
        return  dbQuery {
            SMSTable.selectAll().map { smsRow->
                mapToSMSModel(smsRow)
            }
        }
    }
    override suspend fun retrieveAllAirtimes(): List<Airtime> {
        return dbQuery {
            AirtimeTable.selectAll().map { airtimeRow->
                mapToAirtime(airtimeRow)
            }
        }
    }
    override suspend fun updateBalance(acctBalance: AccountBalance) {
        dbQuery {
            AccBalanceTable.update(
                where = { accountEmail eq acctBalance.accountEmail })
                                        { updtStatement->
                updtStatement[accBalance] = acctBalance.accBalance
            }
        }
    }
}