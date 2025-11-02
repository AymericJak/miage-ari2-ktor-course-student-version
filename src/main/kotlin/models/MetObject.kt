package com.univlille.ari2.models

import kotlinx.serialization.Serializable

@Serializable
data class MetObject(
    val objectID: Int,
    val title: String,
    val artistDisplayName: String,
    val primaryImage: String,
    val primaryImageSmall: String,
    val objectDate: String,
    val dimensions: String,
    val publicDomain: Boolean,
    val objectURL: String,
)
