package com.example.ddinerapp.featureMain.domain.model

data class Desk(
    val description: String = "",
    @field:JvmField
    val isOccupied: Boolean = false
)
