package io.rnoro.dotory.domain.models

import org.jetbrains.compose.resources.DrawableResource

data class StoryPage(
    val number: Int,         // 페이지 번호
    val content: String,     // 페이지 내용
    val illustration: DrawableResource  // 삽화
)