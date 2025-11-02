package com.univlille.ari2.routes

import com.univlille.ari2.models.MetObject
import com.univlille.ari2.services.GalleryService
import com.univlille.ari2.services.MetService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.ThymeleafContent

fun Application.registerArtRoutes() {
    val met = MetService()
    val gallery = GalleryService(met)

    routing {
        route("/art") {
            get {
                val featured: List<MetObject> = gallery.featuredSequential()
//                val featured: List<MetObject> = gallery.featuredParallel()
                val departments = met.getDepartments()

                val model = mapOf(
                    "title" to "KtorGallery - Accueil",
                    "featured" to featured,
                    "departments" to departments
                )
                call.respond(ThymeleafContent("art-home", model))
            }

            get("/work/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "id invalide")
                val work = met.getObject(id)
                    ?: return@get call.respond(HttpStatusCode.NotFound, "Œuvre introuvable")
                call.respond(work)
            }

            get("/objects") {
                val resp = met.getObjectsIndex()
//                val resp = met.getObjectsIndexWithCache()
                call.respond(resp)
            }

            get("/random") {
                val work = gallery.randomWork()
                    ?: return@get call.respond(HttpStatusCode.NotFound, "Aucune œuvre trouvée")

                val model = mapOf(
                    "title" to "Œuvre aléatoire - KtorGallery",
                    "work" to work
                )
                call.respond(ThymeleafContent("art-random", model))
            }

            get("/search/view") {
                val q = call.request.queryParameters["q"]?.trim()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Paramètre 'q' requis")
                val hasImages = call.request.queryParameters["hasImages"]?.toBooleanStrictOrNull()

                val gallery = GalleryService(met)
                val (total, works) = gallery.searchWorks(q, hasImages, limit = 12)

                val model = mapOf(
                    "title" to "Résultats pour « $q » - KtorGallery",
                    "q" to q,
                    "hasImages" to (hasImages == true),
                    "total" to total,
                    "resultsCount" to works.size,
                    "works" to works
                )

                call.respond(ThymeleafContent("art-search", model))
            }
        }
    }
}
