package sheridan.simeoni.gradetracker.ui.term

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.*

class TermRecyclerViewAdapter : RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>() {

    var terms: List<Term>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(terms!![position])
    }

    override fun getItemCount(): Int = terms?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentTermItemBinding ): RecyclerView.ViewHolder(binding.root) {

        fun bind(term: Term) {
            binding.termItemLabel.text = term.termName
            binding.termPercentLabel.text = term.average.toString()
            binding.termProgressLabel.text = term.progress.toString()
            binding.root.setOnClickListener {
                val action = TermFragmentDirections.actionTermToCourse(KeyEnvelope(term.termName, term.id))
                it.findNavController().navigate(action)
            }
            binding.root.setOnLongClickListener {
                val action = TermFragmentDirections.actionTermToDelete(term.id, term.termName)
                it.findNavController().navigate(action)
                true
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentTermItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}