package io.rnoro.dotory.presentation.screens.fairyTale

import StoryBook
import StoryBookResources
import StoryPage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UserInfo(
    val name: String,
    val gender: String
)

expect fun initLlama()
expect fun runLlama(prompt: String, printer: (String) -> Unit)

class FairyTaleViewModel : ViewModel() {
    private var storyBook: StoryBook? = null
    private var pages: List<StoryPage> = emptyList()

    var currentPage by mutableStateOf(0)
        private set

    var hasCompletedActivity by mutableStateOf(false)
        private set

    var hasCompletedStory by mutableStateOf(false)
        private set

    var isLlmMode by mutableStateOf(false)
        private set

    var displayedText by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun loadStory(
        storyId: String,
        isFromBookshelf: Boolean = false,
        isLlmMode: Boolean = false,
        topic: String? = null
    ): Boolean {
        this.isLlmMode = isLlmMode

        return if (isLlmMode && topic != null) {
            loadLlmStory(topic)
            true
        } else {
            loadRegularStory(storyId, isFromBookshelf)
        }
    }

    private fun loadRegularStory(storyId: String, isFromBookshelf: Boolean): Boolean {
        storyBook = StoryBookResources.getBookById(storyId)?.also { book ->
            pages = if (isFromBookshelf) {
                val firstPart = book.generatePages(isFirstPart = true)
                val secondPart = book.generatePages(isFirstPart = false)
                firstPart + secondPart
            } else {
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

    private fun loadLlmStory(topic: String) {
        viewModelScope.launch {
            isLoading = true
            displayedText = "" // 텍스트 초기화

            try {
                withContext(Dispatchers.IO) {
                    initLlama()
                }
                isLoading = false

                val userInfo = UserInfo(
                    name = "아이름",
                    gender = "여자"
                )

                val prompt = """
                    Create a ${topic} story PART 1. 
                    Child: ${userInfo.name}, ${userInfo.gender}. 
                    Introduce ${userInfo.name} in a magical setting, 
                    present a simple environmental challenge, 
                    then stop at WAITING_FOR_PHOTO. 
                    Keep story and issues child-friendly.
                """.trimIndent()

                withContext(Dispatchers.IO) {
                    runLlama(prompt) { text ->
                        displayedText += text
                    }
                }
            } catch (e: Exception) {
                isLoading = false
                displayedText = "이야기를 생성하는 중 오류가 발생했습니다: ${e.message}"
            }
        }
    }

    fun getCurrentPage(): StoryPage? = pages.getOrNull(currentPage)
    fun getCurrentStoryBook(): StoryBook? = storyBook

    fun canGoToPreviousPage() = currentPage > 0
    fun canGoToNextPage() = currentPage < pages.size - 1
    fun isLastPageAndActivityCompleted() = currentPage == pages.size - 1 && hasCompletedActivity && !hasCompletedStory

    fun goToPreviousPage() {
        if (canGoToPreviousPage()) currentPage--
    }

    fun goToNextPage() {
        if (canGoToNextPage()) currentPage++
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
}