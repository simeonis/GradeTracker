package sheridan.simeoni.gradetracker.model

import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course

class GradeCalculator {
    companion object{

        //Course Grade
        fun calcGrade(list : List<Assignment>) : Float{
            var totalGrade = 0.0f
            for (item in list){
               val weightedGrade =  item.grade * item.weight
                totalGrade += weightedGrade
            }
            return totalGrade
        }
        //Calculate AVG
        fun term_avg(list : List<Course>) : Int{
            var total = 0
            for(item in list){
                    total += item.grade // or times item.credit_weight
            }
            return total / list.size
        }

        //Progress
        fun course_progress(list : List<Assignment>, defaultGrade : Int) : Int{
            //number of assignments complete / total number of assignments
            var completedCount = 0
            for(item in list){
                if(item.grade != defaultGrade ){
                    completedCount += 1
                }
            }
            return completedCount / list.size
        }

        //GPA
        fun getGPA(list : List<Course>) : Float{
            var totalPoints = 0.0f
            var creditsAttempted = 0.0f
            val credits = 3 // we don't have this info
            for(item in list){
                val coursePt = getPointsGrade(item.grade)
                totalPoints += coursePt * credits
                creditsAttempted += credits // should be item.credit
            }
            return totalPoints / creditsAttempted
        }

        //Letter Grade
        fun getLetterGrade(grade: Float){
            when (grade) {
                in 90.0f..100.0f -> print("A")
                in 80.0f..89.9f-> print("B")
                in 70.0f..79.9f -> print("C")
                in 50.0f..69.9f -> print("D")
                in 0.0f..49.9f -> print("F")
                else -> { // Note the block
                    print("Error")
                }
            }
        }
        // get points
        fun getPointsGrade(grade: Int) : Int{
            var points = 0
            when (grade) {
                in 90..100 -> points += 4
                in 80..89-> points += 3
                in 70..79 -> points += 2
                in 50..69 -> points += 1
                in 0..49 -> points += 0
                else -> { // Note the block
                    print("Error")
                }
            }
            return points
        }
    }
}