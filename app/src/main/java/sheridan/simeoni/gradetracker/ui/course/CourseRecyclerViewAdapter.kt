package sheridan.simeoni.gradetracker.ui.course

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.databinding.FragmentCourseItemBinding
import sheridan.simeoni.gradetracker.helper.DragRecyclerView
import sheridan.simeoni.gradetracker.model.*

class CourseRecyclerViewAdapter(private val context: Context, private val view: View) :
        RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>(),
        DragRecyclerView {

    var courses: MutableList<Course>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun swap(p1: Int, p2: Int) {
        val temp = courses!![p1]
        courses!!.remove(temp)
        courses!!.add(p2, temp)
        notifyItemMoved(p1, p2)
    }

    override fun delete(index: Int) {
        val course = courses!![index]
        val action = CourseFragmentDirections.actionCourseToDelete(course.id, course.courseName)
        view.findNavController().navigate(action)
    }

    override fun update() {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses!![position])
    }

    override fun getItemCount(): Int = courses?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentCourseItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.courseItemLabel.text = course.courseCode
            binding.courseGradeLabel.text =
                    if(course.grade == -1.0f)
                        context.getString(R.string.grade).plus(context.getString(R.string.blank))
                    else
                        context.getString(R.string.grade).plus(String.format("%.1f%%", course.grade))
            binding.courseGradeTargetLabel.text = context.getString(R.string.course_target).plus(String.format("%.1f%%", course.targetGrade))
            binding.root.setOnClickListener {
                val action = CourseFragmentDirections.actionCourseToAssignment(KeyEnvelope(course.courseName, course.id))
                it.findNavController().navigate(action)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, context: Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentCourseItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }
}