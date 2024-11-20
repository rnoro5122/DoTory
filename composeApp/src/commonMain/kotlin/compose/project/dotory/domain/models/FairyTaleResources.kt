package compose.project.dotory.domain.models

import org.jetbrains.compose.resources.DrawableResource

object FairyTaleResources {
    val fairyTales = mapOf(
        "adventure" to FairyTale(
            title = "작은 씨앗의 여행",
            pages = listOf(
                FairyTalePage(
                    content = "옛날 옛적에, 작은 씨앗 하나가 있었어요. 이 씨앗은 바람을 타고 멀리멀리 날아가고 싶었답니다.",
                    imageResource = BookResources.bookGenres[0].books[0].image
                ),
                FairyTalePage(
                    content = "어느 날, 따뜻한 봄바람이 불어와 작은 씨앗을 하늘 높이 데려갔어요. 씨앗은 구름도 만나고, 새들과도 인사를 나누었답니다.",
                    imageResource = BookResources.bookGenres[0].books[1].image
                )
                // 더 많은 페이지 추가 가능
            )
        ),
        "environment" to FairyTale(
            title = "달님의 선물",
            pages = listOf(
                FairyTalePage(
                    content = "깊은 숲속 마을에 사이좋은 동물 친구들이 살고 있었어요. 토끼와 다람쥐, 곰과 여우는 매일 함께 놀며 즐거운 시간을 보냈답니다.",
                    imageResource = BookResources.bookGenres[1].books[0].image
                ),
                FairyTalePage(
                    content = "어느 날 밤, 달님이 동물 친구들에게 반짝이는 별빛 가루를 선물했어요. 별빛 가루는 친구들의 우정을 더욱 빛나게 만들어주었답니다.",
                    imageResource = BookResources.bookGenres[1].books[1].image
                )
            )
        )
        // 더 많은 동화 추가 가능
    )
}

data class FairyTale(
    val title: String,
    val pages: List<FairyTalePage>
)

data class FairyTalePage(
    val content: String,
    val imageResource: DrawableResource
)