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
import sheridan.simeoni.gradetracker.databinding.DialogAssignmentBinding
import java.io.Serializable
import java.text.FieldPosition

class AssignmentDialog : DialogFragment() {

    private lateinit var binding: DialogAssignmentBinding
    private val safeArgs: AssignmentDialogArgs by navArgs()

    companion object{
        const val CONFIRMATION_ASSIGNMENT_RESULT = "confirmation_assignment_result"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAssignmentBinding.inflate(inflater, container, false)
        binding.status = safeArgs.status
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogAssignmentNameInput.setOnClickListener {
            binding.dialogAssignmentNameWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }
        binding.dialogAssignmentCodeInput.setOnClickListener {
            binding.dialogAssignmentCodeWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }

        binding.dialogAssignmentWeightInput.setOnClickListener {
            binding.dialogAssignmentWeightWrapper.apply{
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
        val assignment = safeArgs.status.assignment
        var name = binding.dialogAssignmentNameInput.text.toString()
        var grade = binding.dialogAssignmentCodeInput.text.toString()
        var weight = binding.dialogAssignmentWeightInput.text.toString()
        var validated = true

        if(name.isEmpty()){
            if (status) name = assignment!!.assignmentName
            else { binding.dialogAssignmentNameWrapper.error = "required"; validated = false }
        }
        if(grade.isEmpty()){
            if (status) grade = assignment!!.totalPoints.toString()
            else { binding.dialogAssignmentCodeWrapper.error = "required"; validated = false }
        }
        if(weight.isEmpty()){
            if (status) weight = assignment!!.weight.toString()
            else { binding.dialogAssignmentWeightWrapper.error = "required"; validated = false }
        }
        if (validated) {
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(CONFIRMATION_ASSIGNMENT_RESULT, AssignmentDialogData(
                    status, assignment?.id ?: 0,assignment?.position ?: -1,name, grade.toInt(), weight.toFloat()))
            dismiss()
        }
    }

    data class AssignmentDialogData(val edit: Boolean, val id: Long, val position: Int,
                                    val name : String, val totalPoints : Int,
                                    val weight : Float) : Serializable
}