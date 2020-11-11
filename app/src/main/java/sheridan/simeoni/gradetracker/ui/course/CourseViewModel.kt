package sheridan.simeoni.gradetracker.ui.course

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao




    fun add (courseID : String, termID : String, grade: Int, targetGrade: Int){
        viewModelScope.launch {
            gradeTrackerDao.insert(Course(courseID, termID, grade,targetGrade))
        }
    }

}