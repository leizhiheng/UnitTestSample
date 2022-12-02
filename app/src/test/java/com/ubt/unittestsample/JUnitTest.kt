package com.ubt.unittestsample

import com.ubt.unittestsample.junit.ClassRoom
import com.ubt.unittestsample.junit.Student
import com.ubt.unittestsample.utils.StudentUtil
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert.*

/**
 * JUnit: java语言单元测试框架。运行在jvm上，主要测试方法是断言，即 assertEquals()
 * 优点：运行速度快，支持代码覆盖率检测工具。
 * 缺点：无法mock对象，复杂对象的构造很麻烦。
 */
class JUnitTest {

    lateinit var classRoom: ClassRoom

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup_beforeClass() {
            println("setup_beforeClass")
        }
    }

    @Before
    fun setup() {
        classRoom = ClassRoom("Jk_1001")
        println("setup, class room's student cont: ${classRoom.students.size}")
    }


    @Test
    fun joinClassRoom_StudentJoin_ReturnSameNumber() {
        println("joinClassRoom_StudentJoin_ReturnSameNumber")
        val s1 = Student(1001, 22, "lzh")
        s1.jonClassRoom(classRoom)
        assertEquals(1, classRoom.students.size)
    }

    @Test
    fun exitClassRoom_StudentExit_ReturnSameNumber() {
        println("exitClassRoom_StudentExit_ReturnSameNumber")
        val s1 = Student(1001, 22, "lzh")
        s1.jonClassRoom(classRoom)
        s1.exitClassRoom(classRoom)
        assertEquals(0, classRoom.students.size)
    }

    /**
     * 静态方法可以正常测试
     */
    @Test
    fun staticJudgeGradleLevel_90Score_ReturnLevelA() {
        var level = StudentUtil.judgeGradleLevel(90)
        assertEquals("A", level)
    }
}