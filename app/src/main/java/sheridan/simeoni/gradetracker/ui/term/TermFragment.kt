package sheridan.simeoni.gradetracker.ui.term

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.database.TermStatus
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding
import sheridan.simeoni.gradetracker.helper.DragManageAdapter
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog.Companion.CONFIRMATION_RESULT

class TermFragment : Fragment() {

    private lateinit var binding: FragmentTermBinding
    private lateinit var adapter: TermRecyclerViewAdapter
    private val viewModel : TermViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTermBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        adapter = TermRecyclerViewAdapter(requireContext(), binding.root, viewModel)
        (activity as AppCompatActivity).supportActionBar?.show()

        val callback = DragManageAdapter(adapter, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
                ItemTouchHelper.RIGHT)
        val helper = ItemTouchHelper(callback)

        with(binding) {
            termRecycler.adapter = adapter
            termRecycler.layoutManager = LinearLayoutManager(context)
            helper.attachToRecyclerView(termRecycler)
        }

        viewModel.terms.observe(viewLifecycleOwner) { adapter.terms = it as MutableList<Term>? }

        binding.termAddButton.setOnClickListener {
            val action = TermFragmentDirections.actionTermToTermDialog(TermStatus(false, null))
            findNavController().navigate(action)
        }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Long>(CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it >= 0) viewModel.delete(it) else adapter.notifyDataSetChanged()
        }

        return binding.root
    }

}
