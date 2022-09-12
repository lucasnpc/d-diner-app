package com.example.ddinerapp.featureMain.domain.model

import java.sql.Timestamp

data class Order(
    val id: String = "",
    val concluded: Boolean = false,
    val employeeCpf: String = "",
    val startDate: Timestamp = Timestamp(System.currentTimeMillis()),
    val endDate: Timestamp? = null
)
