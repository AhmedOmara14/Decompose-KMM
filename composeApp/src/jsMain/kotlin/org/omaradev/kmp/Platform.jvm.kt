package org.omaradev.kmp

class JSPlatform: Platform {
    override val name: String = "Compose For Web Using Kotlin/JS"
}

actual fun getPlatform(): Platform = JSPlatform()