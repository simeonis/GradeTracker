package sheridan.simeoni.gradetracker.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentCourseBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.ui.assignment.AssignmentFragmentArgs
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog
import sheridan.simeoni.gradetracker.ui.term.TermRecyclerViewAdapter

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
        adapter = CourseRecyclerViewAdapter()

        with(binding) {
            courseRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            courseRecycler.adapter = adapter
            courseRecycler.layoutManager = LinearLayoutManager(context)
        }

        activity?.title = safeArgs.keyEnveloppe.title
        viewModel.courses.observe(viewLifecycleOwner) { adapter.courses = it }

        binding.courseAddButton.setOnClickListener { openDialog() }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Long>(ConfirmationDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            viewModel.delete(it)
        }


        return binding.root
    }

    private fun openDialog(){
        val courseDialog = CourseDialog()
        viewModel.add("Course1", -1, -1)
        courseDialog.show(childFragmentManager, "dialogTerm" )
    }
}




