import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.*
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

data class StoryPage(
    val number: Int,         // 페이지 번호
    val content: String,     // 페이지 내용
    val illustration: DrawableResource  // 삽화
)

// 3. 장르 정의 (enum으로 관리)
enum class Genre(
    val displayName: String,
    val description: String,
    val imageResource: DrawableResource,
    val subTopics: List<String>
) {
    ADVENTURE(
        displayName = "모험",
        description = "자연과 함께하는 신나는 모험을 떠나보아요",
        imageResource = Res.drawable.book_fantasy1,
        subTopics = listOf("숲 속 탐험", "바다 모험", "산 정복하기", "동물 친구들과의 여행")
    ),
    ENVIRONMENT(
        displayName = "환경보호",
        description = "지구를 지키는 특별한 이야기를 만들어보아요",
        imageResource = Res.drawable.book_classic1,
        subTopics = listOf("분리수거 대작전", "플라스틱 없는 하루", "에너지 절약 일기", "깨끗한 지구 만들기")
    ),
    FAMILY(
        displayName = "가족",
        description = "가족과 함께하는 따뜻한 이야기를 만들어보아요",
        imageResource = Res.drawable.book_family1,
        subTopics = listOf("가족 캠핑", "친환경 식사", "환경 지킴이 가족", "주말 농장 체험")
    ),
    FANTASY(
        displayName = "판타지",
        description = "마법의 힘으로 환경을 지켜보아요",
        imageResource = Res.drawable.book_fantasy3,
        subTopics = listOf("마법의 재활용", "환경 지킴이 마법사", "요정들의 숲", "생명의 나무 이야기")
    ),
    CLASSIC(
        displayName = "고전",
        description = "시간이 지나도 변하지 않는 이야기",
        imageResource = Res.drawable.book_classic2,
        subTopics = listOf("역사적 배경", "교훈적인 이야기", "문학적 가치")
    ),
    HORROR(
        displayName = "공포",
        description = "등골이 서늘해지는 이야기",
        imageResource = Res.drawable.book_fantasy4,
        subTopics = listOf("유령의 숲", "공포의 성", "사라진 마을")
    ),
    COMEDY(
        displayName = "코미디",
        description = "웃음과 재미가 가득한 이야기",
        imageResource = Res.drawable.book_classic3,
        subTopics = listOf("웃음 가득한 하루", "장난꾸러기 모험", "예기치 못한 유머")
    );
}


object StoryBookResources {
    object BookAssets {
        data class BookResources(
            val cover: DrawableResource,
            val activity: DrawableResource,
            val illustrations: List<DrawableResource>
        )

        // 각 책별 리소스 관리
        val seedJourney = BookResources(
            cover = Res.drawable.book_fantasy1,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_fantasy1,
                Res.drawable.book_fantasy2,
                Res.drawable.book_fantasy3,
                Res.drawable.book_fantasy4
            )
        )

        val moonGift = BookResources(
            cover = Res.drawable.book_classic1,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val bananaKingdom = BookResources(
            cover = Res.drawable.book_family1,
            activity = Res.drawable.book_family2,
            illustrations = listOf(
                Res.drawable.book_family1,
                Res.drawable.book_family2,
                Res.drawable.book_family3,
                Res.drawable.book_family4
            )
        )

        val shadowTown = BookResources(
            cover = Res.drawable.book_fantasy3,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )
    }

    fun String.removeLineBreaks(): String {
        return this.split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString(" ")
    }

