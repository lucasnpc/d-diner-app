package com.example.ddinerapp.featureHome.domain.model

data class Order(
    val id: String = "",
    val concluded: Boolean = false,
    val employeeCpf: String = "",
    val startDate: String = "",
    val startHour: String = "",
    val endDate: String? = null,
    val endHour: String? = null
)
