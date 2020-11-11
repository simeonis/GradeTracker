package sheridan.simeoni.gradetracker.ui.term

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class TermFragment : Fragment() {

    private lateinit var binding: FragmentTermBinding
    private lateinit var adapter: TermRecyclerViewAdapter
    private val viewModel : TermViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTermBinding.inflate(inflater, container, false)
        adapter = TermRecyclerViewAdapter()

        with(binding) {
            termRecycler.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            termRecycler.adapter = adapter
            termRecycler.layoutManager = LinearLayoutManager(context)
        }

        viewModel.terms.observe(viewLifecycleOwner) { adapter.terms = it }
        activity?.title = "Select Term"

        binding.termAddButton.setOnClickListener { openDialog() }

        return binding.root

    }

    private fun openDialog(){
        val termDialog = TermDialog()
        viewModel.add("Term1")
        termDialog.show(childFragmentManager, "dialogTerm" )
    }
}