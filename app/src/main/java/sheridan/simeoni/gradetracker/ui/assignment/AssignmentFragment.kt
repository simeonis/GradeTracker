package sheridan.simeoni.gradetracker.ui.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentBinding
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog

class AssignmentFragment : Fragment() {

    private lateinit var binding: FragmentAssignmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAssignmentBinding.inflate(inflater, container, false)

        binding.assignmentAddButton.setOnClickListener { openDialog() }
        binding.assignmentNextButton.setOnClickListener { findNavController().navigate(R.id.action_assignment_to_grade) }

        return binding.root
    }

    private fun openDialog() {
        val assignmentDialog = AssignmentDialog()
        assignmentDialog.show(childFragmentManager, "dialogAssignment")
    }
}