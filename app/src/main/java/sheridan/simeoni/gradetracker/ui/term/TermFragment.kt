package sheridan.simeoni.gradetracker.ui.term

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class TermFragment : Fragment() {
    private lateinit var adapter: TermRecyclerViewAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_term, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.term_recycler)
        adapter = TermRecyclerViewAdapter(view.context)
        recyclerView.adapter = adapter


        return view
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