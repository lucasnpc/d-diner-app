package com.example.ddinerapp.featureMain.domain.model

data class OrderedItems(
    val id: String = "",
    val itemId: String = "",
    val observations: String = "",
    val quantity: Double = 0.0,
    val status: String = ""
)
