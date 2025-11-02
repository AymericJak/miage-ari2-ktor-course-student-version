package com.univlille.ari2.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.ThymeleafContent
import kotlinx.serialization.Serializable

@Serializable
data class UpperIn(val text: String)

@Serializable
data class UpperOut(val result: String)

data class ThymeleafUser(val id: Int, val name: String)

/**
 * Routes D'EXEMPLE de requêtes GET/POST
 *
 * - GET  /exemple/ping           -> "pong"
 * - GET  /exemple/echo?msg=...   -> renvoie le message
 * - POST /exemple/upper          -> { "text": "hello" } -> { "result": "HELLO" }
 * - GET  /exemple/thymeleaf      -> rend une page Thymeleaf
 */
fun Application.registerExampleRoutes() {
    routing {
        route("/exemple") {
            get {
                val html = """
                    <html>
                      <head><title>Exemples Ktor</title></head>
                      <body>
                        <h1>Exemples d’API Ktor</h1>
                        <ul>
                          <li><b>GET</b> <a href="/exemple/ping">/exemple/ping</a> - Répond 'pong'</li>
                          <li><b>GET</b> <a href="/exemple/echo?msg=bonjour">/exemple/echo?msg=bonjour</a> - Renvoie le message envoyé</li>
                          <li><b>POST</b> /exemple/upper - Convertit un texte en majuscules</li>
                          <li><b>GET</b> <a href="/exemple/thymeleaf">/exemple/thymeleaf</a> - Page Thymeleaf d'exemple</li>
                        </ul>

                        <h2>Tester la requête POST /exemple/upper</h2>
                        <p>Pour tester la route depuis le terminal :</p>
                        <pre style="background:#f4f4f4;padding:1rem;border-radius:8px;">
curl -X POST http://localhost:8080/exemple/upper \
  -H "Content-Type: application/json" \
  -d '{ "text": "hello" }'
                        </pre>

                        <p>Réponse attendue :</p>
                        <pre style="background:#eef;padding:1rem;border-radius:8px;">{ "result": "HELLO" }</pre>
                      </body>
                    </html>
                """.trimIndent()

                call.respondText(html, ContentType.Text.Html)
            }

            get("/ping") {
                call.respondText("pong", ContentType.Text.Plain)
            }

            get("/echo") {
                val msg = call.request.queryParameters["msg"]
                if (msg.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "missing 'msg'")
                } else {
                    call.respondText(msg, ContentType.Text.Plain)
                }
            }

            post("/upper") {
                val body = call.receive<UpperIn>()
                call.respond(UpperOut(body.text.uppercase()))
            }

            get("/thymeleaf") {
                call.respond(ThymeleafContent("index", mapOf("user" to ThymeleafUser(1, "user1"))))
            }
        }
    }
}
