package com.muchbeer

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.muchbeer.routes.*

fun main() {
    embeddedServer(Netty, port = 8085, host = "0.0.0.0") {
        configureMonitoring()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
