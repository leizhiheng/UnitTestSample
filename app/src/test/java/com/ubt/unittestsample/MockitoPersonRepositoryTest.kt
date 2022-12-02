package com.ubt.unittestsample

import com.ubt.unittestsample.mockito.Person
import com.ubt.unittestsample.mockito.PersonDao
import com.ubt.unittestsample.mockito.PersonRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatcher
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.internal.InOrderImpl
import org.mockito.kotlin.*

class IntRangeMatcher: ArgumentMatcher<Int> {
    override fun matches(argument: Int?): Boolean {
        return argument?.let {
            it > 10
        }?: false
    }
}

class MockitoPersonRepositoryTest {
    lateinit var mockPersonDao: PersonDao
    lateinit var personRepository: PersonRepository

    @Before
    fun setup() {
        //模拟PersonDao对象
        mockPersonDao = mock()

        //when..then* ...这种操作叫做Stubbing（）
        // 使用when、thenReturn方法设置调用链中方法的返回值
        //argThat可以使用自定义参数匹配器
//        `when`(mockPersonDao.getPerson(argThat(IntRangeMatcher()))).thenReturn(Person(1, "lzh"))
        //可以直接使用lambda表达式
        Mockito.`when`(mockPersonDao.getPerson(argThat{ arg -> arg > 10})).thenReturn(Person(1, "lzh"))
        //使用isA方法判断参数类型
        `when`(mockPersonDao.updatePerson(isA())).thenReturn(true)

        personRepository = PersonRepository(mockPersonDao)
    }

    @Test
    fun update_FindId_ReturnTrue() {
        println("update_FindId_ReturnTrue")
        val result = personRepository.update(11, "lzh")
        assertTrue("must true", result)
        //verify方法验证的一定是mock对象
        verify(mockPersonDao, times(1)).getPerson(11)

        //这里的验证对象也必须是mock对象
        var order = inOrder(mockPersonDao)
        //以下两个测试通过。getPerson()在updatePerson()前面执行，调换两个verify顺序后，测试失败。
        order.verify(mockPersonDao).getPerson(11)
        order.verify(mockPersonDao).updatePerson(isA())
    }

    @Test
    fun update_NotFindId_ReturnTrue() {
        println("update_NotFindId_ReturnTrue")
        val result = personRepository.update(2, "lzh")
        assertTrue("not be true", result)
    }
}