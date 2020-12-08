package sheridan.simeoni.gradetracker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogCourseBinding
import sheridan.simeoni.gradetracker.ui.course.CourseFragment
import java.io.Serializable

class CourseDialog : DialogFragment() {

    companion object{
        const val CONFIRMATION_RESULT = "confirmation_course_result"
    }

    private lateinit var binding: DialogCourseBinding
    private val safeArgs: CourseDialogArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogCourseBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (safeArgs.status.edit) { initEdit() }

        binding.doneButton.setOnClickListener { confirmed() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun initEdit() {
        val course = safeArgs.status.course!!
        binding.dialogCourseTitleLabel.text = getString(R.string.edit_course)
        binding.dialogCourseNameInput.hint = course.courseName
        binding.dialogCourseCodeInput.hint = course.courseCode
        binding.dialogCourseTargetInput.hint = course.targetGrade.toString()
    }

    private fun confirmed(){
        val status = safeArgs.status.edit
        val course = safeArgs.status.course
        var courseName = binding.dialogCourseNameInput.text.toString()
        var courseCode = binding.dialogCourseCodeInput.text.toString()
        var courseTarget = binding.dialogCourseTargetInput.text.toString()

        if(courseName.isEmpty()){
            if (status) courseName = course!!.courseName
            else binding.dialogCourseNameInput.error = "required"
        }
        if(courseCode.isEmpty()) {
            if (status) courseCode = course!!.courseCode
            else binding.dialogCourseCodeInput.error = "required"
        }
        if(courseTarget.isEmpty()) {
            if (status) courseTarget = course!!.targetGrade.toString()
            else binding.dialogCourseTargetInput.error = "required"
        }
        if ((courseName.isNotEmpty() && courseCode.isNotEmpty()) || status){
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(CONFIRMATION_RESULT,
                    CourseDialogData(status, course?.id ?: 0,
                            courseName, courseCode,course?.grade ?: -1f, courseTarget.toFloat()))
            dismiss()
        }
    }
    data class CourseDialogData(val status: Boolean, val id: Long,
                                val name : String, val courseCode : String,
                                val grade : Float, val targetGrade : Float) : Serializable
}