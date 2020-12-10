package sheridan.simeoni.gradetracker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogCourseBinding
import java.io.Serializable

class CourseDialog : DialogFragment() {

    companion object{
        const val CONFIRMATION_RESULT = "confirmation_course_result"
    }

    private lateinit var binding: DialogCourseBinding
    private val safeArgs: CourseDialogArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogCourseBinding.inflate(inflater, container, false)
        binding.status = safeArgs.status
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogCourseNameWrapper.setOnClickListener {
            binding.dialogCourseNameWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }
        binding.dialogCourseCodeWrapper.setOnClickListener {
            binding.dialogCourseCodeWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }

        binding.dialogCourseTargetWrapper.setOnClickListener {
            binding.dialogCourseTargetWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }

        binding.doneButton.setOnClickListener { confirmed() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun confirmed(){
        val status = safeArgs.status.edit
        val course = safeArgs.status.course
        var name = binding.dialogCourseNameInput.text.toString()
        var code = binding.dialogCourseCodeInput.text.toString()
        var target = binding.dialogCourseTargetInput.text.toString()
        var validated = true

        if(name.isEmpty()){
            if (status) name = course!!.courseName
            else { binding.dialogCourseNameWrapper.error = "required"; validated = false }
        }
        if(code.isEmpty()) {
            if (status) code = course!!.courseCode
            else { binding.dialogCourseCodeWrapper.error = "required"; validated = false }
        }
        if(target.isEmpty()) {
            if (status) target = course!!.targetGrade.toString()
            else { binding.dialogCourseTargetWrapper.error = "required"; validated = false }
        }
        if (validated){
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(CONFIRMATION_RESULT, CourseDialogData(
                    status, course?.id ?: 0, course?.position ?: -1, name, code,course?.grade ?: -1f, target.toFloat()))
            dismiss()
        }
    }
    data class CourseDialogData(val status: Boolean, val id: Long, val position: Int,
                                val name : String, val courseCode : String,
                                val grade : Float, val targetGrade : Float) : Serializable
}