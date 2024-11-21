package io.rnoro.dotory.domain.models

import dotory.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

object TopicResources {
    private val adventureTopic = Topic(
        id = "adventure",
        title = "모험",
        description = "자연과 함께하는 신나는 모험을 떠나보아요",
        imageResource = Res.drawable.book_fantasy1,
        subTopics = listOf(
            "숲 속 탐험",
            "바다 모험",
            "산 정복하기",
            "동물 친구들과의 여행"
        )
    )

    private val environmentTopic = Topic(
        id = "environment",
        title = "환경보호",
        description = "지구를 지키는 특별한 이야기를 만들어보아요",
        imageResource = Res.drawable.book_classic1,
        subTopics = listOf(
            "분리수거 대작전",
            "플라스틱 없는 하루",
            "에너지 절약 일기",
            "깨끗한 지구 만들기"
        )
    )

    private val familyTopic = Topic(
        id = "family",
        title = "가족",
        description = "가족과 함께하는 따뜻한 이야기를 만들어보아요",
        imageResource = Res.drawable.book_family1,
        subTopics = listOf(
            "가족 캠핑",
            "친환경 식사",
            "환경 지킴이 가족",
            "주말 농장 체험"
        )
    )

    private val fantasyTopic = Topic(
        id = "fantasy",
        title = "판타지",
        description = "마법의 힘으로 환경을 지켜보아요",
        imageResource = Res.drawable.book_fantasy3,
        subTopics = listOf(
            "마법의 재활용",
            "환경 지킴이 마법사",
            "요정들의 숲",
            "생명의 나무 이야기"
        )
    )

    // 모든 주제 목록
    val allTopics = listOf(
        adventureTopic,
        environmentTopic,
        familyTopic,
        fantasyTopic
    )
}

data class Topic(
    val id: String,
    val title: String,
    val description: String,
    val imageResource: DrawableResource,
    val subTopics: List<String>
)