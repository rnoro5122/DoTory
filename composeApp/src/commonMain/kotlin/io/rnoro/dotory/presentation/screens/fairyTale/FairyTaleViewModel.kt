package io.rnoro.dotory.presentation.screens.fairyTale

import StoryBook
import StoryBookResources
import StoryPage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FairyTaleViewModel : ViewModel() {
    private var storyBook: StoryBook? = null
    private var pages: List<StoryPage> = emptyList()

    var currentPage by mutableStateOf(0)
        private set

    var hasCompletedActivity by mutableStateOf(false)
        private set

    fun loadStory(storyId: String): Boolean {
        storyBook = StoryBookResources.getBookById(storyId)?.also { book ->
            pages = if (!hasCompletedActivity) {
                book.generatePages(isFirstPart = true)
            } else {
                book.generatePages(isFirstPart = false)
            }
        }
        currentPage = 0
        return storyBook != null
    }

    fun completeActivity() {
        hasCompletedActivity = true
        storyBook?.let { book ->
            pages = book.generatePages(isFirstPart = false)
            currentPage = 0
        }
    }

    fun getCurrentPage(): StoryPage? = pages.getOrNull(currentPage)
    fun getCurrentStoryBook(): StoryBook? = storyBook

    fun canGoToPreviousPage() = currentPage > 0
    fun canGoToNextPage() = currentPage < pages.size - 1

    fun goToPreviousPage() {
        if (canGoToPreviousPage()) currentPage--
    }

    fun goToNextPage() {
        if (canGoToNextPage()) currentPage++
    }
}