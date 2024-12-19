package io.rnoro.dotory.domain.models

import Genre
import org.jetbrains.compose.resources.DrawableResource


data class StoryBook(
    val id: String,
    val title: String,
    val genre: Genre,
    val coverImage: DrawableResource,
    val firstPartText: String,
    val activityImage: DrawableResource,
    val activityText: String,
    val secondPartText: String,
    val illustrations: List<DrawableResource>,
    val description: String,
) {
    fun generatePages(isFirstPart: Boolean = true, maxCharsPerPage: Int = 200): List<StoryPage> {
        val text = if (isFirstPart) firstPartText else secondPartText
        val pages = mutableListOf<StoryPage>()
        val sentences = text.split(". ")
            .filter { it.isNotBlank() }
            .map { if (!it.endsWith(".")) "$it." else it }

        var currentPage = StringBuilder()
        var pageNumber = 1
        var illustrationIndex = 0

        for (sentence in sentences) {
            if (currentPage.length + sentence.length > maxCharsPerPage && currentPage.isNotEmpty()) {
                pages.add(StoryPage(
                    number = pageNumber,
                    content = currentPage.toString().trim(),
                    illustration = illustrations[illustrationIndex % illustrations.size]
                ))
                pageNumber++
                illustrationIndex++
                currentPage = StringBuilder()
            }
            currentPage.append("$sentence ")
        }

        if (currentPage.isNotEmpty()) {
            pages.add(StoryPage(
                number = pageNumber,
                content = currentPage.toString().trim(),
                illustration = illustrations[illustrationIndex % illustrations.size]
            ))
        }

        return pages
    }
}