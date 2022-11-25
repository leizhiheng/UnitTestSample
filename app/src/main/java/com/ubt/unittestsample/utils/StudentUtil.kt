package com.ubt.unittestsample.utils

import com.ubt.unittestsample.junit.Student

class StudentUtil {
    companion object {
        fun sortStudentByName(students: MutableList<Student>) {
            students.sortBy {
                it.name
            }
        }

        fun judgeGradleLevel(score: Int): String {
            return when {
                score >= 90 -> "A"
                score >= 80 -> "B"
                score >= 70 -> "C"
                score >= 60 -> "D"
                else -> "E"
            }
        }
    }
}