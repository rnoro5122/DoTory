package compose.project.dotory.presentation.screens.FairyTale

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import compose.project.dotory.domain.models.FairyTaleResources

class FairyTaleViewModel : ViewModel() {
    var currentPage by mutableStateOf(0)
        private set
        
    private var fairyTale: FairyTale? = null
    
    fun loadFairyTale(topicId: String): Boolean {
        fairyTale = FairyTaleResources.fairyTales[topicId]
        return fairyTale != null
    }
    
    fun getCurrentFairyTale() = fairyTale
    
    fun canGoToPreviousPage() = currentPage > 0
    
    fun canGoToNextPage() = fairyTale?.let { currentPage < it.pages.size - 1 } ?: false
    
    fun goToPreviousPage() {
        if (canGoToPreviousPage()) {
            currentPage--
        }
    }
    
    fun goToNextPage() {
        if (canGoToNextPage()) {
            currentPage++
        }
    }
}