package sheridan.simeoni.gradetracker.model

import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course

class GradeCalculator {
    companion object{
        var fillerGrade = .7f

        //Course Grade
        fun calculateGrade(list : List<Assignment>) : Float{
            var totalGrade = 0.0f

            var fillerCounter = 0
            for (item in list){
                var weightedGrade : Float
                if(item.points != -1){
                    weightedGrade =  (item.points / item.totalPoints.toFloat()) * (item.weight / 100f)
                }
                else{
                    weightedGrade =  fillerGrade * (item.weight / 100f)
                    fillerCounter++
                }
                totalGrade += weightedGrade
            }
            if(fillerCounter == list.size){
                 return -1f
            }
            return totalGrade * 100f
        }
        //Calculate Average
        fun termAverage(list : List<Course>) : Float{
            var total = 0.0f
            for(item in list){
                if(item.grade != -1f) {
                    total += item.grade.toFloat() // or times item.credit_weight
                }
            }
            if(list.isNotEmpty()){
                return total / list.size.toFloat()
            }
            else{
                return -1.0f
            }
        }

        //GPA
//        fun getGPA(list : List<Course>) : Float{
//            var totalPoints = 0.0f
//            var creditsAttempted = 0.0f
//            val credits = 3 // we don't have this info
//            for(item in list){
//                val coursePt = getPointsGrade(item.grade)
//                totalPoints += coursePt * credits
//                creditsAttempted += credits // should be item.credit
//            }
//            return totalPoints / creditsAttempted
//        }

        //Letter Grade
//        fun getLetterGrade(grade: Float){
//            when (grade) {
//                in 90.0f..100.0f -> print("A")
//                in 80.0f..89.9f-> print("B")
//                in 70.0f..79.9f -> print("C")
//                in 50.0f..69.9f -> print("D")
//                in 0.0f..49.9f -> print("F")
//                else -> { // Note the block
//                    print("Error")
//                }
//            }
//        }
        // get points
//        fun getPointsGrade(grade: Float) : Int{
//            var points = 0
//            when (grade) {
//                in 90..100 -> points += 4
//                in 80..89-> points += 3
//                in 70..79 -> points += 2
//                in 50..69 -> points += 1
//                in 0..49 -> points += 0
//                else -> { // Note the block
//                    print("Error")
//                }
//            }
//            return points
//        }
    }
}