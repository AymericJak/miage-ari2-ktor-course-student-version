package com.univlille.ari2.plugins

import com.univlille.ari2.routes.registerArtRoutes
import com.univlille.ari2.routes.registerExampleRoutes
import com.univlille.ari2.routes.registerIntroRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")
        get("/") { call.respondText("TP Ktor") }
    }
    registerExampleRoutes()
    registerIntroRoutes()
    registerArtRoutes()
}
