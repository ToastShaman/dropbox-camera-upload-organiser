package com.github.toastshaman.dropbox.domain

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toLocalDate(): LocalDate = LocalDate.ofInstant(toInstant(), ZoneOffset.UTC)

fun LocalDate.formatAsYearMonth(): String = format(DateTimeFormatter.ofPattern("YYYY-MM"))