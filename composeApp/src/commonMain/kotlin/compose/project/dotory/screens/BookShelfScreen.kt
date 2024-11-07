package compose.project.dotory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.bookPreview1
import dotory.composeapp.generated.resources.bookPreview2
import dotory.composeapp.generated.resources.bookPreview3
import dotory.composeapp.generated.resources.bookPreview4
import dotory.composeapp.generated.resources.bookPreview5
import dotory.composeapp.generated.resources.bookPreview6
import dotory.composeapp.generated.resources.bookshelf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookShelfScreen(navController: NavController, selectedItem: MutableState<Int>) {
    Column(
        modifier = Modifier.statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "나만의 서점",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        BookShelf(
            overlayImages = listOf(
                Res.drawable.bookPreview1,
                Res.drawable.bookPreview2,
                Res.drawable.bookPreview3,
                Res.drawable.bookPreview4,
                Res.drawable.bookPreview5,
                Res.drawable.bookPreview6
            )
        )
    }
}

@Composable
fun BookShelf(
    overlayImages: List<DrawableResource>
) {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.bookshelf),
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