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
    val id: String,
    val displayName: String,
    val description: String,
    val imageResource: DrawableResource,
    val subTopics: List<String>
) {
    ADVENTURE(
        id = "adventure",
        displayName = "모험",
        description = "동화 속 세상 속으로 신나는 모험을 떠나보아요",
        imageResource = Res.drawable.adventure_creation,
        subTopics = listOf("숲 속 탐험", "바다 모험", "산 정복하기", "동물 친구들과의 여행")
    ),
    ENVIRONMENT(
        id = "environment",
        displayName = "환경보호",
        description = "지구를 지키는 특별한 이야기를 만들어보아요",
        imageResource = Res.drawable.environment_creation,
        subTopics = listOf("분리수거 대작전", "플라스틱 없는 하루", "에너지 절약 일기", "깨끗한 지구 만들기")
    ),
    FANTASY(
        id = "fantasy",
        displayName = "판타지",
        description = "마법의 힘으로 새로운 세계를 만들어보아요",
        imageResource = Res.drawable.fantasy_creation,
        subTopics = listOf("마법의 재활용", "환경 지킴이 마법사", "요정들의 숲", "생명의 나무 이야기")
    ),
    ENGLISH(
        id = "english",
        displayName = "영어",
        description = "흥미로운 이야기로 영어를 배워보아요",
        imageResource = Res.drawable.english_creation,
        subTopics = listOf("영어 동화", "재미있는 영어 표현", "영어로 떠나는 여행", "환경을 영어로 말해요")
    ),
    CLASSIC(
        id = "classic",
        displayName = "고전",
        description = "고전 속 이야기로 옛날 생활을 체험해보아요",
        imageResource = Res.drawable.classic_creation,
        subTopics = listOf("역사적 배경", "교훈적인 이야기", "문학적 가치")
    ),
    HORROR(
        id = "scary",
        displayName = "공포",
        description = "무서운 이야기로 가득한 공포의 세계로 떠나보아요",
        imageResource = Res.drawable.horror_creation,
        subTopics = listOf("유령의 숲", "공포의 성", "사라진 마을")
    ),
    COMEDY(
        id = "comic",
        displayName = "코믹",
        description = "웃음으로 가득한 즐거운 이야기를 만들어보아요",
        imageResource = Res.drawable.comedy_creation,
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

        val bananaKingdom = BookResources(
            cover = Res.drawable.comedy_banana_ex,
            activity = Res.drawable.book_family2,
            illustrations = listOf(
                Res.drawable.book_family1,
                Res.drawable.book_family2,
                Res.drawable.book_family3,
                Res.drawable.book_family4
            )
        )

        val shadowTown = BookResources(
            cover = Res.drawable.horror_shadowvillage_ex,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val bananaTree = BookResources(
            cover = Res.drawable.comedy_banana_eo,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val ballonCommotion = BookResources(
            cover = Res.drawable.comedy_chicken_ex,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val sprout = BookResources(
            cover = Res.drawable.comedy_sprout_eo,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val lostChicken = BookResources(
            cover = Res.drawable.horror_lostthings_eo,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val cursedCastle = BookResources(
            cover = Res.drawable.horror_cursedcattle_ex,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val samAndChair = BookResources(
            cover = Res.drawable.english_chair_ex,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val fullTrashbin = BookResources(
            cover = Res.drawable.english_trashbin_eo,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val forestKey = BookResources(
            cover = Res.drawable.adventure_forestkey_ex,
            activity = Res.drawable.book_fantasy3,
            illustrations = listOf(
                Res.drawable.book_classic1,
                Res.drawable.book_classic2,
                Res.drawable.book_classic3,
                Res.drawable.book_classic4
            )
        )

        val skyWalk = BookResources(
            cover = Res.drawable.fantasy_skywalk_ex,
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
        ),
        BookData(
            id = "banana_tree",
            title = "시들어가는 바나나 나무",
            genre = Genre.COMEDY,
            coverImage = BookAssets.bananaTree.cover,
            description = "시들어가는 바나나 나무를 지켜라!",
            firstPartText = """바나나들만 사는 작은 왕국이 있었습니다. 이곳은 언제나 평화로웠고, 바나나들은 서로 돕고 사랑하며 행복하게 살아가고 있었죠. 왕국 곳곳에는 초록빛 바나나 나무들이 가득했고, 그 아래에서 바나나들은 마음껏 뛰놀며 익어가는 하루하루를 보냈습니다. 하지만 어느 날, 왕국에 이상한 일이 벌어졌습니다. 처음엔 작은 변화처럼 보였습니다. 왕국 곳곳에 조금씩 쓰레기가 보이기 시작한 겁니다. "어, 저기 뭔가 떨어져 있어!" 한 바나나가 말했지만, 누구도 대수롭지 않게 여겼습니다. 하지만 시간이 지나면서 쓰레기는 점점 쌓여갔고, 문제가 심각해지기 시작했습니다. 아름답던 바나나 나무들이 시들기 시작했고, 나뭇잎은 더 이상 초록빛을 띠지 않았습니다. 바나나 왕은 깊은 고민에 빠졌습니다. "이대로 가다간 왕국이 망할지도 몰라, 어떻게 해야 할까?" 그는 왕궁에서 밤새도록 생각했지만, 좋은 해결책이 떠오르지 않았습니다. 그때, 왕국에서 가장 똑똑하기로 유명한 바나나 박사가 나섰습니다
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "쓰레기를 주워 바나나 왕국을 도와주자!",
            secondPartText = """바나나들은 처음엔 반신반의했지만, 모두 박사의 말대로 해보기로 했습니다. 다음 날 아침부터 바나나들은 쓰레기를 하나씩 주워서 모으기 시작했습니다. 어떤 바나나는 길가에서 종이를, 또 어떤 바나나는 강가에 떠내려온 병을 주웠습니다. 심지어 작은 바나나 아이들까지 나서서 가족과 함께 쓰레기를 주웠습니다. 처음엔 변화가 보이지 않는 듯했지만, 며칠이 지나자 점점 달라지기 시작했습니다. 왕국 곳곳에 쌓였던 쓰레기들이 줄어들고, 나무들은 다시 초록빛을 되찾기 시작했습니다. 바나나 왕은 이 모습을 보고 감격스러워하며 말했습니다. "이 모든 것이 여러분의 노력 덕분입니다. 우리 왕국은 여러분의 작은 행동들로 다시 살아났어요." 기쁜 마음으로 왕은 왕국의 모든 바나나들에게 특별한 상을 수여했습니다. 그것은 바로 “초록빛 바나나 메달”이었죠. 메달을 받은 바나나들은 환호하며 서로를 칭찬했습니다. 그 후로도 바나나 왕국에서는 쓰레기를 버리지 않고, 깨끗한 환경을 유지하는 것이 중요한 법이 되었습니다. 이 소식은 멀리 떨어진 동물 친구들에게까지 전해졌습니다. 동물들도 바나나들의 환경 활동을 본받아 자신들의 숲과 강을 깨끗하게 만들기 시작했죠. 이렇게 해서 바나나 왕국의 작은 변화는 멀리멀리 퍼져나가, 모두가 행복한 세상을 만드는 데 기여하게 되었습니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.bananaTree.illustrations
        ),
        BookData(
            id = "ballon_commotion",
            title = "풍선 소동",
            genre = Genre.COMEDY,
            coverImage = BookAssets.ballonCommotion.cover,
            description = "욕심은 금물!",
            firstPartText = """외딴 오두막에 살고 있는 아이들이 있었어요. 아이들은 매일 풍선을 가지고 놀며, 풍선을 손으로 튕기며 뛰어놀고, 공중에 띄우며 웃음소리를 가득 채웠죠. 그런데 어느 날, 이상한 일이 벌어졌어요. 풍선이 갑자기 혼자서 움직이기 시작한 거예요! "푸푸푹!" 풍선은 점점 커지더니 집 안 곳곳에서 굴러다니며 이상한 소리를 내기 시작했어요. "풍선아, 왜 이러는 거야?!" 아이들은 걱정이 되기 시작했어요. "이러다 풍선이 터질지도 몰라!" 아이들은 풍선을 어떻게 해야 할지 몰라 발만 동동 굴렀습니다. 그때, 지혜로운 할머니가 다가오셨어요. "풍선은 너무 많이 불면 이렇게 커지고, 이상한 소리도 내지. 적당히 공기를 넣고 관리하면 오래 사용할 수 있단다." 아이들은 그제야 자신들이 너무 신나서 풍선에 공기를 너무 많이 불어 넣었다는 것을 깨달았어요. 아이들은 할머니의 도움을 받아 풍선에서 공기를 조금 빼기로 했어요. 풍선을 조심스레 잡고 공기를 천천히 빼기 시작했죠. "이제 됐어요!" 아이들은 풍선이 다시 적당한 크기로 돌아온 것을 보고 환호했어요. "다음엔 이렇게 되지 않도록 연습하자!"라고 말하며 적당히 공기를 넣는 방법을 배우기 시작했어요.
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "우리도 풍선을 한 번 불어볼까??",
            secondPartText = """풍선은 너무 작지도, 너무 크지도 않은 딱 알맞은 크기로 만들어졌어요. 아이들은 풍선이 튼튼해지고 더 오래 놀 수 있다는 걸 깨닫게 되었죠. 며칠 후, 아이들은 할머니에게 풍선을 보여주며 말했어요. "할머니, 이제 풍선을 잘 불 수 있게 되었어요! 할머니 덕분이에요." 할머니는 흐뭇한 미소를 지으며 말씀하셨어요. "그래, 모든 것은 적당한 것이 중요하단다. 풍선뿐만 아니라 우리 삶에서도 마찬가지란다. 물건들은 아껴 쓰고, 음식도 골고루 먹어야 해." 그날 이후, 아이들은 풍선을 보며 할머니의 가르침을 잊지 않았어요. "모든 것은 적당한 것이 가장 좋다"는 말을 항상 마음에 새기며, 일상에서 실천하려고 했죠. 아이들은 이제 다른 것들도 적당히 사용하는 법을 배웠어요. 집안의 물건들을 아껴 쓰게 되었고, 필요할 때만 쓰면서 물건을 남용하지 않기로 결심했죠. 풍선처럼 소중하게 다루기로 한 거예요. 과자도 너무 많이 먹지 않고, 놀이도 너무 오래 하지 않기로 했죠. 그 결과, 아이들은 이제 풍선뿐만 아니라, 모든 일에 있어서 균형을 맞추는 법을 배웠어요. 그 덕분에 집안은 항상 평화롭고, 아이들은 하루하루 더 행복하게 지낼 수 있었답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.ballonCommotion.illustrations
        ),
        BookData(
            id = "sprout",
            title = "콩나물 냄비의 비밀",
            genre = Genre.COMEDY,
            coverImage = BookAssets.sprout.cover,
            description = "요정을 도와 콩나물들을 살리자!",
            firstPartText = """여러 채소가 살고 있는 작은 마을이 있었습니다. 이 마을에는 요리를 정말 좋아하는 콩나물 요정이 살고 있었죠. 요정은 매일 아침 커다란 냄비에 물을 붓고 콩나물을 길렀어요. 마을의 모든 채소는 요정이 만든 맛있는 요리 덕분에 행복하게 지내고 있었답니다. 콩나물 요정은 매우 부지런한 요정이라서, 매일 콩나물을 키우는 일이 매우 즐거웠어요. 하지만 어느 날, 예상치 못한 일이 벌어졌습니다. 요정은 냄비에 물을 부으면서 무언가를 느꼈지만, 바쁜 나머지 신경을 쓰지 않았죠. 물이 넘칠 정도로 많이 붓고 나서야, 콩나물 요정은 그 문제가 무엇인지 깨달았습니다. 냄비의 물이 너무 많아서, 콩나물들이 물에 잠겨 숨을 쉴 수 없었어요. "이게 무슨 일이야! 콩나물이 숨을 쉬지 않아!" 요정은 냄비에서 콩나물을 꺼내고 주변을 둘러보며 큰 소리로 외쳤습니다. "도대체 뭐가 문제야?" 마을의 채소 친구들이 급히 달려와 상황을 살펴봤지만, 모두 당황해서 어떻게 해야 할지 몰랐어요. 마을의 채소 친구들은 서로 눈을 마주치며 걱정스러운 표정을 지었어요. "이렇게 계속 가면 콩나물이 죽어버릴지도 몰라!" 토마토가 떨리는 목소리로 말했죠. 브로콜리는 콩나물 요정을 다독이며 "우리는 어떻게든 이 문제를 해결할 수 있을 거야, 함께 방법을 찾자!"라고 말했어요.
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = " 화분의 식물에 물을 적당히 주어 콩나물 요정을 도와주자!",
            secondPartText = """채소 친구들은 물을 아껴 쓰는 놀이를 시작했어요. 매일 저녁 모여서 물을 아끼는 방법에 관해 이야기하고, 서로 다른 아이디어를 나누었죠. 예를 들어, 화분에 물을 줄 때 남은 물을 다른 식물에 나누어 주는 게임을 했고, 다른 채소들은 물을 적당히 사용하며 어떻게 하면 물을 절약할 수 있을지 실험 했어요. 이 과정을 통해 채소들은 물의 소중함을 더 깊이 이해하게 되었어요. 콩나물 요정은 채소 친구들의 도움 덕분에 물을 적당히 조절하는 법을 배웠고, 냄비 속 콩나물도 건강하게 자라기 시작했죠. 물을 너무 많이 주지 않고, 적당한 양을 맞추는 법을 알게 되자 콩나물은 숨을 쉴 수 있게 되었고, 점점 더 건강하게 자라나기 시작했답니다. 요정은 채소들의 도움에 감사하며 매일매일 물을 아끼는 데 집중했어요. 마을은 다시 산뜻하고 평화로운 곳으로 돌아갔어요. 냄비에서 나온 콩나물들이 싱그럽게 자라고, 요정은 자랑스럽게 마을 사람들에게 그 결과를 보여주었어요. 그리고 채소 마을에서는 매년 "물 절약 환경 축제"가 열려, 마을 사람들이 함께 모여 물을 아끼고 소중히 여기는 법을 모두가 즐겁게 배웠답니다. 이제 채소 마을에서는 물을 아끼는 일이 일상화되었고, 그 누구도 물 낭비를 하지 않게 되었답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.sprout.illustrations
        ),
        BookData(
            id = "lost_chicken",
            title = "사라진 치킨",
            genre = Genre.COMEDY,
            coverImage =  BookAssets.lostChicken.cover,
            description = "내 치킨이 어디갔지??",
            firstPartText = """어느 날, 초등학생 지호는 점심으로 할머니가 만들어준 맛있는 치킨을 가지고 왔답니다. 치킨을 들고 놀이터에 도착한 지호는 친구들에게 자랑하며 치킨을 꺼냈어요. 모두가 즐거운 마음으로 치킨을 먹을 준비를 하고 있었는데, 그 순간 정말 이상한 일이 벌어졌어요. 지호가 치킨을 한 입 먹으려던 바로 그 순간, 치킨이 갑자기 사라져 버린 거예요! "어디 갔지?" 지호는 깜짝 놀라며 주위를 살펴봤어요. 친구 수민과 재원도 치킨을 찾으며 이상한 표정을 지었어요. "혹시 귀신이 가져간 거 아닐까?" 수민이 농담처럼 말했어요. 지호는 그 말을 듣고 웃으며 "귀신이 치킨을 좋아한다고?"라고 말했지만, 치킨은 여전히 보이지 않았어요. 세 친구는 치킨을 찾기 위해 온 마을을 뒤지기 시작했어요. 나무 밑, 놀이터 벤치, 바닥 등 치킨이 숨을 수 있는 곳을 모두 뒤졌지만, 치킨은 찾을 수 없었죠. 모두가 당황하고 있을 때, 지나가던 고양이 미미가 입에 치킨 조각을 물고 멋지게 나타났어요. 지호는 미미를 보고 깜짝 놀라며 소리쳤죠. "미미! 너 치킨 훔친 거야?" 미미는 아무 말 없이 치킨을 먹으며 만족스러운 표정을 지었어요. "오, 이건 정말 맛있네!" 미미는 고백했죠. 지호는 화가 나면서도 고양이에게 말했어요. "미미, 치킨은 내가 가져오려고 했던 거야! 이렇게 치킨을 훔치는 건 안 돼!" 그러자 고양이는 잠시 멈추고 치킨을 지호에게 돌려주며 말했어요. "미안해, 배가 너무 고팠어."
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "음식을 다른사람에게 나누어 주자!",
            secondPartText = """미미는 잠시 생각하다가 치킨을 지호에게 돌려주었어요. "다음부터는 나도 나누어 먹을게!" 지호는 고양이의 사과를 받아들이며 고마움을 표현했어요. “알겠어, 미미. 하지만 다음부터는 나와 같이 나눠 먹자!” 그렇게 고양이는 치킨을 돌려주고 지호와 친구들은 다시 즐겁게 치킨을 나누어 먹기로 했답니다. 그 후, 지호는 친구들과 고양이 미미에게 중요한 교훈을 전했어요. "치킨은 우리 모두의 것이니까, 나눠 먹어야 해!" 지호는 미미에게 조금 더 나누며 먹자고 말했어요. 그날 이후, 지호와 친구들은 항상 음식을 나누어 먹는 것이 얼마나 중요한지 깨닫게 되었어요. 미미는 더 이상 치킨을 훔치지 않기로 마음먹었고, 모두 함께 음식을 나누는 즐거운 시간을 보냈답니다. 친구들은 함께 음식을 나누며 소중한 시간을 보낼 수 있었고, 고양이 미미는 그날부터 다른 사람들과 음식을 나누는 법을 배웠어요. 그날의 사건은 지호와 친구들에게 중요한 교훈을 남겼고, 미미도 이를 통해 배운 것을 실천하려고 했답니다. 모두 함께 나누는 것이 가장 즐겁다는 것을 깨달은 그들은, 그 이후로도 늘 서로 도우며 행복하게 지내게 되었어요.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.lostChicken.illustrations
        ),
        BookData(
            id = "sam_and_chair",
            title = "Sam과 의자",
            genre = Genre.ENGLISH,
            coverImage = BookAssets.samAndChair.cover,
            description = "물건을 소중히 다루자!",
            firstPartText = """Sam은 red chair를 가지고 있어요. 그 의자는 Sam이 가장 좋아하는 의자였죠. Sam은 의자에 앉아 책을 읽거나, 게임을 하며 즐겁게 지냈어요. 의자에 앉으면 아주 편안하고, 기분이 좋았어요. 어느 날, Sam은 방에서 신나게 뛰어놀고 있었어요. Sam은 "jump!"를 하며 의자 위에서 뛰었어요. 그러다가 의자에서 이상한 소리가 나기 시작했어요. "SQUEAK!" 의자가 시끄럽게 소리 났어요! Sam은 깜짝 놀라면서도 웃었어요. "내 의자가 funny 소리를 내네!"라고 말했어요. Sam은 그 소리가 재미있어서 더 뛰어보았어요. 그런데 이번엔 의자가 더 큰 소리를 냈어요. "SQUEEEEAK!" 엄청 큰 소리가 나자, Sam의 엄마가 방으로 들어왔어요. 엄마는 의자에서 뛰고 있는 Sam을 보고는 걱정스러운 표정을 지었어요. "Sam, 의자는 sit 하는 거지, jump 하는 게 아니란다," 엄마가 말했어요. Sam은 엄마의 말을 듣고 고개를 끄덕였어요. "알겠어요, 엄마!" 하지만 Sam은 여전히 의자가 좋아서, 이제 어떻게 해야 할지 고민이 되었어요.
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "Sam에게 의자 다루는 방법을 알려주자!",
            secondPartText = """엄마는 Sam을 보고 미소 지으며 말했어요. "우리 같이 고쳐보자!" 엄마는 의자를 잘 다루는 방법을 하나씩 알려주었어요. "Sit softly. 의자에 앉을 때는 조심스럽게 천천히 앉아야 해. 의자가 happy 해지니까!" Sam은 이제 의자에 앉을 때 더 조심스럽게 앉기로 결심했어요. "Keep it clean. 의자 위에는 toy를 놓지 않도록 해. 의자 위에 아무것도 올리지 말자," 엄마가 말했어요. Sam은 장난감을 의자 위에 놓지 않기로 했어요. 마지막으로 엄마는 "Say thank you. 도와준 물건에 항상 고마워해야 해. 의자에게도 고마워하자!"라고 말했어요. Sam은 "Thank you, chair!"라고 말하고, 의자에 앉았어요. Sam은 의자에게 매일 고맙다고 말하기로 결심했어요. "Thank you for being so comfortable." Sam은 의자에게 말했어요. 그 후로 의자는 소리를 내지 않았고, Sam은 의자에 앉을 때마다 고마운 마음을 가지고 조심스럽게 사용했어요. 의자는 다시 행복하고 조용히 있었고, Sam도 의자에게 더욱 사랑을 주게 되었답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.samAndChair.illustrations
        ),
        BookData(
            id = "full_trashbin",
            title = "배부른 Trash Bin",
            genre = Genre.ENGLISH,
            coverImage =BookAssets.fullTrashbin.cover,
            description = "배부른 Trash Bin을 도와주자!",
            firstPartText = """Lily는 big trash bin을 가지고 있었어요. 그 쓰레기통은 항상 꽉 차 있었죠. Lily는 집에서 쓰레기를 버릴 때마다 쓰레기통에 넣으려고 했지만, 그 통은 점점 더 가득 차기만 했어요. 어느 날, Lily가 방에서 놀고 있을 때, 갑자기 쓰레기통에서 이상한 소리가 들렸어요. "I can't eat anymore!" 쓰레기통이 말했어요! Lily는 깜짝 놀라서 쓰레기통을 바라보며 말했어요. "The trash bin is talking!" 그때, 쓰레기통이 흔들리며 또다시 말했어요. "Please help me! I'm too full!" Lily는 당황했어요. "Oh no! What should I do?" 쓰레기통이 말을 해서 도와줘야 한다는 걸 깨달은 Lily는 아무것도 할 수 없다는 생각에 아빠에게 달려갔어요. "Dad, what should I do?" Lily는 아빠에게 물었어요. 아빠는 웃으면서 Lily를 다독였어요. "Don't worry, we can fix this together. 쓰레기통을 깨끗하게 하고, recycle 하면 돼!"
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "쓰레기를 분리수거하고, 재활용해서 Lily를 도와주자!",
            secondPartText = """아빠는 Lily에게 쓰레기를 정리하는 방법을 하나씩 알려주었어요. "Sort your trash. paper, plastic, cans은 따로따로 버리면 좋아." Lily는 종이와 플라스틱, 캔을 분리해서 버리기 시작했어요. 그리고 아빠는 또 말했어요. "Reuse things. bottles이나 boxes는 다시 사용할 수 있어. 버리기 전에 한 번 더 생각해 보자!" Lily는 병과 상자를 다시 사용할 방법을 생각하며 정리했어요. 마지막으로 아빠는 "Save food. 음식을 남기지 말고 꼭 먹으면 좋겠어!"라고 말했어요. Lily는 음식을 남기지 않기로 결심했어요. "음식을 아껴서 먹으면 쓰레기도 줄어들 거야!" Lily는 아빠와 함께 recycling을 하고, 음식도 남기지 않으려고 노력했어요. 그렇게 열심히 쓰레기를 정리하자, 쓰레기통이 기뻐하며 말했어요. "Thank you, Lily! 이제 더 이상 배부르지 않아!" Lily는 웃으며 말했어요. "우리 집이 더 깨끗해졌어! 이제 쓰레기도 적게 나오고, 재활용도 잘했으니, 모두가 행복해!" 그 후로 Lily는 쓰레기를 잘 정리하며 재활용도 열심히 했답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.fullTrashbin.illustrations
        ),
        BookData(
            id = "cursed_castle",
            title = "마녀와 저주받은 성",
            genre = Genre.HORROR,
            coverImage = BookAssets.cursedCastle.cover,
            description = "저주받은 성에 듣어간 알렉스의 이야기",
            firstPartText = """옛날, 작은 마을 외곽에는 검은 장미의 정원이 있었어요. 한때 가장 아름다운 장미가 피던 곳이었지만, 마을 사람들이 마녀를 쫓아낸 뒤로 저주받은 장소가 되었답니다. 사람들은 말했죠. “검은 장미를 꺾는 자는 마녀의 저주를 받아 영원히 성에 갇히게 될 거야.” 이 소문 때문에 아무도 정원에 가까이 가지 않았어요. 하지만 호기심 많은 소년 알렉스는 이를 단순한 전설이라 여기며 믿지 않았어요. “검은 장미를 꺾어도 아무 일도 일어나지 않을 거야.” 알렉스는 친구들과 내기를 하고, 직접 검은 장미를 꺾으러 정원으로 들어갔어요. 정원은 으스스했고 차가운 바람이 불고 있었어요. 그가 손을 뻗어 검은 장미를 꺾는 순간, 차가운 손이 그의 팔을 잡았습니다. 그 순간, 정원에 마녀의 목소리가 울렸어요. “나의 장미를 꺾은 자여… 너의 욕심이 너를 이곳에 묶을 것이다.” 알렉스는 깜짝 놀랐지만, 이미 저주받은 성 안으로 빨려 들어가고 말았답니다. 성은 황폐했고 어둡게 빛나는 기운으로 가득 차 있었어요.
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "알렉스에게 용기를 주는 편지를 써주자!",
            secondPartText = """성 안에서 마녀를 마주친 알렉스는 공포를 참고 용기를 내어 물었어요. “왜 저를 가둔 거예요? 제가 무엇을 잘못했나요?” 마녀는 깊은 한숨과 함께 과거를 이야기했어요. “옛날에 이 정원은 마을 사람들에게 열린 아름다운 곳이었지. 하지만 그들은 나를 배척하고 내 정원을 망쳤어. 아무도 나를 기억하려 하지 않았지. 나는 단지 나를 잊지 말아 달라고 이 저주를 건 것이다.” 알렉스는 마녀의 슬픔을 이해하며 진심으로 말했어요. “당신을 잊었던 건 우리 마을의 잘못이었어요. 제가 이 이야기를 마을에 전하고, 당신을 다시는 잊히지 않게 할게요.” 알렉스의 진심을 들은 마녀는 점차 표정을 풀며 성 전체를 푸른 빛으로 물들였어요. “너의 용기가 나를 해방시켰다. 이제 너는 자유로워도 좋아.” 알렉스는 성을 떠나 마을로 돌아왔어요. 그는 마녀의 이야기를 모두에게 전하며 정원을 돌보는 수호자가 되었답니다. 마을 사람들은 정원을 보호하며 마녀의 이야기를 기억했고, 정원은 다시 평화롭고 아름다운 장소로 남았어요. 마녀의 이름은 더 이상 저주가 아닌 감사로 남게 되었답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.cursedCastle.illustrations
        ),
        BookData(
            id = "forest_key",
            title = "숲의 영혼을 꺠우는 열쇠",
            genre = Genre.ADVENTURE,
            coverImage = BookAssets.forestKey.cover,
            description = "라이언과 엘라의 열쇠를 찾기위한 모험",
            firstPartText = """옛날, 깊은 숲 속에 영혼의 숲이라는 신비로운 장소가 있었어요. 전설에 따르면, 숲의 중심에는 영혼의 열쇠가 숨겨져 있고, 그것을 찾는 자는 어떤 위기든 극복할 힘을 얻게 된다고 했죠. 소년 라이언과 소녀 엘라는 이 전설을 듣고 모험을 떠나기로 결심했어요. “숲의 열쇠를 찾으면, 우리 마을을 도울 수 있을지도 몰라!” 숲으로 들어선 두 사람은 처음에는 반짝이는 햇살과 푸르른 나무들에 감탄했어요. 하지만 점점 길은 험난해졌고, 숲은 점점 어두워졌죠. 두 사람은 숲 속 깊은 곳에서 이상한 돌문에 도달했어요. 돌문에는 글귀가 새겨져 있었습니다. “열쇠를 얻고 싶다면, 너희의 용기를 증명하라. 진정한 용기는 마음에서 비롯된다.” 라이언은 고민하며 말했어요. “이 문을 통과하려면 우리가 어떤 시험을 받아야 할까? 위험하지 않을까?” 엘라는 미소 지으며 대답했어요. “전설에 따르면 열쇠를 찾으려면 진심을 보여야 한다고 했어. 우리도 할 수 있을 거야!” 두 사람은 돌문을 밀어 숲의 중심으로 향했습니다.
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "용기를 발휘했던 경험이 있다면 우리에게 알려줘!",
            secondPartText = """숲의 중심에 들어선 라이언과 엘라는 빛나는 연못을 발견했어요. 연못 한가운데에는 작은 석상이 있었고, 석상 위에는 열쇠처럼 생긴 빛나는 물체가 놓여 있었죠. 하지만 열쇠를 잡으려는 순간, 연못 주변에서 거대한 그림자가 나타났어요. 그림자는 두 사람을 내려다보며 깊은 목소리로 말했어요. “열쇠를 얻으려면 두려움에 맞서야 한다. 너희는 정말 준비되었는가?" 라이언은 처음엔 주춤했지만, 엘라가 용기를 내어 말했어요. “우리는 두렵지만, 진심으로 열쇠를 얻고 싶어요. 우리 마을을 돕고, 이 숲의 비밀을 전하고 싶습니다!” 그들의 진심이 그림자에게 닿자, 그림자는 천천히 사라지며 연못 위 열쇠가 환히 빛났습니다. 라이언과 엘라는 열쇠를 손에 넣었고, 숲은 환한 빛으로 가득 찼어요. 그들은 깨달았어요. “진정한 용기는 두려움을 인정하면서도 앞으로 나아가는 마음이야.” 마을로 돌아간 두 사람은 자신들의 이야기를 전하며, 용기의 중요성을 모두에게 알렸어요. 영혼의 숲은 전설 속 희망의 상징으로 남게 되었답니다.
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.forestKey.illustrations
        ),
        BookData(
            id = "sky_walk",
            title = "하늘을 걷는 아이, 리아",
            genre = Genre.FANTASY,
            coverImage = BookAssets.skyWalk.cover,
            description = "하늘을 걷고 싶어하던 한 아이의 이야기",
            firstPartText = """옛날 옛적, 그림자 구름이 하늘을 가려 태양과 별이 보이지 않는 작은 마을이 있었습니다. 이 마을의 사람들은 늘 우울했지만, 어린 소녀 리아는 하늘을 되찾겠다는 꿈을 품고 있었습니다. 전설에 따르면, 하늘 꼭대기에 있는 구름 신발을 찾아야만 구름을 걷고 문제를 해결할 수 있었습니다. 리아는 용기를 내어 여정을 떠났고, 바람의 계곡과 빛과 어둠의 동굴을 통과하며 구름 신발을 찾아냈습니다. 이 신발을 신은 리아는 구름 위를 걸으며 그림자 구름의 핵과 마주쳤습니다.  핵은 슬픔에서 태어난 존재로, 리아에게 물었습니다. “구름을 없앤다면 너는 하늘을 어떻게 사용할 것이냐?” 리아는 대답했습니다. “하늘은 모두의 것이에요. 우리는 그것을 보고 꿈꾸며 살아가야 해요.” 리아의 대답에 핵은 잠시 멈칫했지만, 여전히 슬픔을 떨치지 못하고 있었습니다.
                       """.trimIndent().removeLineBreaks(),
            activityImage = Res.drawable.book_fantasy3,
            activityText = "어떻게 하면 리아를 도울 수 있을까요?",
            secondPartText = """그때, 마을에서 희미한 빛이 올라오기 시작했습니다. 리아는 구름 위에서 발아래를 내려다보았습니다. 마을의 아이들이 큰 천에 그림을 그리고 있었습니다. 그 그림에는 별, 태양, 구름 위로 날아다니는 새들이 담겨 있었습니다. 아이들은 “우리가 다시 하늘을 볼 수 있길!”이라는 희망을 담아 그림을 그리고 있었죠. 마법처럼, 그 그림은 점점 밝게 빛나기 시작했습니다. 그림의 빛은 구름을 뚫고 하늘 위로 퍼져나갔습니다. 그림자 구름의 핵이 놀란 목소리로 말했습니다. “이건... 희망의 빛? 사람들은 아직 하늘을 잊지 않았다는 거야?” 리아는 아이들의 그림에서 힘을 얻어 구름 신발을 사용해 그림 위를 걸었습니다. 그러자 그림이 하늘에 떠오르더니 별빛처럼 반짝이는 마법의 에너지가 되었습니다. 그 에너지가 그림자 구름을 점점 밝게 물들였습니다. 핵은 점점 힘을 잃으며 이렇게 말했습니다. “이제 알겠어. 내가 만든 구름은 더 이상 필요 없어. 사람들의 희망과 사랑이 하늘을 되찾을 힘이 되었구나.” 리아는 마지막으로 신발의 힘을 사용해 그림자 구름을 완전히 걷어냈습니다. 구름이 사라지자 태양과 별, 푸른 하늘이 모습을 드러냈습니다. 마을 사람들은 기뻐하며 환호했고, 아이들의 그림은 하늘 한가운데 떠올라 모두에게 영감을 주는 별이 되었습니다. 리아는 구름 신발을 벗어 하늘의 가장 높은 곳에 걸어두며 말했습니다. “이제 하늘은 모두가 꿈꿀 수 있는 곳이야. 희망을 잃지 않는다면 하늘은 언제나 빛날 거야.”
            """.trimIndent().removeLineBreaks(),
            illustrations = BookAssets.skyWalk.illustrations
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