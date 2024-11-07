package compose.project.dotory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.bookPreview1
import dotory.composeapp.generated.resources.bookPreview2
import dotory.composeapp.generated.resources.bookPreview3
import dotory.composeapp.generated.resources.bookPreview4
import dotory.composeapp.generated.resources.bookPreview5
import dotory.composeapp.generated.resources.bookPreview6
import dotory.composeapp.generated.resources.bookshelf
import dotory.composeapp.generated.resources.bookshelf_icon
import dotory.composeapp.generated.resources.tree
import dotory.composeapp.generated.resources.tree_icon
import dotory.composeapp.generated.resources.write_icon
import org.jetbrains.compose.resources.painterResource


@Composable
fun MainViewTest() {
    val selectedItem = remember { mutableStateOf(0) }
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        NavHost(
            navController = navController,
            startDestination = "MainScreen"
        ) {
            composable("MainScreen") {
                MainScreen()
            }
            composable("ChooseThemeScreen") {
                ChooseThemeScreen(navController, selectedItem)
            }
            composable("GenerateStoryScreen") {
                GenerateStoryScreen(navController, selectedItem)
            }
        }

        // Showing BottomNavigationBar everywhere except "generate-story" screen
        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        if (currentBackStackEntry?.destination?.route != "GenerateStoryScreen") {
            BottomNavigationBar2(selectedItem, navController)
        }
    }
}

@Composable
fun BottomNavigationBar2(
    selectedItem: MutableState<Int>,
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            modifier = Modifier
                .height(100.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(30.dp)),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(Res.drawable.bookshelf_icon),
                        contentDescription = "bookshelf_icon"
                    )
                },
                label = {
                    Text(
                        "책장",
                        fontSize = 20.sp
                    )
                },
                selected = selectedItem.value == 0,
                onClick = {
                    selectedItem.value = 0
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(Res.drawable.tree_icon),
                        contentDescription = "홈"
                    )
                },
                label = {
                    Text(
                        "나무",
                        fontSize = 20.sp
                    )
                },
                selected = selectedItem.value == 1,
                onClick = {
                    selectedItem.value = 1
                    navController.navigate("MainScreen") {
                        popUpTo("MainScreen") { inclusive = true }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(Res.drawable.write_icon),
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        "이야기 생성",
                        fontSize = 20.sp
                    )
                },
                selected = selectedItem.value == 2,
                onClick = {
                    selectedItem.value = 2
                    navController.navigate("ChooseThemeScreen") {
                        popUpTo("MainScreen")
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun MainScreen() {
    val mainPainter = painterResource(
        resource = Res.drawable.tree
    )
    val bookshelfPainter = painterResource(
        resource = Res.drawable.bookshelf
    )
    val overlayImages = listOf(
        Res.drawable.bookPreview1,
        Res.drawable.bookPreview2,
        Res.drawable.bookPreview3,
        Res.drawable.bookPreview4,
        Res.drawable.bookPreview5,
        Res.drawable.bookPreview6
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            painter = mainPainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Text(
            text = "도토리(DoTory)",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(250.dp)
        ) {
            Image(
                painter = bookshelfPainter,
                contentDescription = null,
                modifier = Modifier.matchParentSize()
            )
            LazyRow(
                modifier = Modifier.align(Alignment.Center).width(280.dp).height(150.dp)
                    .clip(RectangleShape),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(overlayImages) { imageRes ->
                    Image(
                        painter = painterResource(resource = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .width(90.dp)
                            .height(150.dp), // 각 이미지 크기 설정
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun ChooseThemeScreen(navController: NavController, selectedItem: MutableState<Int>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            "주제를 골라주세요.",
            fontSize = 50.sp
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("GenerateStoryScreen") },
                modifier = Modifier.height(50.dp).width(120.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
            ) {
                Text("판타지", color = Color.White)
            }
            Spacer(modifier = Modifier.width(80.dp))
            Button(
                onClick = { navController.navigate("GenerateStoryScreen") },
                modifier = Modifier.height(50.dp).width(120.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("모험", color = Color.White)
            }
        }
        // 다른 버튼 추가...
    }
}

@Composable
fun GenerateStoryScreen(navController: NavController, selectedItem: MutableState<Int>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Generate Story", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                selectedItem.value = 1 // Update selectedItem value to 1 (MainScreen)
                navController.navigate("MainScreen")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("돌아가기")
        }
    }
}
