package com.univlille.ari2.models

import kotlinx.serialization.Serializable

@Serializable
data class MetDepartment(
    val departmentId: Int,
    val displayName: String
)

@Serializable
data class MetDepartments(
    val departments: List<MetDepartment> = emptyList()
)
