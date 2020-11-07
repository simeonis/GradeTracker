package sheridan.simeoni.gradetracker.ui.term

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.ui.assignment.AssignmentRecyclerViewAdapter
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class TermFragment : Fragment() {

    private lateinit var binding: FragmentTermBinding
    private lateinit var adapter: TermRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermBinding.inflate(inflater, container, false)
        adapter = TermRecyclerViewAdapter()

        with(binding) {
            termRecycler.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            termRecycler.adapter = adapter
            termRecycler.layoutManager = LinearLayoutManager(context)
        }

        binding.termAddButton.setOnClickListener { openDialog() }
        return binding.root

    }
    private fun openDialog(){
        val termDialog = TermDialog()
        termDialog.show(childFragmentManager, "dialogTerm" )
    }
}