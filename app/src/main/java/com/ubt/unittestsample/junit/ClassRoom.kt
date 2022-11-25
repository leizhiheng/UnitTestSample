package com.ubt.unittestsample.junit

class ClassRoom(val id: String) {
    var students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun removeStudent(student: Student) {
        students.remove(student)
    }
}