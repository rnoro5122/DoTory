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

    // NavigationViewModel의 isLlmModeEnabled를 직접 사용
    val isLlmModeEnabled = navigationViewModel.isLlmModeEnabled

    fun toggleLlmMode() {
        navigationViewModel.toggleLlmMode(!isLlmModeEnabled.value)
    }

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
        if (isLlmModeEnabled.value) {
            // LLM 모드일 때는 장르 이름만 전달
            navigationViewModel.navigateToFairyTale(
                navController = navController,
                storyId = genre.name,
                isLlmMode = true,
                topic = genre.displayName
            )
        } else {
            // 일반 모드일 때는 기존 로직 유지
            val booksInGenre = StoryBookResources.allBooks.filter { it.genre == genre }
            if (booksInGenre.isNotEmpty()) {
                val randomBook = booksInGenre.random()
                navigationViewModel.navigateToFairyTale(
                    navController = navController,
                    storyId = randomBook.id
                )
            }
        }
    }
}