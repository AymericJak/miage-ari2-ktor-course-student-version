package com.univlille.ari2.models

import kotlinx.serialization.Serializable

@Serializable
data class MetObjectsIndex(
    val total: Int,
    val objectIDs: List<Int>
)
