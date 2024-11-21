import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TopicSelectionUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val genres: List<Genre> = emptyList()
)

class TopicSelectionViewModel(
    private val navigationViewModel: NavigationViewModel
) {
    private val _uiState = MutableStateFlow(TopicSelectionUiState())
    val uiState: StateFlow<TopicSelectionUiState> = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    private fun loadGenres() {
        try {
            _uiState.value = TopicSelectionUiState(
                isLoading = false,
                genres = Genre.values().toList()
            )
        } catch (e: Exception) {
            _uiState.value = TopicSelectionUiState(
                isLoading = false,
                errorMessage = "장르를 불러오는데 실패했습니다: ${e.message}"
            )
        }
    }

    fun retryLoadingGenres() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        loadGenres()
    }

    fun selectGenre(navController: NavHostController, genre: Genre) {
        val booksInGenre = StoryBookResources.allBooks.filter { it.genre == genre }
        if (booksInGenre.isNotEmpty()) {
            val randomBook = booksInGenre.random()
            navigationViewModel.navigateToFairyTale(navController, randomBook.id)
        }
    }
}