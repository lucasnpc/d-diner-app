package com.example.ddinerapp.featureMain.domain.model

data class Order(
    val id: String = "",
    val concluded: Boolean = false,
    val employeeCpf: String = "",
    val startDate: String = "",
    val endDate: String? = null
)
