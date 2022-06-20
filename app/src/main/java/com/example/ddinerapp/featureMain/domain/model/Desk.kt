package com.example.ddinerapp.featureMain.domain.model

data class Desk(
    val id: Int,
    val name: String = "",
    val isOccupied: Boolean = false
)