    private val bookData = listOf(
        BookData(
            id = "seed_journey",
            title = "작은 씨앗의 여행",
            genre = Genre.ADVENTURE,
            coverImage = BookAssets.seedJourney.cover,
            description = "작은 씨앗이 바람을 타고 떠나는 멋진 모험 이야기",
            firstPartText = """
            깊은 숲 속의 커다란 나무 위, 작은 씨앗 하나가 바람에 실려 멀리 날아갔다. "새로운 땅으로 가볼까?" 씨앗은 두근거리는 마음으로 세상을 향해 첫발을 내디뎠다.
            첫 번째로 씨앗이 떨어진 곳은 뜨거운 사막이었다. "여긴 너무 건조해!" 씨앗은 작은 물방울을 찾으려 노력했지만, 뜨거운 태양 아래선 힘들었다. 다행히 한 밤중에 부는 바람이 씨앗을 다시 들어 올려 날아가게 했다.
            두 번째로 도착한 곳은 깊고 어두운 바다였다. "여긴 내가 뿌리를 내릴 곳이 아니야!" 파도에 휩쓸릴 뻔한 씨앗은 갈매기의 부리에 실려 새로운 장소로 떠났다.
            """.trimIndent().removeLineBreaks(),
                activityImage = Res.drawable.book_fantasy3,
                activityText = "씨앗이 떠나는 여행을 따라해보세요!",
                secondPartText = """
            마침내 씨앗은 부드러운 흙이 가득한 초록 들판에 내려앉았다. "여긴 딱 좋아!" 따뜻한 햇살과 촉촭한 비 덕분에 씨앗은 뿌리를 내리고 조금씩 자라났다.
            몇 해가 지나, 작은 씨앗은 아름다운 꽃이 되어 다시 씨앗들을 만들어냈다. "여러분도 새로운 여행을 떠나 보세요!" 씨앗은 바람에 실려 떠나는 친구들을 보며 미소 지었다.
            작은 씨앗의 여행은 끝이 아닌 새로운 시작이었다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.seedJourney.illustrations
        ),
        BookData(
            id = "moon_gift",
            title = "달님의 선물",
            genre = Genre.FANTASY,
            coverImage = BookAssets.moonGift.cover,
            description = "달님이 전해주는 특별한 이야기",
            firstPartText = """
            깊은 숲 속의 커다란 나무 위, 작은 씨앗 하나가 바람에 실려 멀리 날아갔다. "새로운 땅으로 가볼까?" 씨앗은 두근거리는 마음으로 세상을 향해 첫발을 내디뎠다.
            첫 번째로 씨앗이 떨어진 곳은 뜨거운 사막이었다. "여긴 너무 건조해!" 씨앗은 작은 물방울을 찾으려 노력했지만, 뜨거운 태양 아래선 힘들었다. 다행히 한 밤중에 부는 바람이 씨앗을 다시 들어 올려 날아가게 했다.
            두 번째로 도착한 곳은 깊고 어두운 바다였다. "여긴 내가 뿌리를 내릴 곳이 아니야!" 파도에 휩쓸릴 뻔한 씨앗은 갈매기의 부리에 실려 새로운 장소로 떠났다.
            """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "씨앗이 떠나는 여행을 따라해보세요!",
            secondPartText = """
            마침내 씨앗은 부드러운 흙이 가득한 초록 들판에 내려앉았다. "여긴 딱 좋아!" 따뜻한 햇살과 촉촭한 비 덕분에 씨앗은 뿌리를 내리고 조금씩 자라났다.
            몇 해가 지나, 작은 씨앗은 아름다운 꽃이 되어 다시 씨앗들을 만들어냈다. "여러분도 새로운 여행을 떠나 보세요!" 씨앗은 바람에 실려 떠나는 친구들을 보며 미소 지었다.
            작은 씨앗의 여행은 끝이 아닌 새로운 시작이었다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.moonGift.illustrations
        ),
        BookData(
            id = "banana_kingdom",
            title = "바나나 왕국의 큰 소동",
            genre = Genre.COMEDY,
            coverImage = BookAssets.bananaKingdom.cover,
            description = "달님이 전해주는 특별한 이야기",
            firstPartText = """
            바나나들만 사는 작은 왕국이 있습니다. 이곳은 늘 평화롭고, 바나나들끼리 잘 익어가는 행복한 곳이었죠. 그런데 어느 날, 바나나 왕국의 중심에서 이상한 소리가 들리기 시작했습니다. “뿌웅!” 
            “뭐지 폭탄인가?” 바나나 경찰관들이 소리를 듣고 황급히 출동했습니다. 하지만 놀랍게도 소리의 근원지는 왕국에서 가장 큰 바나나, 바나나 왕이었습니다! 
            “미안하네… 오늘 바람을 너무 많이 먹었나 봐…” 왕이 부끄러워하며 말했지만, 그 순간 또다시 소리가 터져 나왔습니다. “뿌웅!” 왕국 전체가 웃음바다가 되었습니다.
            하지만 문제는 거기서 끝나지 않았습니다. 바나나 왕의 “소리”는 점점 커지고, 심지어 왕국의 다른 바나나들도 따라 하기 시작한 겁니다.
            이제는 길을 걷는 모든 바나나가 “뿌웅!” 하는 소리를 내며 걷고 있었습니다. “이러다 우리 왕국의 평화가 사라지겠어!” 
            왕국 최고의 과학자, 바나나 박사가 해결책을 내놓기로 했습니다. 그는 바나나 왕에게 “더 이상 바람이 들어가지 않도록” 특별한 방법을 제안했는데, 그것은 바로…
            """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_family2,
            activityText = "스트레칭 하기",
            secondPartText = """
            스트레칭하는 것이었습니다. 왕은 다소 어이없었지만, 왕국의 평화를 위해 실험을 해보기로 했습니다. 매일 아침과 저녁에 간단한 스트레칭을 하며 몸을 움직였습니다. 이 방법은 효과적이었습니다.
            그날 이후, 바나나 왕국에서는 중요한 법이 생겼습니다. 바나나 왕국은 다시 평화를 찾았지만, 바나나들은 여전히 “뿌웅!” 소리로 서로를 놀리며 즐거운 하루를 보냈답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.bananaKingdom.illustrations
        ),
        BookData(
            id = "shadow_town",
            title = "그림자 마을의 비밀",
            genre = Genre.HORROR,
            coverImage = BookAssets.shadowTown.cover,
            description = "달님이 전해주는 특별한 이야기",
            firstPartText = """
            옛날, 외딴 산속에 위치한 그레이 헤이븐 마을에서는 밤마다 사람들이 사라지는 일이 벌어졌어요. 누군가 밤에 어둠 속을 걷다가 다시는 집으로 돌아오지 못했죠. 아이들은 무서운 이야기를 만들었어요. 
            “그림자가 사람들을 데려간대요. 한번 잡히면 다시는 못 돌아와요.” 마을 사람들은 이 이야기를 단순한 전설로 치부했지만, 매년 밤마다 한두 명씩 정말로 사라지는 일이 계속됐어요. 
            사람들은 공포에 떨며 밤이 되면 문을 꼭 잠그고 바깥을 내다보지 않았답니다. 소녀 린다는 사라진 사람들에 대해 강한 호기심을 품었어요. 그녀의 가장 친한 친구인 제이크도 이런 일에 관심이 많았죠. 
            “어른들은 진실을 두려워하지만, 우리가 알아내야 해. 도대체 왜 사람들이 사라지는 걸까?” 린다와 제이크는 마을 중심에 있는 오래된 저택을 조사하기로 결심했어요. 
            전설에 따르면 이 저택이 그림자와 관련이 있을지도 모른다는 소문이 있었기 때문이죠. 그렇게 들어간 저택은 황폐하고 어둡게 변해 있었어요. 창문은 깨졌고, 벽지는 낡아 찢겨 있었죠. 
            하지만 더 무서운 건, 저택의 벽과 바닥에 움직이는 그림자들이 있었어요. 이 그림자들은 마치 살아 있는 생물처럼 꿈틀거렸답니다. 린다와 제이크가 저택 안으로 들어서자, 그림자들은 천천히 움직이며 그들을 에워싸기 시작했어요. 
            “우리를 잊지 마라…” 그림자들이 속삭이며 두 사람 주위를 맴돌았어요. 제이크는 속삭임에 겁을 먹었지만, 린다는 굳건히 말했어요. “너희는 누구야? 왜 여기에 있는 거지?” 그림자들은 천천히 대답했어요. 
            “우리는 잊힌 영혼들이다. 우리를 기억하지 않는 한, 이 저주는 계속될 것이다…” 두 사람은 그림자들의 정체를 이해하려 노력했어요. 그러다 저택의 중심에 있는 고대의 거울을 발견했죠. 거울 속에는 사라진 사람들의 모습이 비치고 있었어요.
            """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "잊힌 누군가를 떠올리며 그에게 메시지를 보내기",
            secondPartText = """
            린다와 제이크는 그림자들의 말을 이해했어요. 사라진 사람들은 모두 마을 사람들에게 잊힌 존재들이었죠. 그들은 잊힌다는 두려움 속에서 그림자가 되어 저택에 묶여 있었던 거예요. 
            린다와 제이크는 거울 속 사람들의 이름을 하나하나 불렀어요. “우리 모두 너희를 기억해. 너희의 이야기를 다시 알릴게!” 이름이 불릴 때마다 거울 속 사람들은 서서히 현실로 돌아왔고, 그림자들은 점점 사라지며 밝은 빛으로 변했답니다. 
            사라졌던 사람들은 현실로 돌아왔고, 마을은 다시 평화를 되찾았어요. 사람들은 이 사건을 통해 서로를 소중히 여기고, 잊힌 사람들에게도 관심을 가지기로 결심했어요. 
            린다와 제이크는 이 모든 이야기를 기록하며 사람들에게 전했죠. “우리가 서로를 잊지 않는다면, 이 저주는 다시는 돌아오지 않을 거야.” 작은 씨앗의 여행은 끝이 아닌 새로운 시작이였어요.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.shadowTown.illustrations
        )
    )


private data class BookData(
        val id: String,
        val title: String,
        val genre: Genre,
        val coverImage: DrawableResource,
        val description: String,
        val firstPartText: String,
        val activityImage: DrawableResource,
        val activityText: String,
        val secondPartText: String,
        val illustrations: List<DrawableResource>
    )

    val allBooks: List<StoryBook> = bookData.map { data ->
        StoryBook(
            id = data.id,
            title = data.title,
            genre = data.genre,
            coverImage = data.coverImage,
            description = data.description,
            firstPartText = data.firstPartText,
            activityImage = data.activityImage,
            activityText = data.activityText,
            secondPartText = data.secondPartText,
            illustrations = data.illustrations
        )
    }

    fun getBookById(id: String): StoryBook? = allBooks.find { it.id == id }
}