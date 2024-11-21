package io.rnoro.dotory.presentation.screens.topicSelect

import androidx.navigation.NavHostController
import io.rnoro.dotory.domain.models.Topic
import io.rnoro.dotory.domain.models.TopicResources
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TopicSelectionViewModel.kt
class TopicSelectionViewModel(
    private val navigationViewModel: NavigationViewModel
) {
    data class TopicSelectionUiState(
        val topics: List<Topic> = emptyList(),
        val isLoading: Boolean = false,
        val selectedTopic: Topic? = null,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(TopicSelectionUiState())
    val uiState: StateFlow<TopicSelectionUiState> = _uiState.asStateFlow()

    init {
        loadTopics()
    }

    fun selectTopic(navController: NavHostController, topic: Topic) {
        println("TopicSelectionViewModel: Selecting topic ${topic.id}")
        _uiState.update { it.copy(selectedTopic = topic) }
        println("TopicSelectionViewModel: Calling navigateToFairyTale with ${topic.id}")
        navigationViewModel.navigateToFairyTale(navController, topic.id)
    }

    fun retryLoadingTopics() {
        loadTopics()
    }

    private fun loadTopics() {
        _uiState.update { it.copy(isLoading = true) }

        try {
            val topics = TopicResources.allTopics
            if (topics.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "사용 가능한 주제가 없습니다."
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        topics = topics,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "주제를 불러오는데 실패했습니다: ${e.message}"
                )
            }
        }
    }

    fun clearSelectedTopic() {
        _uiState.update { it.copy(selectedTopic = null) }
    }
}