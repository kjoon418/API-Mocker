package framework.repository

import framework.exception.KeyDuplicateException
import java.util.concurrent.ConcurrentHashMap

class MemoryRepository<T : Entity<K>, K> : Repository<T, K> {
    private val entities: ConcurrentHashMap<K, T> = ConcurrentHashMap()

    override fun save(entity: T): T {
        check(!entities.containsKey(entity.key)) { KeyDuplicateException() }

        entities[entity.key] = entity

        return entity
    }

    override fun save(entities: Collection<T>): List<T> {
        for (entity: T in entities) {
            save(entity)
        }

        return entities.toList()
    }

    override fun findAll(): List<T> {
        return entities.values.toList()
    }

    override fun findByKey(key: K): T? {
        return entities[key]
    }

    override fun contains(entity: T): Boolean {
        return entities.containsKey(entity.key)
    }

    override fun contains(key: K): Boolean {
        return entities.containsKey(key)
    }

    override fun deleteAll() {
        entities.clear()
    }

    override fun delete(entity: T) {
        entities.remove(entity.key)
    }

    override fun deleteByKey(key: K) {
        entities.remove(key)
    }
}
