package io.rnoro.dotory.presentation.screens.activityRecord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleViewModel
import kotlinx.coroutines.launch

class ActivityRecordViewModel : ViewModel() {
    var imageByteArray by mutableStateOf<ByteArray?>(null)
        private set
        
    var description by mutableStateOf("")
        private set
        
    var isLoading by mutableStateOf(false)
        private set

    fun updateImage(bytes: ByteArray?) {
        imageByteArray = bytes
    }

    fun updateDescription(text: String) {
        description = text
    }

    fun saveActivityRecord(
        onComplete: () -> Unit,
        fairyTaleViewModel: FairyTaleViewModel
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                // TODO: 이미지와 설명 저장 로직
                fairyTaleViewModel.completeActivity()
                onComplete()
            } catch (e: Exception) {
                // TODO: 에러 처리
            } finally {
                isLoading = false
            }
        }
    }
}