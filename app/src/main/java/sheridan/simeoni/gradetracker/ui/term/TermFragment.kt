package sheridan.simeoni.gradetracker.ui.term

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog.Companion.CONFIRMATION_RESULT
import sheridan.simeoni.gradetracker.ui.dialog.TermDialog

class TermFragment : Fragment() {

    private lateinit var binding: FragmentTermBinding
    private lateinit var adapter: TermRecyclerViewAdapter
    private val viewModel : TermViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTermBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        adapter = TermRecyclerViewAdapter(requireContext())
        (activity as AppCompatActivity).supportActionBar?.show()

        with(binding) {
            termRecycler.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            termRecycler.adapter = adapter
            termRecycler.layoutManager = LinearLayoutManager(context)
        }

        viewModel.terms.observe(viewLifecycleOwner) { adapter.terms = it }

        binding.termAddButton.setOnClickListener { findNavController().navigate(R.id.action_term_to_termDialog) }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Long>(CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            viewModel.delete(it)
        }
        return binding.root
    }

    private fun openDialog() {
        val termDialog = TermDialog()
        termDialog.show(childFragmentManager, "dialogTerm" )
    }
}
