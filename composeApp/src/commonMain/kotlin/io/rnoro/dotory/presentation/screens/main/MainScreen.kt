package io.rnoro.dotory.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.boy_icon
import io.rnoro.dotory.presentation.components.BookCarousel
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import dotory.composeapp.generated.resources.christmas

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
    var selectedTab by remember { mutableStateOf(0) }

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
            carouselTitleSize = 20.sp
        )
        else -> UiSizes(
            profileIconSize = 64.dp,
            profileTextSize = 20.sp,
            subTitleSize = 24.sp,
            mainTitleSize = 40.sp,
            titleBottomPadding = 16.dp,
            topPadding = 40.dp,
            contentPadding = 30.dp,
            carouselTitleSize = 24.sp
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header section with profile and title
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

            // Tab layout
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier
                    .padding(top = 24.dp, end = 8.dp)
                    .fillMaxWidth(),
                containerColor = Color(0xFFFcc47e),
                contentColor = Color(0xFFFFFFFF),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFFFA9270)
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    text = {
                        Text(
                            "우리들의 이야기",
                            color = if (selectedTab == 0) Color(0xFFFFFFFF) else Color(0xFF212121)
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    text = {
                        Text(
                            "특별한 이야기",
                            color = if (selectedTab == 1) Color(0xFFFFFFFF) else Color(0xFF212121)
                        )
                    }
                )
            }

            // Content based on selected tab
            when (selectedTab) {
                0 -> {
                    CommunityBoardContent(
                        uiState = uiState,
                        uiSizes = uiSizes,
                        windowSizeClass = windowSizeClass,
                        navigationViewModel = navigationViewModel,
                        navController = navController
                    )
                }
                1 -> {
                    SpecialBookContent(
                        uiState = uiState,
                        uiSizes = uiSizes,
                        windowSizeClass = windowSizeClass,
                        navigationViewModel = navigationViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun SpecialBookContent(
    uiState: MainViewModel.MainUiState,
    uiSizes: UiSizes,
    windowSizeClass: WindowSizeClass,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = uiSizes.contentPadding)
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        // Content Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Left side - Image
            Card(
                modifier = Modifier
                    .weight(1.5f)
                    .height(400.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.christmas),
                    contentDescription = "Special Story Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Right side - Text and Button
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "12월의 특별한 이야기",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFFA9270)
                    )
                    Text(
                        text = "북극의 빙하가 녹아 산타마을이 물에 잠기고 말았어요!\n\n곧 다가올 크리스마스에 산타가 선물을 나누어줄 수 있도록 도와주세요!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 35.sp
                    )
                }
                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = { navigationViewModel.navigateToFairyTale(
                        navController = navController,
                        storyId = "christmas",
                        isLlmMode = false,
                        topic = "speical"
                    )},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFA9270)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "산타를 도와주러 가보자고!",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CommunityBoardContent(
    uiState: MainViewModel.MainUiState,
    uiSizes: UiSizes,
    windowSizeClass: WindowSizeClass,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = uiSizes.contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Popular Books Section with reduced size
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "최근 가장 인기가 있었던 책들이예요",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BookCarousel(
                        books = uiState.popularBooks,
                        windowSizeClass = windowSizeClass,
                        modifier = Modifier.fillMaxWidth(),
                        onBookClick = { book ->
                            navigationViewModel.navigateToFairyTale(navController, book.id, isFromBookshelf = true)
                        }
                    )
                }
            }
        }

        // Shared Books Section Header
        item {
            Text(
                text = "이야기 자랑하기",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }

        // Grid of Shared Books
        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height((uiState.sharedBooks.size / 3 + 1) * 180.dp)
            ) {
                items(uiState.sharedBooks) { book ->
                    SharedBookCard(
                        book = book,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SharedBookCard(
    book: MainViewModel.SharedBook,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.7f)  // Create a nice vertical card ratio
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Book Cover
            Image(
                painter = painterResource(book.coverImage),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Book Information
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "by ${book.author}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9d9fe6),
                        maxLines = 1
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Likes",
                            tint = Color(0xFFefaca7),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "${book.likes}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFefaca7)
                        )
                    }
                }
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