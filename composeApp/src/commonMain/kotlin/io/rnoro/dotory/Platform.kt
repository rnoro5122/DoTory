package io.rnoro.dotory

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform