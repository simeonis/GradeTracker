package sheridan.simeoni.gradetracker.ui.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CourseViewModelFactory(
    private val envelopeKey: Long,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(envelopeKey, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}