package org.ccaa.android.ccaa_smp.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toSimpleDate() : String
{
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val instant = Instant.ofEpochMilli(this)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(date)
}
fun Long.toFullDate() : String
{
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    val instant = Instant.ofEpochMilli(this)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(date)
}