package framework.repository

interface Repository<T : Entity<K>, K> {
    fun save(entity: T): T
    fun save(entities: Collection<T>): List<T>
    fun findAll(): List<T>
    fun findByKey(key: K): T?
    fun contains(entity: T): Boolean
    fun contains(key: K): Boolean
    fun deleteAll()
    fun delete(entity: T)
    fun deleteByKey(key: K)
}
