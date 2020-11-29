package sheridan.simeoni.gradetracker.ui.term

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.*
import sheridan.simeoni.gradetracker.helper.DragRecyclerView

class TermRecyclerViewAdapter(
        private val context: Context,
        private val view: View,
        private val viewModel: TermViewModel) :
        RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>(),
        DragRecyclerView {

    var terms: MutableList<Term>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun swap(p1: Int, p2: Int) {
        terms!![p1].position = p2
        terms!![p2].position = p1

        val term1 = terms!![p1]
        terms!!.remove(term1)
        terms!!.add(p2, term1)
        notifyItemMoved(p1, p2)
    }

    override fun delete(position: Int) {
        val term = terms!![position]
        val action = TermFragmentDirections.actionTermToDelete(term.id, term.termName)
        view.findNavController().navigate(action)
    }

    override fun update() {
        viewModel.update(terms!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(terms!![position])
    }

    override fun getItemCount(): Int = terms?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentTermItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(term: Term) {
            binding.termItemLabel.text = term.termName
            binding.termPercentLabel.text =
                    if(term.grade == -1.0f)
                        context.getString(R.string.term_average).plus(context.getString(R.string.blank))
                    else
                        context.getString(R.string.term_average).plus(String.format("%.1f%%", term.grade))
            binding.termProgressLabel.text = context.getString(R.string.term_progress).plus(String.format("%d%%", term.progress))
            binding.termProgressBar.progress = term.progress
            binding.root.setOnClickListener {
                val action = TermFragmentDirections.actionTermToCourse(KeyEnvelope(term.termName, term.id))
                it.findNavController().navigate(action)
            }
            binding.root.setOnLongClickListener {
                it.isSelected = true
                true
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, context : Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentTermItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }
}