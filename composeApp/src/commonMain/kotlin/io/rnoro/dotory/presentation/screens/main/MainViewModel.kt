package io.rnoro.dotory.presentation.screens.main

import Genre
import androidx.lifecycle.ViewModel
import io.rnoro.dotory.domain.models.StoryBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.DrawableResource

class MainViewModel : ViewModel() {
    data class MainUiState(
        val isExpanded: Boolean = false,
        val booksByGenre: Map<Genre, List<StoryBook>> = emptyMap(),
        val allBooks: List<StoryBook> = emptyList(),
        val scrollPosition: Float = 0f,
        val autoScrollActive: Boolean = true,
        val popularBooks: List<StoryBook> = emptyList(),
        val sharedBooks: List<SharedBook> = emptyList()
    )

    data class SharedBook(
        val id: String,
        val title: String,
        val author: String,
        val coverImage: DrawableResource,
        val likes: Int,
        val createdAt: String
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
        loadPopularBooks()
        loadSharedBooks()
    }

    private fun loadBooks() {
        val allBooks = StoryBookResources.allBooks
        val booksByGenre = allBooks.groupBy { it.genre }

        _uiState.value = _uiState.value.copy(
            booksByGenre = booksByGenre,
            allBooks = allBooks
        )
    }

    private fun loadPopularBooks() {
        // 임시로 가장 좋아요가 많은 책들을 선별
        val popularBooks = StoryBookResources.allBooks.take(5)
        _uiState.value = _uiState.value.copy(popularBooks = popularBooks)
    }

    private fun loadSharedBooks() {
        // 임시 데이터
        val sharedBooks = listOf(
            SharedBook(
                id = "1",
                title = "초록 섬의 비밀",
                author = "한경",
                coverImage = StoryBookResources.BookAssets.greenIsland.cover,
                likes = 156,
                createdAt = "2024-12-07"
            ),
            SharedBook(
                id = "2",
                title = "마녀와 저주받은 성",
                author = "민지",
                coverImage = StoryBookResources.BookAssets.cursedCastle.cover,
                likes = 142,
                createdAt = "2024-12-18"
            ),
            SharedBook(
                id = "3",
                title = "구름 위의 집",
                author = "준호",
                coverImage = StoryBookResources.BookAssets.skyWalk.cover,
                likes = 128,
                createdAt = "2024-12-09"
            ),
            SharedBook(
                id = "4",
                title = "사라진 치킨",
                author = "시아",
                coverImage = StoryBookResources.BookAssets.lostChicken.cover,
                likes = 92,
                createdAt = "2024-12-15"
            ),
            SharedBook(
                id = "5",
                title = "잃어버린 시간을 찾는 시계탑",
                author = "준호",
                coverImage = StoryBookResources.BookAssets.clockTower.cover,
                likes = 62,
                createdAt = "2024-12-16"
            ),
            SharedBook(
                id = "6",
                title = "콩나물 냄비의 비밀",
                author = "나연",
                coverImage = StoryBookResources.BookAssets.sprout.cover,
                likes = 13,
                createdAt = "2024-12-19"
            )
        )
        _uiState.value = _uiState.value.copy(sharedBooks = sharedBooks)
    }

    fun setExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(isExpanded = expanded)
    }

    fun updateScrollPosition(position: Float) {
        _uiState.value = _uiState.value.copy(scrollPosition = position)
    }

    fun setAutoScrollActive(active: Boolean) {
        _uiState.value = _uiState.value.copy(autoScrollActive = active)
    }
}