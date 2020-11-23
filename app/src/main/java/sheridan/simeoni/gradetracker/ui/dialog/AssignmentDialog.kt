package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogAssignmentBinding
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import sheridan.simeoni.gradetracker.ui.assignment.AssignmentViewModel
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import java.io.Serializable
import java.lang.IllegalStateException

class AssignmentDialog : DialogFragment() {

    private lateinit var binding: DialogAssignmentBinding

    companion object{
        const val CONFIRMATION_ASSIGNMENT_RESULT = "confirmation_assignment_result"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogAssignmentBinding.inflate(inflater, container, false)

        binding.doneButton.setOnClickListener { confirmed() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun confirmed(){
        val assignmentName = binding.dialogAssignmentNameInput.text.toString()
        val assignmentGrade = binding.dialogAssignmentGradeInput.text.toString()
        val assignmentWeight = binding.dialogAssignmentWeightInput.text.toString()

        if(assignmentName.isEmpty()){
            binding.dialogAssignmentNameInput.error = "required"
        }
        if(assignmentGrade.isEmpty()){
            binding.dialogAssignmentGradeInput.error = "required"
        }
        if(assignmentWeight.isEmpty()){
            binding.dialogAssignmentWeightInput.error = "required"
        }
        else if (assignmentName.isNotEmpty() && assignmentGrade.isNotEmpty()){
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(CONFIRMATION_ASSIGNMENT_RESULT, AssignmentDialogData(assignmentName, assignmentGrade.toInt(), assignmentWeight.toFloat()))
            dismiss()
        }

    }
    data class AssignmentDialogData(val name : String, val assignmentGrade : Int, val assignmentWeight : Float) : Serializable
}