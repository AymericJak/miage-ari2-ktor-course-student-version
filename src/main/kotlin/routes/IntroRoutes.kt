package com.univlille.ari2.routes

import com.univlille.ari2.data.AddIngredientRequest
import com.univlille.ari2.data.CaloriesOut
import com.univlille.ari2.data.Message
import com.univlille.ari2.data.OrderIn
import com.univlille.ari2.data.OrderOut
import com.univlille.ari2.data.Store
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.ThymeleafContent

fun Application.registerIntroRoutes() {
    routing {
        route("/intro") {

            // 1) Dire bonjour au chef (texte)
            get("/hello") {
                call.respondText("Bienvenue dans la cuisine de KtorChef !")
            }

            // 2) Saluer un cuisinier (path param)
            get("/greet/{name}") {
                val name = call.parameters["name"]
                call.respondText("Bonjour Chef $name ! Prêt à cuisiner ?")
            }

            // 3) Recette du jour (texte)
            get("/recipe") {
                val recipe = Store.recipesOfTheDay.random()
                call.respondText("Recette du jour : $recipe")
            }

            // 4) Liste des ingrédients (JSON)
            get("/ingredients") {
                call.respond(Store.ingredients)
            }

            // 5) Chercher un ingrédient (query param + texte)
            get("/ingredients/search") {
                val ingredientParam = call.request.queryParameters["name"]

                if (ingredientParam.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, Message("Paramètre 'name' requis"))
                }

                val found = Store.ingredients.any { it.equals(ingredientParam, ignoreCase = true) }

                if (found) {
                    call.respondText("L’ingrédient \"$ingredientParam\" est disponible !")
                } else {
                    call.respondText("Désolé, \"$ingredientParam\" n’est pas encore au menu.")
                }
            }

            // 6) Ajouter un ingrédient (POST JSON)
            post("/ingredients") {
            }

            // 7) Calories d’un plat (JSON)
            get("/calories") {
            }

            // 8) Simuler une commande (POST JSON)
            post("/order") {
            }

            // 9) Historique des commandes (JSON)
            get("/orders") {
            }

            // 10) Afficher une page HTML (Thymeleaf)
            get("/home") {
            }
        }
    }
}
