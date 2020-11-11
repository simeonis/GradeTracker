package sheridan.simeoni.gradetracker.ui.assignment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AssignmentViewModelFactory(
    private val envelopeKey: Long,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssignmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssignmentViewModel(envelopeKey, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}