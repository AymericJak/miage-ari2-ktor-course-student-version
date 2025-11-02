package com.univlille.ari2.services

import com.univlille.ari2.models.MetDepartments
import com.univlille.ari2.models.MetObject
import com.univlille.ari2.models.MetObjectsIndex
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class MetService {

    private val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 60_000
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    @Serializable
    private data class ObjectResponse(
        @SerialName("objectID") val objectID: Int,
        val title: String = "",
        val artistDisplayName: String = "",
        val primaryImage: String = "",
        val primaryImageSmall: String = "",
        val objectDate: String = "",
        val dimensions: String = "",
        @SerialName("isPublicDomain") val publicDomain: Boolean = false,
        @SerialName("objectURL") val objectURL: String = ""
    )

    @Serializable
    private data class ObjectsIndexResponse(
        val total: Int = 0,
        @SerialName("objectIDs") val objectIDs: List<Int>? = null
    )

    // Q1 - Départements
    suspend fun getDepartments(): MetDepartments {
        return MetDepartments(emptyList())
    }

    // Q2 - Détails d’une œuvre par id
    suspend fun getObject(objectID: Int): MetObject? {
        return null
    }

    // Q6 - Index global des IDs (appel direct)
    suspend fun getObjectsIndex(): MetObjectsIndex {
        return MetObjectsIndex(total = 0, objectIDs = emptyList())
    }

    // Q7 - Cache mémoire (TTL) pour l’index des IDs
    suspend fun getObjectsIndexWithCache(ttlMillis: Long = 60 * 60 * 1000L): MetObjectsIndex {
        return MetObjectsIndex(total = 0, objectIDs = emptyList())
    }

    fun clearObjectsIndexCache() {
    }

    // Q9 - Recherche (q + hasImages optionnel)
    suspend fun searchObjectsIndex(
        q: String,
        hasImages: Boolean? = null
    ): MetObjectsIndex {
        return MetObjectsIndex(total = 0, objectIDs = emptyList())
    }
}
