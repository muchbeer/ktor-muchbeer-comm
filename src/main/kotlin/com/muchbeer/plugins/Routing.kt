package com.muchbeer.plugins

import com.muchbeer.data.RepositoryImpl
import com.muchbeer.data.User
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
            call.respond(status = HttpStatusCode.Created, users)
        }
    }
}
