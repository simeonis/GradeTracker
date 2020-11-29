package sheridan.simeoni.gradetracker.ui.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentBinding
import sheridan.simeoni.gradetracker.helper.DragManageAdapter
import sheridan.simeoni.gradetracker.ui.course.CourseViewModel
import sheridan.simeoni.gradetracker.ui.course.CourseViewModelFactory
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog

class AssignmentFragment : Fragment() {

    private lateinit var binding: FragmentAssignmentBinding
    private lateinit var adapter: AssignmentRecyclerViewAdapter
    private val safeArgs: AssignmentFragmentArgs by navArgs()
    private val viewModel: AssignmentViewModel by viewModels {
        AssignmentViewModelFactory(safeArgs.keyEnvelope.key, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        adapter = AssignmentRecyclerViewAdapter(requireContext(), binding.root)

        val callback = DragManageAdapter(adapter, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
                ItemTouchHelper.RIGHT)
        val helper = ItemTouchHelper(callback)

        with(binding) {
            assignmentRecycler.adapter = adapter
            assignmentRecycler.layoutManager = LinearLayoutManager(context)
            helper.attachToRecyclerView(assignmentRecycler)
        }

        activity?.title = safeArgs.keyEnvelope.title
        viewModel.assignments.observe(viewLifecycleOwner) { adapter.assignments = it as MutableList<Assignment>? }

        binding.assignmentAddButton.setOnClickListener { findNavController().navigate(R.id.action_assignment_to_assignmentDialog) }
        viewModel.course.observe(viewLifecycleOwner){
            binding.assignmentCurrentProgress.setProgress(it.grade.toInt())
            if(it.grade != -1.0f) { binding.assignmentCurrentNumberLabel.text = String.format("%.1f%%", it.grade) }
            else{ binding.assignmentCurrentNumberLabel.text = getString(R.string.blank) }
        }
        viewModel.course.observe(viewLifecycleOwner){
            binding.assignmentGoalProgress.setProgress(it.targetGrade.toInt())
            if(it.targetGrade != -1.0f){ binding.assignmentGoalNumberLabel.text = String.format("%.1f%%", it.targetGrade) }
            else{ binding.assignmentGoalNumberLabel.text = getString(R.string.blank) }
        }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.set(AssignmentDialog.CONFIRMATION_ASSIGNMENT_RESULT, null) // Dialog will override this
        savedStateHandle?.getLiveData<AssignmentDialog.AssignmentDialogData>(AssignmentDialog.CONFIRMATION_ASSIGNMENT_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it != null) viewModel.add(it.name,-1, it.assignmentGrade, it.assignmentWeight)
        }
        savedStateHandle?.getLiveData<Long>(ConfirmationDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it >= 0) viewModel.delete(it) else adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}