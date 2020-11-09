package sheridan.simeoni.gradetracker.ui.term

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.AssignmentData
import sheridan.simeoni.gradetracker.model.CourseData
import sheridan.simeoni.gradetracker.model.StartData
import sheridan.simeoni.gradetracker.model.TermData

class TermRecyclerViewAdapter : RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>() {

    var safeArgs: StartData? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(safeArgs!!.termData[position])
    }

    override fun getItemCount(): Int = safeArgs?.termData?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentTermItemBinding ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TermData) {
            binding.termItemLabel.text = data.name
            binding.termPercentLabel.text = "Avg: 100%"
            binding.termProgressLabel.text = "Prog: 100%"
            binding.root.setOnClickListener {
                val action = TermFragmentDirections.actionTermToCourse(data)
                it.findNavController().navigate(action)
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