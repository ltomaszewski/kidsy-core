package com.growgenie.kidsyCore

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform