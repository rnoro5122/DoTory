package io.rnoro.dotory.domain.models

import Genre
import org.jetbrains.compose.resources.DrawableResource

data class LlmStoryBook(
    val id: String,
    val title: String,
    val genre: Genre,
    val content: String,
    val coverImage: DrawableResource,
    val illustrations: List<DrawableResource>,
    val rating: Int,
    val isLlmGenerated: Boolean = true
)

// LLM 스토리 저장소
object LlmStoryRepository {
    private val stories = mutableListOf<LlmStoryBook>()

    fun saveStory(story: LlmStoryBook) {
        stories.add(story)
    }

    fun getAllStories(): List<LlmStoryBook> = stories.toList()

    fun getStoryById(id: String): LlmStoryBook? = stories.find { it.id == id }

    // StoryBook 형식으로 변환
    fun convertToStoryBook(llmStory: LlmStoryBook): StoryBook {
        return StoryBook(
            id = llmStory.id,
            title = llmStory.title,
            genre = llmStory.genre,
            coverImage = llmStory.coverImage,
            firstPartText = llmStory.content,
            activityImage = llmStory.illustrations.firstOrNull() ?: llmStory.coverImage,
            activityText = "",
            secondPartText = "",
            illustrations = llmStory.illustrations,
            description = "LLM이 생성한 동화"
        )
    }
}