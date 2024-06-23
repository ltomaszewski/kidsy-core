package com.growgenie.kidsyCore.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class DateHelper {
    fun getCurrentUnixTimestamp(): Long {
        return Clock.System.now().epochSeconds
    }

    fun getStartOfDayUnixTimestamp(timestamp: Long): Long {
        val dateTime = kotlinx.datetime.Instant.fromEpochSeconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val startOfDay =
            dateTime.date.atStartOfDayIn(TimeZone.currentSystemDefault())
        return startOfDay.epochSeconds
    }

    fun getEndOfDayUnixTimestamp(timestamp: Long): Long {
        val dateTime = kotlinx.datetime.Instant.fromEpochSeconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val endOfDay = dateTime.date
            .plus(1, DateTimeUnit.DAY)
            .atStartOfDayIn(TimeZone.currentSystemDefault())
            .minus(1, DateTimeUnit.SECOND)
        return endOfDay.epochSeconds
    }
}