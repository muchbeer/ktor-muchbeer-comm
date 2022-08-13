package com.muchbeer.routes

import com.muchbeer.data.*
import com.muchbeer.data.db.DatabaseFactory
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    DatabaseFactory.init()
    val repository = RepositoryImpl()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/register") {
            val registerUser = call.receive<User>()
         val insertStatus =   repository.insertUser(registerUser)
            if (insertStatus !=null) {  call.respond(status = HttpStatusCode.Created, insertStatus)
            } else call.respondText("Network error",status = HttpStatusCode.BadRequest)
        }

        get("/listusers") {
            val users = repository.retrieveAllUsers()
            call.respond(status = HttpStatusCode.OK, users)
        }
    }

    routing {
        get("/viewSMS") {
            val sms = repository.retrieveAllSMSes()
            call.respond(status = HttpStatusCode.OK, sms)
        }

        post("/saveSMS") {

            val saveSMS = call.receive<SMSRecipient>()
            val dataT = repository.insertSMS(saveSMS)
            if (dataT!=null) {
                call.respond(status = HttpStatusCode.Created, dataT) } else
                    call.respond(status = HttpStatusCode.BadRequest, "something wrong")

        }
    }

    routing {
        get("/viewAirtime") {
            val sms = repository.retrieveAllSMSes()
            call.respond(status = HttpStatusCode.OK, sms)
        }

        post("/saveAirtime") {
            val saveAirtime = call.receive<Airtime>()
            val dataT = repository.insertAirtime(saveAirtime)
            if (dataT!=null) {
                call.respond(status = HttpStatusCode.Created, dataT) } else
                call.respond(status = HttpStatusCode.BadRequest, "something wrong")
        }
    }

   routing {
        post("/senderList") {
            val sid = call.receive<SenderID>()
            val email = sid.senderEmail
            call.respond(status = HttpStatusCode.OK, repository.retrieveAllSenderIDByEmail(email))
        }
    }
}
