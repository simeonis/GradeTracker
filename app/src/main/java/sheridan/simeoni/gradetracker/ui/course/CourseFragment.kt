package sheridan.simeoni.gradetracker.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.CourseStatus
import sheridan.simeoni.gradetracker.databinding.FragmentCourseBinding
import sheridan.simeoni.gradetracker.helper.DragManageAdapter
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog.CourseDialogData


class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var adapter: CourseRecyclerViewAdapter
    private val safeArgs: CourseFragmentArgs by navArgs()
    private val viewModel: CourseViewModel by viewModels {
        CourseViewModelFactory(safeArgs.keyEnveloppe.key, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        adapter = CourseRecyclerViewAdapter(requireContext(), binding.root)
        activity?.title = safeArgs.keyEnveloppe.title

        // Enabling Drag and Swipe Support
        val callback = DragManageAdapter(adapter, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
                ItemTouchHelper.RIGHT)
        val helper = ItemTouchHelper(callback)

        // Connect RecyclerView adapter
        with(binding) {
            courseRecycler.adapter = adapter
            courseRecycler.layoutManager = LinearLayoutManager(context)
            helper.attachToRecyclerView(courseRecycler)
        }

        // Update RecyclerView on Courses LiveData change
        viewModel.courses.observe(viewLifecycleOwner) { adapter.courses = it as MutableList<Course>? }

        // Navigate to CourseDialog
        binding.courseAddButton.setOnClickListener {
            val action = CourseFragmentDirections.actionCourseToCourseDialog(CourseStatus(false, null))
            findNavController().navigate(action)
        }

        // Prepare to listen for data from previous BackStack
        val savedStateHandle: SavedStateHandle? = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CourseDialog.CONFIRMATION_RESULT, null) // Dialog will override this

        // Listen for data coming from CourseDialog
        savedStateHandle?.getLiveData<CourseDialogData>(CourseDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it != null) { if (it.status) viewModel.edit(it) else viewModel.add(it) }
        }
        // Listen for data coming from ConfirmationDialog
        savedStateHandle?.getLiveData<Long>(ConfirmationDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it >= 0) viewModel.delete(it) else adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}




