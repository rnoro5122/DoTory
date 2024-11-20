package compose.project.dotory.domain.models

import org.jetbrains.compose.resources.DrawableResource

data class Book(
    val image: DrawableResource,
    val name: String
)