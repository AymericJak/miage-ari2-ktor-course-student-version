package com.univlille.ari2.data

import kotlinx.serialization.Serializable

@Serializable
data class AddIngredientRequest(val name: String)

@Serializable
data class OrderIn(val dish: String, val quantity: Int)

@Serializable
data class OrderOut(val dish: String, val quantity: Int, val total: Double)

@Serializable
data class CaloriesOut(val dish: String, val calories: Int)

@Serializable
data class Message(val message: String)

object Store {
    val ingredients: MutableList<String> = mutableListOf(
        "tomate",
        "mozzarella",
        "basilic",
        "huile d'olive",
        "ail",
        "oignon",
        "parmesan",
        "farine",
        "levure",
        "olives noires"
    )

    val dishPrices: Map<String, Double> = mapOf(
        "pizza" to 12.0,
        "margherita" to 11.0,
        "salade" to 10.0,
        "lasagnes" to 14.0,
        "soupe" to 8.0,
        "carbonara" to 13.5,
        "gnocchi" to 11.0
    )

    val dishCalories: Map<String, Int> = mapOf(
        "pizza" to 900,
        "margherita" to 820,
        "salade" to 350,
        "lasagnes" to 850,
        "soupe" to 250,
        "carbonara" to 780,
        "gnocchi" to 620
    )

    val recipesOfTheDay: List<String> = listOf(
        "Pizza Margherita",
        "Pâtes Carbonara",
        "Gnocchi au pesto",
        "Bruschetta tomate-basilic",
        "Lasagnes à la bolognaise",
        "Risotto aux champignons"
    )

    val orders: MutableList<OrderOut> = mutableListOf(
        OrderOut("margherita", 2, 22.0),
        OrderOut("carbonara", 1, 13.5),
        OrderOut("pizza", 3, 36.0),
        OrderOut("lasagnes", 1, 14.0),
        OrderOut("gnocchi", 2, 22.0)
    )
}
