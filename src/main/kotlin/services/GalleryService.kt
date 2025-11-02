package com.univlille.ari2.services

import com.univlille.ari2.models.MetObject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.random.Random

class GalleryService(private val met: MetService) {

    // Q3 - Œuvres « mises en avant » (séquentiel)
    suspend fun featuredSequential(): List<MetObject> {
        return emptyList()
    }

    // Q4 - Œuvres « mises en avant » (parallèle)
    suspend fun featuredParallel(): List<MetObject> = coroutineScope {
        emptyList()
    }

    // Q8 - Œuvre aléatoire
    suspend fun randomWork(): MetObject? {
        return null
    }

    // Q9 - Recherche + chargement des fiches en parallèle (limité à N)
    suspend fun searchWorks(q: String, hasImages: Boolean? = null, limit: Int = 12): Pair<Int, List<MetObject>> {
        return 0 to emptyList()
    }
}
