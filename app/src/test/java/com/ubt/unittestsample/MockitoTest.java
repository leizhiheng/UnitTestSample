package com.ubt.unittestsample;

import com.ubt.unittestsample.mockito.PersonDao;

import org.junit.Before;
import org.mockito.Mock;

public class MockitoTest {

    @Mock
    PersonDao personDao;

    @Before
    public void setup() {

    }
}
