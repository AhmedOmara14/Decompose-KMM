package org.omaradev.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform