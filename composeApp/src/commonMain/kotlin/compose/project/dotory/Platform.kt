package compose.project.dotory

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform