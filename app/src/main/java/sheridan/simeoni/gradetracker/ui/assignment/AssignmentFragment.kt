package sheridan.simeoni.gradetracker.ui.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentBinding
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog

class AssignmentFragment : Fragment() {

    private lateinit var binding: FragmentAssignmentBinding
    private lateinit var adapter: AssignmentRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        val recyclerView = binding.assignmentRecycler
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        recyclerView.addItemDecoration(
                DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL))

        adapter = AssignmentRecyclerViewAdapter()
        recyclerView.adapter = adapter

        binding.assignmentAddButton.setOnClickListener { openDialog() }
        binding.assignmentNextButton.setOnClickListener { findNavController().navigate(R.id.action_assignment_to_grade) }

        return binding.root
    }

    private fun openDialog() {
        val assignmentDialog = AssignmentDialog()
        assignmentDialog.show(childFragmentManager, "dialogAssignment")
    }
}