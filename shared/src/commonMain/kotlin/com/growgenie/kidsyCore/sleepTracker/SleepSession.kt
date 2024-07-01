package com.growgenie.kidsyCore.sleepTracker

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class SleepSession : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var startTime: Long = -1
    var endTime: Long = -1
    var type: String = SleepSessionType.NIGHT.toString()
}

enum class SleepSessionType {
    NIGHT,
    NAP
}
