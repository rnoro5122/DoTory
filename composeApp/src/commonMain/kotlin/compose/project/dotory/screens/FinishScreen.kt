package compose.project.dotory.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.book_fantasy3
import dotory.composeapp.generated.resources.bookside
import dotory.composeapp.generated.resources.curtain
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource


@Composable
fun FinishScreen(navController: NavController, selectedItem: MutableState<Int>) {
    val bookTitle = rememberSaveable { mutableStateOf("") }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isLoading by remember { mutableStateOf(true) }

    Box {
        Column {
            Column(
                modifier = Modifier.weight(0.6f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LaunchedEffect(Unit) {
                    delay(3000) // 3초 딜레이
                    isLoading = false
                }
                Spacer(modifier = Modifier.height(100.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    // 이미지 컨텐츠
                    if (!isLoading) {
                        Box {
                            Image(
                                painter = painterResource(resource = Res.drawable.book_fantasy3),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(267.dp)
                                    .height(337.dp),
                                contentScale = ContentScale.Crop
                            )
                            Image(
                                painter = painterResource(resource = Res.drawable.bookside),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(275.dp)
                                    .height(350.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // 로딩 인디케이터
                    if (isLoading) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.matchParentSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "동화책 표지 생성중...",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.weight(0.4f).fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Surface(
                    shadowElevation = 2.dp,
                    shape = RoundedCornerShape(30.dp)
                ) {
                    TextField(
                        value = bookTitle.value,
                        onValueChange = { newValue ->
                            bookTitle.value = newValue
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "책 제목을 입력해주세요",
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = RoundedCornerShape(30.dp),
                        // 아래 속성들 추가
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide() // 키보드 숨기기
                            }
                        ),
                        singleLine = true  // 한 줄 입력으로 제한
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(30.dp),
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "후기를 남겨주세요",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        val rateCount = rememberSaveable { mutableStateOf(0) }

                        Row {
                            for (i in 1..5) {
                                IconButton(
                                    onClick = {
                                        rateCount.value = i
                                    }
                                ) {
                                    val tintColor = if (i <= rateCount.value) {
                                        Color.Yellow
                                    } else {
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    }
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star Icon",
                                        tint = tintColor
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            showDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Finish Icon",
                            tint = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(Res.drawable.curtain),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.weight(0.65f).fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(0.35f))
        }

        ReplayDialog(showDialog, navController, selectedItem)
    }
}


@Composable
fun ReplayDialog(
    showDialog: MutableState<Boolean>,
    navController: NavController,
    selectedItem: MutableState<Int>
) {
    if (showDialog.value) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = {
                            showDialog.value = false
                            navController.navigate("DetailScreen")
                            selectedItem.value = 0
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.secondaryContainer,
                            containerColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("네")
                    }
                    TextButton(
                        onClick = {
                            showDialog.value = false
                            navController.navigate(
                                "MainScreen"
                            )
                            selectedItem.value = 1
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.secondaryContainer,
                            containerColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("아니요, 책장에 보관할래요")
                    }
                }
            },
            title = {
                Text(
                    text = "이 책을 다시 읽어볼건가요?",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                )
            }
        )
    }
}
