package com.univlille.ari2

import com.univlille.ari2.plugins.configureRouting
import com.univlille.ari2.plugins.configureSerialization
import com.univlille.ari2.plugins.configureTemplating
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureTemplating()
    configureRouting()
}
