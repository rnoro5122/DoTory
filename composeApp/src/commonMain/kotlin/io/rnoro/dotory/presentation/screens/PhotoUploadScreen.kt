//package compose.project.dotory.presentation.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil3.compose.AsyncImage
//import com.mohamedrejeb.calf.core.LocalPlatformContext
//import com.mohamedrejeb.calf.io.readByteArray
//import com.mohamedrejeb.calf.picker.FilePickerFileType
//import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
//import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
//import dotory.composeapp.generated.resources.Res
//import dotory.composeapp.generated.resources.books
//import dotory.composeapp.generated.resources.camera_icon
//import dotory.composeapp.generated.resources.gallery_icon
//import dotory.composeapp.generated.resources.squirrel_read
//import kotlinx.coroutines.launch
//import org.jetbrains.compose.resources.painterResource
//
//@Composable
//fun PhotoUploadScreen(navController: NavController) {
//    val scope = rememberCoroutineScope()
//    val context = LocalPlatformContext.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//    var byteArray by remember { mutableStateOf(ByteArray(0)) }
//    var showDialog by remember { mutableStateOf(false) }
//    var inputText by remember { mutableStateOf(TextFieldValue("")) }
//    var isImageUploaded by remember { mutableStateOf(false) }
//    val bookTitle = rememberSaveable { mutableStateOf("") }
//
//    val pickerLauncher = rememberFilePickerLauncher(
//        type = FilePickerFileType.Image,
//        selectionMode = FilePickerSelectionMode.Single,
//        onResult = { files ->
//            scope.launch {
//                files.firstOrNull()?.let {
//                    byteArray = it.readByteArray(context)
//                    isImageUploaded = true
//                }
//            }
//        }
//    )
//
//    Box(
//        modifier = Modifier
//            .fillHeightOfParent(40.dp)
//            .statusBarsPadding()
//            .fillMaxSize()
//    ) {
//        Row(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Image(
//                painter = painterResource(resource = Res.drawable.books),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(300.dp)
//                    .width(200.dp)
//            )
//            Image(
//                painter = painterResource(resource = Res.drawable.squirrel_read),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(300.dp)
//                    .width(200.dp)
//            )
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .statusBarsPadding(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Spacer(modifier = Modifier.height(50.dp))
//        Box(
//            modifier = Modifier
//                .size(300.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(MaterialTheme.colorScheme.secondaryContainer),
//            contentAlignment = Alignment.Center
//        ) {
//            if (byteArray.isNotEmpty()) {
//                AsyncImage(
//                    model = byteArray,
//                    contentDescription = "Selected Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            } else {
//                Text("활동 사진을 업로드해주세요.", color = MaterialTheme.colorScheme.onPrimaryContainer)
//            }
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(40.dp)
//        ) {
//            Button(
//                onClick = { pickerLauncher.launch() },
//                shape = MaterialTheme.shapes.medium,
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
//            ) {
//                Icon(
//                    modifier = Modifier.size(40.dp),
//                    painter = painterResource(Res.drawable.camera_icon),
//                    contentDescription = "Camera_Icon",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//            Button(
//                onClick = { pickerLauncher.launch() },
//                shape = MaterialTheme.shapes.medium,
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
//            ) {
//                Icon(
//                    modifier = Modifier.size(40.dp),
//                    painter = painterResource(Res.drawable.gallery_icon),
//                    contentDescription = "Gallery_Icon",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//        }
//
//        // 사진이 업로드된 후 텍스트 입력 필드와 확인 버튼 표시
//        if (isImageUploaded) {
//            Spacer(modifier = Modifier.height(50.dp))
//            TextField(
//                value = bookTitle.value,
//                onValueChange = { newValue ->
//                    bookTitle.value = newValue
//                },
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
//                placeholder = {
//                    Text(
//                        "사진에 대한 짧은 기록을 남겨주세요.",
//                        modifier = Modifier.fillMaxWidth(),
//                        color = MaterialTheme.colorScheme.onPrimaryContainer,
//                        textAlign = TextAlign.Center,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp
//                    )
//                },
//                textStyle = TextStyle(
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp
//                ),
//                colors = TextFieldDefaults.colors(
//                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
//                ),
//                shape = RoundedCornerShape(30.dp),
//                // 아래 속성들 추가
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        keyboardController?.hide() // 키보드 숨기기
//                    }
//                ),
//                singleLine = true  // 한 줄 입력으로 제한
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    navController.navigate("SecondStoryScreen")
//                },
//                modifier = Modifier.padding(top = 16.dp)
//            ) {
//                Text("확인")
//            }
//        }
//    }
//
//    // "계속하시겠습니까?" 팝업 표시
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = { Text(text = "사진 업로드 완료") },
//            text = { Text("계속하시겠습니까?") },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        showDialog = false
//                        navController.navigate("SecondStoryScreen") // "예"를 선택하면 이동
//                    }
//                ) {
//                    Text("예")
//                }
//            },
//            dismissButton = {
//                Button(
//                    onClick = {
//                        showDialog = false
//                        byteArray = ByteArray(0) // "아니요"를 선택하면 초기화
//                        inputText = TextFieldValue("") // 텍스트 필드 초기화
//                        isImageUploaded = false // 상태 초기화
//                    }
//                ) {
//                    Text("아니요")
//                }
//            }
//        )
//    }
//}
//
