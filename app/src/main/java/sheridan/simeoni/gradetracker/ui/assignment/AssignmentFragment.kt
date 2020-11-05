package sheridan.simeoni.gradetracker.ui.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog

class AssignmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.assignment_add_button).setOnClickListener {
            openDialog()
        }

        view.findViewById<Button>(R.id.assignment_next_button).setOnClickListener {
            findNavController().navigate(R.id.action_assignment_to_grade)
        }
    }

    private fun openDialog() {
        val assignmentDialog = AssignmentDialog()
        assignmentDialog.show(childFragmentManager, "dialogAssignment")
    }
}