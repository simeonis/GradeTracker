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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Course
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
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        adapter = CourseRecyclerViewAdapter(requireContext(), binding.root)

        val callback = DragManageAdapter(adapter, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
                ItemTouchHelper.RIGHT)
        val helper = ItemTouchHelper(callback)

        with(binding) {
            courseRecycler.adapter = adapter
            courseRecycler.layoutManager = LinearLayoutManager(context)
            helper.attachToRecyclerView(courseRecycler)
        }

        activity?.title = safeArgs.keyEnveloppe.title
        viewModel.courses.observe(viewLifecycleOwner) { adapter.courses = it as MutableList<Course>? }

        binding.courseAddButton.setOnClickListener { findNavController().navigate(R.id.action_course_to_courseDialog) }

        val savedStateHandle: SavedStateHandle? = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CourseDialog.CONFIRMATION_RESULT, null) // Dialog will override this
        savedStateHandle?.getLiveData<CourseDialogData>(CourseDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it != null) viewModel.add(it.name, it.targetGrade)
        }
        savedStateHandle?.getLiveData<Long>(ConfirmationDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it >= 0) viewModel.delete(it) else adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}




