package sheridan.simeoni.gradetracker.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentCourseBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog
import sheridan.simeoni.gradetracker.ui.term.TermRecyclerViewAdapter

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var adapter: CourseRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        adapter = CourseRecyclerViewAdapter()

        with(binding) {
            courseRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            courseRecycler.adapter = adapter
            courseRecycler.layoutManager = LinearLayoutManager(context)
        }

        binding.courseAddButton.setOnClickListener { openDialog() }

        return binding.root
    }

    private fun openDialog(){
        val courseDialog = CourseDialog()
        courseDialog.show(childFragmentManager, "dialogTerm" )
    }
}




