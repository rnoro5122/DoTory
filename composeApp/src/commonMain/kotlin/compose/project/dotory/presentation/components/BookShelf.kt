package compose.project.dotory.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import compose.project.dotory.domain.models.BookResources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookShelf(
    images: List<DrawableResource>,
    bookNames: List<String>,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(230.dp)
    ) {
        Image(
            painter = painterResource(
                resource = BookResources.bookshelfImage
            ),
            contentDescription = null,
            modifier = Modifier
                .width(380.dp)
                .height(200.dp)
                .padding(horizontal = 5.dp)
                .align(Alignment.BottomCenter),
        )
        LazyRow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .clip(RectangleShape)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(images.zip(bookNames)) { (image, name) ->
                BookItem(
                    image = image,
                    name = name,
                    onBookClick = { navController.navigate("DetailScreen") }
                )
            }
        }
    }
}

@Composable
private fun BookItem(
    image: DrawableResource,
    name: String,
    onBookClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(110.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.clickable(onClick = onBookClick)
            ) {
                Image(
                    painter = painterResource(resource = image),
                    contentDescription = null,
                    modifier = Modifier
                        .width(107.dp)
                        .height(135.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(resource = BookResources.bookSideImage),
                    contentDescription = null,
                    modifier = Modifier
                        .width(110.dp)
                        .height(140.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = name,
                fontSize = 12.sp,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}