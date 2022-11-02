package com.example.ddinerapp.common.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateFormat(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this).toString()
}

fun Long.toHourFormat(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(this).toString()
}

fun Long.toShowDateFormat(): String {
    return SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(this).toString()
}

fun Double.currencyFormat(): String {
    return "R$ ${String.format("%.2f", this).replace(".", ",")}"
}