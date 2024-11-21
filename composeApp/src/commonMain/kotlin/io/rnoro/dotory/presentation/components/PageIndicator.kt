package io.rnoro.dotory.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) 
                MaterialTheme.colorScheme.primary
            else 
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
