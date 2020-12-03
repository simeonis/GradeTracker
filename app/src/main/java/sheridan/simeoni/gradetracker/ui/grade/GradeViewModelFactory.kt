package sheridan.simeoni.gradetracker.ui.grade

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GradeViewModelFactory(
        private val envelopeKey: Long,
        private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GradeViewModel(envelopeKey, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}