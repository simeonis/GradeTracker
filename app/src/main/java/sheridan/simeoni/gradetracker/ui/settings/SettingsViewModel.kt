package sheridan.simeoni.gradetracker.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.model.GradeCalculator

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao

    fun deleteAll() {
        viewModelScope.launch {
            gradeTrackerDao.deleteAll()
        }
    }

    fun updateAll(){
        viewModelScope.launch {
            val terms = gradeTrackerDao.getAllTermsGlobal()

            for(t in terms){
                var termCourses = gradeTrackerDao.getAllCoursesList(t.id)

                for(c in termCourses){
                    val assignments = gradeTrackerDao.getAllAssignmentsList(c.id)
                    gradeTrackerDao.updateCourseGrade(c.id, GradeCalculator.calculateGrade(assignments))
                }
                termCourses = gradeTrackerDao.getAllCoursesList(t.id)
                gradeTrackerDao.updateTermGrade(t.id, GradeCalculator.termAverage(termCourses))
            }
        }
    }
}