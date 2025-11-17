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
                val req = call.receive<AddIngredientRequest>()
                val name = req.name.trim()
                if (name.isEmpty()) {
                    return@post call.respond(HttpStatusCode.BadRequest, Message("Nom d'ingrédient vide"))
                }
                val exists = Store.ingredients.any { it.equals(name, ignoreCase = true) }
                if (!exists) Store.ingredients.add(name) // À NE PAS FAIRE NUL PART (MERCI) !
                call.respond(HttpStatusCode.Created, Message("Ingrédient '$name' ajouté avec succès !"))
            }

            // 7) Calories d’un plat (JSON)
            get("/calories") {
                val dish = call.request.queryParameters["dish"]?.trim()
                if (dish.isNullOrEmpty()) {
                    return@get call.respond(HttpStatusCode.BadRequest, Message("Paramètre 'dish' requis"))
                }

                val entry = Store.dishCalories.entries.firstOrNull { it.key.equals(dish, ignoreCase = true) }
                if (entry == null) {
                    return@get call.respond(HttpStatusCode.NotFound, Message("Plat inconnu"))
                }
                call.respond(CaloriesOut(entry.key, entry.value))
            }

            // 8) Simuler une commande (POST JSON)
            post("/order") {
                val req = call.receive<OrderIn>()
                val dish = req.dish.trim()
                val qty = req.quantity

                if (dish.isEmpty() || qty <= 0) {
                    return@post call.respond(HttpStatusCode.BadRequest, Message("Plat invalide ou quantité <= 0"))
                }

                val entry = Store.dishPrices.entries.firstOrNull { it.key.equals(dish, ignoreCase = true) }
                if (entry == null) {
                    return@post call.respond(HttpStatusCode.NotFound, Message("Plat inconnu"))
                }

                val total = entry.value * qty
                Store.orders.add(OrderOut(entry.key, qty, total))

                call.respond(HttpStatusCode.Created,
                    mapOf(
                        "confirmation" to "Commande reçue : ${qty}x ${entry.key}",
                        "total" to total.toString()
                    )

                )
            }

            // 9) Historique des commandes (JSON)
            get("/orders") {
                call.respond(Store.orders.asReversed())
            }

            // 10) Afficher une page HTML (Thymeleaf)
            get("/home") {
                val model = mapOf(
                    "title" to "KtorChef - Tableau de bord",
                    "ingredients" to Store.ingredients,
                    "dishPrices" to Store.dishPrices,
                    "orders" to Store.orders.asReversed(),
                    "recipes" to Store.recipesOfTheDay,
                    "todayRecipe" to Store.recipesOfTheDay.random()
                )
                call.respond(ThymeleafContent("intro-kitchen", model))

            }
        }
    }
}
