package sheridan.simeoni.gradetracker.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentCourseBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.courseAddButton.setOnClickListener { openDialog() }


        return binding.root
    }
//    val bundle = bundleOf("title" to "PROG20082")
//    findNavController().navigate(R.id.action_course_to_assignment, bundle)

    private fun openDialog(){
        val courseDialog = CourseDialog()
        courseDialog.show(childFragmentManager, "dialogTerm" )
    }
}




