package compose.project.dotory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.clouds
import dotory.composeapp.generated.resources.lights
import dotory.composeapp.generated.resources.many_books
import org.jetbrains.compose.resources.painterResource

@Composable
fun SelectThemeScreen(navController: NavController, selectedItem: MutableState<Int>) {
    var cloudsImage = painterResource(resource = Res.drawable.clouds)
    Box(
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.lights),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(200.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(resource = Res.drawable.many_books),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(250.dp))
            Text(
                "주제를 골라주세요.",
                fontSize = 40.sp,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(60.dp)
            ) {
                CloudButton("고전", cloudsImage, navController)
                CloudButton("판타지", cloudsImage, navController)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(60.dp)
            ) {
                CloudButton("공포", cloudsImage, navController)
                CloudButton("모험", cloudsImage, navController)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(60.dp)
            ) {
                CloudButton("추리", cloudsImage, navController)
                CloudButton("유머", cloudsImage, navController)
            }
        }
    }
}

@Composable
fun CloudButton(
    name: String,
    backgroundImage: Painter,
    navController: NavController
) {
    var isClicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(70.dp)
            .width(120.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    isClicked = !isClicked
                    navController.navigate("FirstStoryScreen")
                }
            )
            .offset(
//                x = if (isClicked) (-4).dp else 0.dp,
                y = if (isClicked) (-3).dp else 0.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            name,
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


fun Modifier.fillWidthOfParent(parentPadding: Dp) = this.then(
    layout { measurable, constraints ->
        // This is to force layout to go beyond the borders of its parent
        val placeable = measurable.measure(
            constraints.copy(
                maxWidth = constraints.maxWidth + 2 * parentPadding.roundToPx(),
            ),
        )
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    },
)

fun Modifier.fillHeightOfParent(parentPadding: Dp) = this.then(
    layout { measurable, constraints ->
        // 부모의 높이 경계를 초과하도록 설정
        val placeable = measurable.measure(
            constraints.copy(
                maxHeight = constraints.maxHeight + 2 * parentPadding.roundToPx(),
            ),
        )
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    },
)