import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

data class StoryBook(
    val id: String,
    val title: String,
    val genre: BookGenre,
    val coverImage: DrawableResource,
    val firstPartText: String,
    val secondPartText: String,
    val illustrations: List<DrawableResource>,
    val description: String,
) {
    fun generatePages(isFirstPart: Boolean = true, maxCharsPerPage: Int = 150): List<StoryPage> {
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

data class StoryPage(
    val number: Int,         // 페이지 번호
    val content: String,     // 페이지 내용
    val illustration: DrawableResource  // 삽화
)

// 3. 장르 정의 (enum으로 관리)
enum class BookGenre(val displayName: String) {
    FANTASY("판타지"),
    CLASSIC("고전"),
    FAMILY("가족"),
    ADVENTURE("모험"),
    ENVIRONMENT("환경");

    companion object {
        fun fromString(value: String): BookGenre =
            values().find { it.name.lowercase() == value.lowercase() }
                ?: throw IllegalArgumentException("Invalid genre: $value")
    }
}

// 4. 모든 책 리소스 관리
object StoryBookResources {
    private object Images {
        val fantasy = listOf(
            Res.drawable.book_fantasy1,
            Res.drawable.book_fantasy2,
            Res.drawable.book_fantasy3,
            Res.drawable.book_fantasy4
        )

        val classic = listOf(
            Res.drawable.book_classic1,
            Res.drawable.book_classic2,
            Res.drawable.book_classic3,
            Res.drawable.book_classic4
        )
    }

    fun String.removeLineBreaks(): String {
        return this.split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString(" ")
    }

    // 개별 책 정의
    private val seedsJourney = StoryBook(
        id = "adventure",
        title = "작은 씨앗의 여행",
        genre = BookGenre.ADVENTURE,
        coverImage = Images.fantasy[0],
        description = "작은 씨앗이 바람을 타고 떠나는 멋진 모험 이야기",
        firstPartText = """
        깊은 숲 속의 커다란 나무 위, 작은 씨앗 하나가 바람에 실려 멀리 날아갔다. "새로운 땅으로 가볼까?" 씨앗은 두근거리는 마음으로 세상을 향해 첫발을 내디뎠다.
        첫 번째로 씨앗이 떨어진 곳은 뜨거운 사막이었다. "여긴 너무 건조해!" 씨앗은 작은 물방울을 찾으려 노력했지만, 뜨거운 태양 아래선 힘들었다. 다행히 한 밤중에 부는 바람이 씨앗을 다시 들어 올려 날아가게 했다.
        두 번째로 도착한 곳은 깊고 어두운 바다였다. "여긴 내가 뿌리를 내릴 곳이 아니야!" 파도에 휩쓸릴 뻔한 씨앗은 갈매기의 부리에 실려 새로운 장소로 떠났다.
    """.trimIndent().removeLineBreaks(),
        secondPartText = """
        마침내 씨앗은 부드러운 흙이 가득한 초록 들판에 내려앉았다. "여긴 딱 좋아!" 따뜻한 햇살과 촉촭한 비 덕분에 씨앗은 뿌리를 내리고 조금씩 자라났다.
        몇 해가 지나, 작은 씨앗은 아름다운 꽃이 되어 다시 씨앗들을 만들어냈다. "여러분도 새로운 여행을 떠나 보세요!" 씨앗은 바람에 실려 떠나는 친구들을 보며 미소 지었다.
        작은 씨앗의 여행은 끝이 아닌 새로운 시작이었다.
    """.trimIndent().removeLineBreaks(),
        illustrations = Images.fantasy
    )

    private val moonGift = StoryBook(
        id = "moon_gift",
        title = "달님의 선물",
        genre = BookGenre.FANTASY,
        coverImage = Images.classic[0],
        description = "달님이 전해주는 특별한 이야기",
        firstPartText = """
        깊은 숲 속의 커다란 나무 위, 작은 씨앗 하나가 바람에 실려 멀리 날아갔다. "새로운 땅으로 가볼까?" 씨앗은 두근거리는 마음으로 세상을 향해 첫발을 내디뎠다.
        첫 번째로 씨앗이 떨어진 곳은 뜨거운 사막이었다. "여긴 너무 건조해!" 씨앗은 작은 물방울을 찾으려 노력했지만, 뜨거운 태양 아래선 힘들었다. 다행히 한 밤중에 부는 바람이 씨앗을 다시 들어 올려 날아가게 했다.
        두 번째로 도착한 곳은 깊고 어두운 바다였다. "여긴 내가 뿌리를 내릴 곳이 아니야!" 파도에 휩쓸릴 뻔한 씨앗은 갈매기의 부리에 실려 새로운 장소로 떠났다.
    """.trimIndent().removeLineBreaks(),
        secondPartText = """
        마침내 씨앗은 부드러운 흙이 가득한 초록 들판에 내려앉았다. "여긴 딱 좋아!" 따뜻한 햇살과 촉촭한 비 덕분에 씨앗은 뿌리를 내리고 조금씩 자라났다.
        몇 해가 지나, 작은 씨앗은 아름다운 꽃이 되어 다시 씨앗들을 만들어냈다. "여러분도 새로운 여행을 떠나 보세요!" 씨앗은 바람에 실려 떠나는 친구들을 보며 미소 지었다.
        작은 씨앗의 여행은 끝이 아닌 새로운 시작이었다.
    """.trimIndent().removeLineBreaks(),
        illustrations = Images.classic
    )

    val allBooks = listOf(seedsJourney, moonGift)

    // 유용한 접근 메서드들은 그대로 유지
    fun getBookById(id: String): StoryBook? = allBooks.find { it.id == id }
    fun getBooksByGenre(genre: BookGenre): List<StoryBook> = allBooks.filter { it.genre == genre }
    val genres: List<BookGenre> = allBooks.map { it.genre }.distinct()
}