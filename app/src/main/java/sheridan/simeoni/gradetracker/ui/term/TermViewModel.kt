package sheridan.simeoni.gradetracker.ui.term

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.database.Term


class TermViewModel(application: Application) : AndroidViewModel(application) {

    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao

    fun add (termID: String){
        viewModelScope.launch {
            gradeTrackerDao.insert(Term(0,termID, -1, 0, 0))
        }
    }

}