package com.growgenie.kidsyCore.sleepTracker

import com.growgenie.kidsyCore.utils.DateHelper
import com.growgenie.kidsyCore.utils.RealmHelper
import io.realm.kotlin.ext.query

class SleepTrackerManager(realmHelper: RealmHelper) {
    private val realm = realmHelper.realm
    private val dateHelper = DateHelper()

    // Get list of sleeps or naps for a specific date
    fun getSessionsForDate(date: Long): List<SleepSession> {
        val startOfDay = dateHelper.getStartOfDayUnixTimestamp(date)
        val endOfDay = dateHelper.getEndOfDayUnixTimestamp(date)
        return realm.query<SleepSession>("startTime > $0 AND startTime < $1", startOfDay, endOfDay)
            .find()
    }

    // Add a new sleep or nap
    fun addSession(startTime: Long, endTime: Long = -1, type: SleepSessionType): SleepSession {
        val session = SleepSession().apply {
            this.startTime = startTime
            this.endTime = endTime
            this.type = type.toString()
        }
        realm.writeBlocking {
            copyToRealm(session)
        }
        return session
    }

    // Modify an existing sleep or nap
    fun modifySession(
        id: String,
        startTime: Long?,
        endTime: Long?,
        type: SleepSessionType?
    ): SleepSession {
        realm.writeBlocking {
            val session = query<SleepSession>("id == $0", id).find().first()
            if (startTime is Long) {
                session.startTime = startTime
            }

            if (endTime is Long) {
                session.endTime = endTime
            }

            if (type is SleepSessionType) {
                session.type = type.toString()
            }
        }
        return realm.query<SleepSession>("id == $0", id).find().first()
    }

    // Delete an existing sleep or nap
    fun deleteSession(id: String) {
        realm.writeBlocking {
            val session = query<SleepSession>("id == $0", id).find().first()
            delete(session)
        }
    }

    // Get today's sleep or nap sessions
    fun getTodaySessions(): List<SleepSession> {
        val today = dateHelper.getCurrentUnixTimestamp()
        return getSessionsForDate(today)
    }
}