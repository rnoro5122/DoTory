package compose.project.dotory.domain.models

data class BookGenre(
    val title: String,
    val books: List<Book>
)