package com.ubt.unittestsample.mockito

class PersonRepository(private val personDao: PersonDao) {
    fun update(id: Int, name: String): Boolean {
        val p = personDao.getPerson(id)
        p?.apply {
            val pUpdate = Person(id, name)
            return personDao.updatePerson(pUpdate)
        }
        return false
    }
}