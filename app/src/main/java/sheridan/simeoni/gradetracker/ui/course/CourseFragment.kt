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
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class CourseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.course_add_button).setOnClickListener {
            openDialog()
        }
        view.findViewById<Button>(R.id.course_button).setOnClickListener {
            val bundle = bundleOf("title" to "PROG20082")
            findNavController().navigate(R.id.action_course_to_assignment, bundle)
        }
    }

    private fun openDialog(){
        val courseDialog = CourseDialog()
        courseDialog.show(childFragmentManager, "dialogTerm" )
    }
}




