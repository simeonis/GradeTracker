package sheridan.simeoni.gradetracker.ui.grade

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase

class GradeViewModel(application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao

    fun updateGrade(grade: Int) {

    }
}