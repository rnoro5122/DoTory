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

    var hasCompletedStory by mutableStateOf(false)
        private set

    fun loadStory(storyId: String, isFromBookshelf: Boolean = false): Boolean {
        storyBook = StoryBookResources.getBookById(storyId)?.also { book ->
            pages = if (isFromBookshelf) {
                // 책장에서 열었을 때는 전체 이야기를 한 번에 보여줌
                val firstPart = book.generatePages(isFirstPart = true)
                val secondPart = book.generatePages(isFirstPart = false)
                firstPart + secondPart
            } else {
                // 일반적인 경우 (활동 기반)
                if (!hasCompletedActivity) {
                    book.generatePages(isFirstPart = true)
                } else {
                    book.generatePages(isFirstPart = false)
                }
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

    fun completeStory() {
        hasCompletedStory = true
    }

    fun getCurrentPage(): StoryPage? = pages.getOrNull(currentPage)
    fun getCurrentStoryBook(): StoryBook? = storyBook

    fun canGoToPreviousPage() = currentPage > 0
    fun canGoToNextPage() = currentPage < pages.size - 1

    // 마지막 페이지이고 활동을 완료했는지 확인하는 함수 추가
    fun isLastPageAndActivityCompleted() = currentPage == pages.size - 1 && hasCompletedActivity && !hasCompletedStory

    fun goToPreviousPage() {
        if (canGoToPreviousPage()) currentPage--
    }

    fun goToNextPage() {
        if (canGoToNextPage()) currentPage++
    }
}