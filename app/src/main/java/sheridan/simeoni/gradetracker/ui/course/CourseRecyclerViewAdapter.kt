package sheridan.simeoni.gradetracker.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentCourseItemBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding

class CourseRecyclerViewAdapter : RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>() {

    var terms: List<String>? = listOf("Course 1", "Course 2", "Course 3", "Course 4", "Course 5", "Course 6", "Course 7", "Course 8")
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
            private val binding: FragmentCourseItemBinding ): RecyclerView.ViewHolder(binding.root) {

        fun bind(name : String, size: Int) {
            binding.courseItemLabel.text = name
            binding.courseGradeLabel.text = "100%"
            binding.courseGradeTargetLabel.text = "100%"
            binding.root.setOnClickListener {
                val bundle = bundleOf("title" to "PROG20082")
                it.findNavController().navigate(R.id.action_course_to_assignment, bundle)
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