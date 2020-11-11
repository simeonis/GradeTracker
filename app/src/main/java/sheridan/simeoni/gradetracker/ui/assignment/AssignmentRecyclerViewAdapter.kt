package sheridan.simeoni.gradetracker.ui.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentItemBinding
import sheridan.simeoni.gradetracker.model.AssignmentData
import sheridan.simeoni.gradetracker.model.CourseData
import sheridan.simeoni.gradetracker.model.GradeData

class AssignmentRecyclerViewAdapter : RecyclerView.Adapter<AssignmentRecyclerViewAdapter.ViewHolder>() {

    var assignments: List<Assignment>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(assignments!![position])
    }

    override fun getItemCount(): Int = assignments?.size ?: 0

    class ViewHolder private constructor(
        private val binding: FragmentAssignmentItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(assignment: Assignment) {
            binding.assignmentItemNameLabel.text = assignment.assignmentName
            binding.assignmentItemGradeLabel.text = String.format("%s/%d",
                    if (assignment.grade == -1) "-"
                    else assignment.grade.toString(), assignment.gradeTotal)
            binding.assignmentItemWeightLabel.text = String.format("%.1f%%", assignment.weight)
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