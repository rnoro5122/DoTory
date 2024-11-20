package compose.project.dotory.presentation.navigation

import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.bookshelf_icon
import dotory.composeapp.generated.resources.tree_icon
import dotory.composeapp.generated.resources.write_icon

object NavigationConfig {
    // Routes
    const val MAIN_SCREEN = "MainScreen"
    const val TOPIC_SELECTION_SCREEN = "TopicSelectionScreen"
    const val BOOKSHELF_SCREEN = "BookShelfScreen"
    private const val FAIRY_TALE_SCREEN = "FairyTaleScreen"
    const val FAIRY_TALE_ROUTE = "$FAIRY_TALE_SCREEN/{topicId}"

    fun createFairyTaleRoute(topicId: String) = "$FAIRY_TALE_SCREEN/$topicId"

    // Labels
    const val LABEL_BOOKSHELF = "책장"
    const val LABEL_HOME = "홈"
    const val LABEL_CREATE_STORY = "이야기 생성"

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
