package sheridan.simeoni.gradetracker.ui.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.AssignmentStatus
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentBinding
import sheridan.simeoni.gradetracker.helper.DragManageAdapter
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog.AssignmentDialogData
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog.Companion.CONFIRMATION_ASSIGNMENT_RESULT
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog.Companion.CONFIRMATION_RESULT

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
        activity?.title = safeArgs.keyEnvelope.title // Set fragment title

        // Enabling Drag and Swipe Support
        val callback = DragManageAdapter(adapter, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), ItemTouchHelper.RIGHT)
        val helper = ItemTouchHelper(callback)

        // Connect RecyclerView adapter
        with(binding) {
            assignmentRecycler.adapter = adapter
            assignmentRecycler.layoutManager = LinearLayoutManager(context)
            helper.attachToRecyclerView(assignmentRecycler)
        }

        // Update RecyclerView on Assignment LiveData change
        viewModel.assignments.observe(viewLifecycleOwner) { adapter.assignments = it as MutableList<Assignment>? }

        // Navigate to AssignmentDialog
        binding.assignmentAddButton.setOnClickListener {
            val action = AssignmentFragmentDirections.actionAssignmentToAssignmentDialog(AssignmentStatus(false, null))
            findNavController().navigate(action)
        }

        // Update "Summary" on Course LiveData changes
        viewModel.course.observe(viewLifecycleOwner){
            // Current Grade
            binding.assignmentCurrentProgress.progress = it.grade.toInt()
            if(it.grade != -1.0f) { binding.assignmentCurrentNumberLabel.text = String.format("%.1f%%", it.grade) }
            else{ binding.assignmentCurrentNumberLabel.text = getString(R.string.blank) }
            // Target Grade
            binding.assignmentGoalProgress.progress = it.targetGrade.toInt()
            if(it.targetGrade != -1.0f){ binding.assignmentGoalNumberLabel.text = String.format("%.1f%%", it.targetGrade) }
            else{ binding.assignmentGoalNumberLabel.text = getString(R.string.blank) }
        }

        // Prepare to listen for data from previous BackStack
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CONFIRMATION_ASSIGNMENT_RESULT, null) // Dialog will override this

        // Listen for data coming from AssignmentDialog
        savedStateHandle?.getLiveData<AssignmentDialogData>(CONFIRMATION_ASSIGNMENT_RESULT)?.observe(viewLifecycleOwner)
        {
            it?.apply { if (it.edit) viewModel.edit(it) else viewModel.add(it) }
        }
        // Listen for data coming from ConfirmationDialog
        savedStateHandle?.getLiveData<Long>(CONFIRMATION_RESULT)?.observe(viewLifecycleOwner) {
            if (it >= 0) viewModel.delete(it) else adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}