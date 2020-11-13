package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogCourseBinding
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import java.io.Serializable

class CourseDialog : DialogFragment() {

    companion object{
        const val CONFIRMATION_COURSE_RESULT = "confirmation_course_result"
    }

    private lateinit var binding: DialogCourseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogCourseBinding.inflate(inflater, container, false)

        binding.doneButton.setOnClickListener { confirmed() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun confirmed(){
        val courseName = binding.dialogCourseNameInput.text.toString()
        val courseCode = binding.dialogCourseCodeInput.text.toString()
        val courseTarget = binding.dialogCourseTargetInput.text.toString()

        if(courseName.isEmpty()){
            binding.dialogCourseNameInput.error = "required"
        }
        if(courseCode.isEmpty()){
            binding.dialogCourseCodeInput.error = "required"
        }
        if(courseTarget.isEmpty()){
            binding.dialogCourseTargetInput.error = "required"
        }
        else if (courseName.isNotEmpty() && courseCode.isNotEmpty()){
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(CONFIRMATION_COURSE_RESULT, CourseDialogData(courseName, courseCode, courseTarget.toInt()))
            dismiss()
        }

    }
    data class CourseDialogData(val name : String, val courseCode : String, val targetGrade : Int) : Serializable
}