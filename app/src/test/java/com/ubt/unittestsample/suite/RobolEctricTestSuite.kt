package com.ubt.unittestsample.suite

import com.ubt.unittestsample.JUnitTest
import com.ubt.unittestsample.RepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(JUnitTest::class, RepositoryTest::class)
class RobolEctricTestSuite