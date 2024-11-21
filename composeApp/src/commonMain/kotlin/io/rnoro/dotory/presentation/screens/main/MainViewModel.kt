package io.rnoro.dotory.presentation.screens.main

import Genre
import StoryBook
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    data class MainUiState(
        val isExpanded: Boolean = false,
        val booksByGenre: Map<Genre, List<StoryBook>> = emptyMap(),
        val allBooks: List<StoryBook> = emptyList(),
        val scrollPosition: Float = 0f,
        val autoScrollActive: Boolean = true
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        val allBooks = StoryBookResources.allBooks
        val booksByGenre = allBooks.groupBy { it.genre }

        _uiState.value = MainUiState(
            booksByGenre = booksByGenre,
            allBooks = allBooks
        )
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