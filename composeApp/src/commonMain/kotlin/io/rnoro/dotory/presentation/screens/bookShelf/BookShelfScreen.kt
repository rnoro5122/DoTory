package io.rnoro.dotory.presentation.screens.bookShelf

import Genre
import StoryBook
import StoryBookResources
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.components.BookCard
import io.rnoro.dotory.presentation.navigation.NavigationViewModel

@Composable
expect fun WindowSizeWrapper(content: @Composable (WindowSizeClass) -> Unit)

@Composable
fun BookShelfScreen(
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    WindowSizeWrapper { windowSizeClass ->
        BookShelfScreenContent(
            windowSizeClass = windowSizeClass,
            viewModel = navigationViewModel,
            navController = navController
        )
    }
}

@Composable
fun BookShelfScreenContent(
    windowSizeClass: WindowSizeClass,
    viewModel: NavigationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val booksByGenre = remember {
        StoryBookResources.allBooks.groupBy { it.genre }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 24.dp, bottom = 100.dp)
    ) {
        Text(
            text = "나의 책장",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        booksByGenre.forEach { (genre, books) ->
            if (books.isNotEmpty()) {
                GenreSection(
                    genre = genre,
                    books = books,
                    windowSizeClass = windowSizeClass,
                    onBookClick = { book ->
                        viewModel.navigateToFairyTale(navController, book.id, isFromBookshelf = true)
                    }
                )
            }
        }
    }
}

@Composable
private fun GenreSection(
    genre: Genre,
    books: List<StoryBook>,
    windowSizeClass: WindowSizeClass,
    onBookClick: (StoryBook) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 장르 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = genre.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = genre.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${books.size}권",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // 카드 크기 설정
        val cardSize = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> 240.dp
            WindowWidthSizeClass.Medium -> 280.dp
            else -> 400.dp
        }

        // LazyRow로 책 목록 표시
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            items(books.size) { index ->
                BookCard(
                    book = books[index],
                    modifier = Modifier
                        .width(cardSize)
                        .height(cardSize),
                    onClick = { onBookClick(books[index]) }
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}