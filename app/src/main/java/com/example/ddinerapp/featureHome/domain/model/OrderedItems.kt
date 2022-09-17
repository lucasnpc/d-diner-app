package com.example.ddinerapp.featureHome.domain.model

data class OrderedItems(
    val id: String = "",
    val placedItems: Map<String, Double> = mapOf(),
    val observations: String = "",
    val status: String = ""
)
