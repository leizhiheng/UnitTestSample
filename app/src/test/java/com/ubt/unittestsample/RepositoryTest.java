package com.ubt.unittestsample;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ubt.unittestsample.mockito.Person;
import com.ubt.unittestsample.mockito.PersonDao;
import com.ubt.unittestsample.mockito.PersonRepository;

import org.junit.Before;
import org.junit.Test;

public class RepositoryTest {

    private PersonDao mockPersonDao;
    private PersonRepository personRepository;

    @Before
    public void setup() {
        System.out.println("RepositoryTest setUp");
        mockPersonDao = mock(PersonDao.class);
        when(mockPersonDao.getPerson(1)).thenReturn(new Person(1, "lzh"));
        when(mockPersonDao.updatePerson(isA(Person.class))).thenReturn(true);
        personRepository = new PersonRepository(mockPersonDao);
    }

    @Test
    public void update_SameId_EqualTimes() {
        System.out.println("RepositoryTest update_SameId_EqualTimes");
        boolean result = personRepository.update(1, "lzh");
        assertTrue("must be true", result);
        verify(mockPersonDao, times(1)).updatePerson(isA(Person.class));
    }
}
