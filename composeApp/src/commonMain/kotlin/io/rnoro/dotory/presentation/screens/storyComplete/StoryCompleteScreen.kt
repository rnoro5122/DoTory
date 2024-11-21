package io.rnoro.dotory.presentation.screens.storyComplete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleViewModel
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCompleteScreen(
    viewModel: FairyTaleViewModel,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController,
    storyId: String,
    modifier: Modifier = Modifier
) {
    val storyBook = viewModel.getCurrentStoryBook()
    var rating by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf(storyBook?.title ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),  // 32.dp에서 24.dp로 줄임
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "이야기 완성!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)  // 48.dp에서 32.dp로 줄임
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)  // height 대신 weight 사용
                .padding(horizontal = 24.dp),  // 32.dp에서 24.dp로 줄임
            horizontalArrangement = Arrangement.spacedBy(32.dp)  // 48.dp에서 32.dp로 줄임
        ) {
            // 왼쪽: 책 표지
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                storyBook?.coverImage?.let { coverImage ->
                    Image(
                        painter = painterResource(coverImage),
                        contentDescription = "Book Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // 오른쪽: 제목 입력 및 평가
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween  // spacedBy 대신 SpaceBetween 사용
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // 제목 입력
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("이야기 제목") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.headlineSmall,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    // 별점 평가
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "이야기는 어땠나요?",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            repeat(5) { index ->
                                IconButton(
                                    onClick = { rating = index + 1 },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "별점 ${index + 1}",
                                        modifier = Modifier.size(48.dp),
                                        tint = if (index < rating)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }

                // 저장 버튼
                Button(
                    onClick = {
                        navigationViewModel.navigateToHome(navController)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "이야기 저장하기",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}