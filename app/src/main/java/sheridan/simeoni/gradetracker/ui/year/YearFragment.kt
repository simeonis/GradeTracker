package sheridan.simeoni.gradetracker.ui.year

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class YearFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_year, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.term_add_button).setOnClickListener {
            openDialog()
        }

        view.findViewById<Button>(R.id.term_next_button).setOnClickListener {
            findNavController().navigate(R.id.action_year_to_course)
        }

    }
    private fun openDialog(){
        val termDialog = TermDialog()
        termDialog.show(childFragmentManager, "dialogTerm" )
    }
}