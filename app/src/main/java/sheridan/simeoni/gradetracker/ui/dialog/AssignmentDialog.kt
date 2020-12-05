package sheridan.simeoni.gradetracker.ui.dialog


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogAssignmentBinding
import sheridan.simeoni.gradetracker.ui.assignment.AssignmentFragmentArgs
import java.io.Serializable

class AssignmentDialog : DialogFragment() {

    private lateinit var binding: DialogAssignmentBinding
    private val safeArgs: AssignmentDialogArgs by navArgs()

    companion object{
        const val CONFIRMATION_ASSIGNMENT_RESULT = "confirmation_assignment_result"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAssignmentBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (safeArgs.status.edit) { initEdit() }

        binding.doneButton.setOnClickListener { confirmed() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun initEdit() {
        val assignment = safeArgs.status.assignment!!
        binding.dialogAssignmentTitleLabel.text = getString(R.string.edit_assignment)
        binding.dialogAssignmentNameInput.hint = assignment.assignmentName
        binding.dialogAssignmentGradeInput.hint = String.format("%s/%d",
                if (assignment.points == -1) "-" else assignment.points.toString(),
                assignment.totalPoints)
        binding.dialogAssignmentWeightInput.hint = assignment.weight.toString()
    }

    private fun confirmed(){
        val status = safeArgs.status.edit
        val assignment = safeArgs.status.assignment
        var assignmentName = binding.dialogAssignmentNameInput.text.toString()
        var assignmentGrade = binding.dialogAssignmentGradeInput.text.toString()
        var assignmentWeight = binding.dialogAssignmentWeightInput.text.toString()

        if(assignmentName.isEmpty()){
            if (status) assignmentName = assignment!!.assignmentName
            else binding.dialogAssignmentNameInput.error = "required"
        }
        if(assignmentGrade.isEmpty()){
            if (status) assignmentGrade = assignment!!.totalPoints.toString()
            else binding.dialogAssignmentGradeInput.error = "required"
        }
        if(assignmentWeight.isEmpty()){
            if (status) assignmentWeight = assignment!!.weight.toString()
            else binding.dialogAssignmentWeightInput.error = "required"
        }
        if ((assignmentName.isNotEmpty() && assignmentGrade.isNotEmpty()) || status){
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(CONFIRMATION_ASSIGNMENT_RESULT,
                    AssignmentDialogData(status, assignment?.id ?: 0,
                            assignmentName, assignmentGrade.toInt(), assignmentWeight.toFloat()))
            dismiss()
        }

    }

    data class AssignmentDialogData(val edit: Boolean, val id: Long,
                                    val name : String, val assignmentGrade : Int,
                                    val assignmentWeight : Float) : Serializable
}