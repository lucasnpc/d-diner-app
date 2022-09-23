package com.example.ddinerapp.common.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateFormat(): String {
    return SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.getDefault()).format(this).toString()
}

fun Long.toHourFormat(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(this).toString()
}