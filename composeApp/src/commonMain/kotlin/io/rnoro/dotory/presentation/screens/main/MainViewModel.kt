package io.rnoro.dotory.presentation.screens.main

import androidx.lifecycle.ViewModel
import io.rnoro.dotory.domain.models.Book
import io.rnoro.dotory.domain.models.BookResources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    data class MainUiState(
        val isExpanded: Boolean = false,
        val books: List<Book> = BookResources.bookGenres.flatMap { it.books },
        val scrollPosition: Float = 0f,
        val autoScrollActive: Boolean = true
    )

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()
}
