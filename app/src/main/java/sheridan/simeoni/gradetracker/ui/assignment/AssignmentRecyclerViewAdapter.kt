package sheridan.simeoni.gradetracker.ui.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentItemBinding

class AssignmentRecyclerViewAdapter : RecyclerView.Adapter<AssignmentRecyclerViewAdapter.ViewHolder>() {

    var assignments: List<String>? = listOf("Quiz 1", "Quiz 2", "Quiz 3", "A1", "A2", "A3", "Midterm", "Final")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(assignments!![position], assignments!!.size)
    }

    override fun getItemCount(): Int = assignments?.size ?: 0

    class ViewHolder private constructor(
        private val binding: FragmentAssignmentItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(name : String, size: Int) {
            binding.assignmentItemNameLabel.text = name
            binding.assignmentItemGradeLabel.text = "-/100"
            binding.assignmentItemWeightLabel.text = String.format("%.1f", (100f / size)) + "%"
            binding.root.setOnClickListener {
                it.findNavController().navigate(R.id.action_assignment_to_grade)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentAssignmentItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}