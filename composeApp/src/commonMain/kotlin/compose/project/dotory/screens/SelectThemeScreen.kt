package compose.project.dotory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.many_books
import dotory.composeapp.generated.resources.lights
import org.jetbrains.compose.resources.painterResource

@Composable
fun SelectThemeScreen(navController: NavController, selectedItem: MutableState<Int>) {
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
            Spacer(modifier = Modifier.height(200.dp))
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
                    onClick = { navController.navigate("FirstStoryScreen") },
                    modifier = Modifier.height(50.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                ) {
                    Text("판타지", color = Color.White)
                }
                Spacer(modifier = Modifier.width(80.dp))
                Button(
                    onClick = { navController.navigate("FirstStoryScreen") },
                    modifier = Modifier.height(50.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("모험", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("FirstStoryScreen") },
                    modifier = Modifier.height(50.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                ) {
                    Text("로맨스", color = Color.White)
                }
                Spacer(modifier = Modifier.width(80.dp))
                Button(
                    onClick = { navController.navigate("FirstStoryScreen") },
                    modifier = Modifier.height(50.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("일상", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("FirstStoryScreen") },
                    modifier = Modifier.height(50.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                ) {
                    Text("코믹", color = Color.White)
                }
                Spacer(modifier = Modifier.width(80.dp))
                Button(
                    onClick = { navController.navigate("FirstStoryScreen") },
                    modifier = Modifier.height(50.dp).width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("추리", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
            Button(
                onClick = { /* 새로고침 기능 구현 */ },
                modifier = Modifier.height(50.dp).width(120.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("새로고침", color = Color.White)
            }
        }
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