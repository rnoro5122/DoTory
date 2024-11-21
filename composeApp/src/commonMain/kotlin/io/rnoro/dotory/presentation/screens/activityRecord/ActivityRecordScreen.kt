import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.camera_icon
import dotory.composeapp.generated.resources.gallery_icon
import io.rnoro.dotory.presentation.screens.activityRecord.ActivityRecordViewModel
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActivityRecordScreen(
    activityRecordViewModel: ActivityRecordViewModel = viewModel(),
    fairyTaleViewModel: FairyTaleViewModel,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    activityRecordViewModel.updateImage(it.readByteArray(context))
                }
            }
        }
    )

    if (activityRecordViewModel.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp) // 섹션 사이 간격 추가
    ) {
        // 왼쪽 영역: 이미지 업로드
        ImageUploadSection(
            imageByteArray = activityRecordViewModel.imageByteArray,
            onPickImage = { pickerLauncher.launch() },
            modifier = Modifier.weight(1f) // 동일한 비율로 공간 할당
        )

        // 오른쪽 영역: 설명 입력
        DescriptionSection(
            description = activityRecordViewModel.description,
            onDescriptionChange = { activityRecordViewModel.updateDescription(it) },
            onSave = {
                activityRecordViewModel.saveActivityRecord(
                    onComplete = { navController.popBackStack() },
                    fairyTaleViewModel = fairyTaleViewModel
                )
            },
            keyboardController = keyboardController,
            modifier = Modifier.weight(1f) // 동일한 비율로 공간 할당
        )
    }
}


@Composable
private fun ImageUploadSection(
    imageByteArray: ByteArray?,
    onPickImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(500.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                if (imageByteArray != null) {
                    AsyncImage(
                        model = imageByteArray,
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("활동 사진을 업로드해주세요")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ImagePickerButton(
                    icon = Res.drawable.camera_icon,
                    text = "촬영하기",
                    onClick = onPickImage
                )

                ImagePickerButton(
                    icon = Res.drawable.gallery_icon,
                    text = "갤러리",
                    onClick = onPickImage
                )
            }
        }
    }
}

@Composable
private fun ImagePickerButton(
    icon: DrawableResource,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = text,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "활동 기록하기",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("활동 설명") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = 32.dp),
                textStyle = TextStyle(textAlign = TextAlign.Start),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("저장하기")
            }
        }
    }
}