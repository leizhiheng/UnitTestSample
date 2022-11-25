package com.ubt.unittestsample.mockito

interface PersonDao {
    fun getPerson(id: Int): Person?

    fun updatePerson(person: Person): Boolean
}