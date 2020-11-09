package sheridan.simeoni.gradetracker.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentCourseItemBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.AssignmentData
import sheridan.simeoni.gradetracker.model.CourseData
import sheridan.simeoni.gradetracker.model.GradeData
import sheridan.simeoni.gradetracker.model.TermData

class CourseRecyclerViewAdapter : RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>() {

    var safeArgs: TermData? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(safeArgs!!.courseData[position])
    }

    override fun getItemCount(): Int = safeArgs?.courseData?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentCourseItemBinding ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CourseData) {
            binding.courseItemLabel.text = data.name
            binding.courseGradeLabel.text = "100%"
            binding.courseGradeTargetLabel.text = "100%"
            binding.root.setOnClickListener {
                val action = CourseFragmentDirections.actionCourseToAssignment(data)
                it.findNavController().navigate(action)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentCourseItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}