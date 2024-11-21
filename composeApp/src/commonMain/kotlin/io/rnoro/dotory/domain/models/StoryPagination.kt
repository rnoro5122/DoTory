data class DynamicStoryPage(
    val content: String,
    val imageIndex: Int
)


object StoryPagination {
    fun createPages(
        fullText: String,
        totalImages: Int,
        maxCharsPerPage: Int = 150
    ): List<DynamicStoryPage> {
        val sentences = fullText.split(". ")
            .filter { it.isNotBlank() }
            .map { if (!it.endsWith(".")) "$it." else it }

        val pages = mutableListOf<DynamicStoryPage>()
        var currentPage = StringBuilder()
        var currentImageIndex = 0

        for (sentence in sentences) {
            if (currentPage.length + sentence.length > maxCharsPerPage && currentPage.isNotEmpty()) {
                pages.add(DynamicStoryPage(
                    content = currentPage.toString().trim(),
                    imageIndex = currentImageIndex
                ))
                currentPage = StringBuilder()
                currentImageIndex = (currentImageIndex + 1) % totalImages
            }
            currentPage.append("$sentence ")
        }

        if (currentPage.isNotEmpty()) {
            pages.add(DynamicStoryPage(
                content = currentPage.toString().trim(),
                imageIndex = currentImageIndex
            ))
        }

        return pages
    }
}