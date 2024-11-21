package io.rnoro.dotory.presentation.screens.main

import StoryBook
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.boy_icon
import io.rnoro.dotory.presentation.components.BookCarousel
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
expect fun WindowSizeWrapper(content: @Composable (WindowSizeClass) -> Unit)

@Composable
fun MainScreen(
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    WindowSizeWrapper { windowSizeClass ->
        MainScreenContent(
            windowSizeClass = windowSizeClass,
            viewModel = MainViewModel(),
            navigationViewModel = navigationViewModel,
            navController = navController
        )
    }
}


@Composable
fun MainScreenContent(
    windowSizeClass: WindowSizeClass,
    viewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    val uiSizes = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> UiSizes(
            profileIconSize = 48.dp,
            profileTextSize = 16.sp,
            subTitleSize = 16.sp,
            mainTitleSize = 32.sp,
            titleBottomPadding = 8.dp,
            topPadding = 16.dp,
            contentPadding = 12.dp,
            carouselTitleSize = 16.sp
        )
        WindowWidthSizeClass.Medium -> UiSizes(
            profileIconSize = 56.dp,
            profileTextSize = 18.sp,
            subTitleSize = 20.sp,
            mainTitleSize = 36.sp,
            titleBottomPadding = 12.dp,
            topPadding = 20.dp,
            contentPadding = 16.dp,
            carouselTitleSize = 18.sp
        )
        else -> UiSizes(
            profileIconSize = 64.dp,
            profileTextSize = 20.sp,
            subTitleSize = 24.sp,
            mainTitleSize = 40.sp,
            titleBottomPadding = 16.dp,
            topPadding = 40.dp,
            contentPadding = 30.dp,
            carouselTitleSize = 20.sp
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = uiSizes.topPadding,
                        start = uiSizes.contentPadding,
                        end = uiSizes.contentPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProfileIconButton(
                    iconSize = uiSizes.profileIconSize,
                    textSize = uiSizes.profileTextSize
                )
                MainTitle(
                    fontSizeSubTitle = uiSizes.subTitleSize,
                    fontSizeMainTitle = uiSizes.mainTitleSize,
                    bottomPadding = uiSizes.titleBottomPadding
                )
            }

            // Recent Books Section
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "최근에 한경님이 만들었던 책들이예요!",
                    fontSize = uiSizes.carouselTitleSize,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .padding(horizontal = uiSizes.contentPadding),
                    textAlign = TextAlign.Center
                )
                BookCarousel(
                    books = uiState.allBooks,
                    windowSizeClass = windowSizeClass,
                    modifier = Modifier.fillMaxWidth(),
                    onBookClick = { book ->
                        navigationViewModel.navigateToFairyTale(navController, book.id, isFromBookshelf = true)
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileIconButton(
    iconSize: Dp,
    textSize: TextUnit
) {
    val profileImage = Res.drawable.boy_icon
    Column(verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(resource = profileImage),
            contentDescription = "Profile Icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(iconSize)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "한경",
                fontSize = textSize,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

@Composable
fun MainTitle(
    fontSizeSubTitle: TextUnit,
    fontSizeMainTitle: TextUnit,
    bottomPadding: Dp
) {
    Box(modifier = Modifier) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "세상에 단 하나뿐인 나만의 동화책",
                fontSize = fontSizeSubTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(bottom = bottomPadding)
            )
            Text(
                text = "도토리 Dotory",
                fontSize = fontSizeMainTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

data class UiSizes(
    val profileIconSize: Dp,
    val profileTextSize: TextUnit,
    val subTitleSize: TextUnit,
    val mainTitleSize: TextUnit,
    val titleBottomPadding: Dp,
    val topPadding: Dp,
    val contentPadding: Dp,
    val carouselTitleSize: TextUnit
)