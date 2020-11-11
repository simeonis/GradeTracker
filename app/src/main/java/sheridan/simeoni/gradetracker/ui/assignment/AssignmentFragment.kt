package sheridan.simeoni.gradetracker.ui.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentBinding
import sheridan.simeoni.gradetracker.ui.course.CourseViewModel
import sheridan.simeoni.gradetracker.ui.course.CourseViewModelFactory
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog

class AssignmentFragment : Fragment() {

    private lateinit var binding: FragmentAssignmentBinding
    private lateinit var adapter: AssignmentRecyclerViewAdapter
    private val safeArgs: AssignmentFragmentArgs by navArgs()
    private val viewModel: AssignmentViewModel by viewModels {
        AssignmentViewModelFactory(safeArgs.keyEnvelope.key, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        adapter = AssignmentRecyclerViewAdapter()

        with(binding) {
            assignmentRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            assignmentRecycler.adapter = adapter
            assignmentRecycler.layoutManager = LinearLayoutManager(context)
        }

        activity?.title = safeArgs.keyEnvelope.title
        viewModel.assignments.observe(viewLifecycleOwner) { adapter.assignments = it }

        binding.assignmentAddButton.setOnClickListener { openDialog() }

        return binding.root
    }

    private fun openDialog() {
        val assignmentDialog = AssignmentDialog()
        viewModel.add("A1", -1, 40, 35, 15.0f)
        assignmentDialog.show(childFragmentManager, "dialogAssignment")
    }
}