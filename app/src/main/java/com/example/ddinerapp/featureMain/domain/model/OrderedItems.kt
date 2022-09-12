package com.example.ddinerapp.featureMain.domain.model

data class OrderedItems(
    val items: Map<String, Double> = mapOf(
        "" to 0.0
    ),
    val status: String = ""
)
