package io.rnoro.dotory.presentation.navigation

import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.bookshelf_icon
import dotory.composeapp.generated.resources.tree_icon
import dotory.composeapp.generated.resources.write_icon

object NavigationConfig {
    // Routes
    const val MAIN_SCREEN = "MainScreen"
    const val TOPIC_SELECTION_SCREEN = "TopicSelectionScreen"
    const val BOOKSHELF_SCREEN = "BookShelfScreen"

    const val FAIRY_TALE_ROUTE = "fairy_tale/{topicId}?isFromBookshelf={isFromBookshelf}&isLlmMode={isLlmMode}&topic={topic}"

    fun createFairyTaleRoute(
        topicId: String,
        isFromBookshelf: Boolean = false,
        isLlmMode: Boolean = false,
        topic: String? = null
    ): String {
        return buildString {
            append("fairy_tale/$topicId")
            append("?isFromBookshelf=$isFromBookshelf")
            append("&isLlmMode=$isLlmMode")
            append("&topic=${topic?.replace(" ", "_") ?: ""}")
        }
    }

    private const val ACTIVITY_RECORD_BASE = "activity_record"
    const val ACTIVITY_RECORD_ROUTE = "$ACTIVITY_RECORD_BASE/{storyId}"

    const val STORY_COMPLETE_ROUTE = "StoryComplete/{storyId}"

    fun createActivityRecordRoute(storyId: String) = "$ACTIVITY_RECORD_BASE/$storyId"
    fun createStoryCompleteRoute(storyId: String) = "StoryComplete/$storyId"

    // Labels
    const val LABEL_BOOKSHELF = "책장"
    const val LABEL_HOME = "홈"
    const val LABEL_CREATE_STORY = "만들기"

    // Icons
    val ICON_BOOKSHELF = Res.drawable.bookshelf_icon
    val ICON_HOME = Res.drawable.tree_icon
    val ICON_CREATE_STORY = Res.drawable.write_icon

    // Bottom Nav Screens
    val BOTTOM_NAV_SCREENS = listOf(
        MAIN_SCREEN,
        TOPIC_SELECTION_SCREEN,
        BOOKSHELF_SCREEN
    )
}
