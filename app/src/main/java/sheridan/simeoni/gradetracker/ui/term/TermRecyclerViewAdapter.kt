package sheridan.simeoni.gradetracker.ui.term

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding

class TermRecyclerViewAdapter : RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>() {

    var terms: List<String>? = listOf("Term 1", "Term 2", "Term 3", "Term 4", "Term 5", "Term 6", "Term 7", "Term 8")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(terms!![position], terms!!.size)
    }

    override fun getItemCount(): Int = terms?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentTermItemBinding ): RecyclerView.ViewHolder(binding.root) {

        fun bind(name : String, size: Int) {
            binding.termItemLabel.text = name
            binding.termPercentLabel.text = "Avg: 100%"
            binding.termProgressLabel.text = "Prog: 100%"
            binding.root.setOnClickListener {
                it.findNavController().navigate(R.id.action_year_to_course)
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