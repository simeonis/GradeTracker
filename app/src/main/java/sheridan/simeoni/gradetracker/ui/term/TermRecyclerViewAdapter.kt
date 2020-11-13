package sheridan.simeoni.gradetracker.ui.term

import android.content.Context
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.*

class TermRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>() {

    var terms: List<Term>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
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
            binding.termPercentLabel.text = if(term.average == -1) context.getString(R.string.blank) else term.average.toString()
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
            fun from(parent: ViewGroup, context : Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentTermItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }
}