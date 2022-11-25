package com.ubt.unittestsample.junit

class Student(var id: Long, var age: Int, var name: String) {

    var classRoomId = ""
    fun jonClassRoom(classRoom: ClassRoom) {
        updateClassRoomId(classRoom.id)
        classRoom.addStudent(this)
    }

    fun exitClassRoom(classRoom: ClassRoom) {
        updateClassRoomId("")
        classRoom.removeStudent(this)
    }

    private fun updateClassRoomId(classRoomId: String) {
        this.classRoomId = classRoomId
    }
}