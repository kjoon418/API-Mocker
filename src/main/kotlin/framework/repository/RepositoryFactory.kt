package framework.repository

import java.util.concurrent.ConcurrentHashMap

class RepositoryFactory {
    private val repositoryBuilders = ConcurrentHashMap<Pair<Class<*>, Class<*>>, () -> Repository<*, *>>()
    private val repositories = ConcurrentHashMap<Pair<Class<*>, Class<*>>, Repository<*, *>>()

    fun register(
        entityClass: Class<*>,
        keyClass: Class<*>,
        implementationFactory: () -> Repository<*, *>
    ) {
        val key = Pair(entityClass, keyClass)
        repositoryBuilders[key] = implementationFactory
    }

    fun <T : Entity<K>, K> get(
        entityClass: Class<T>,
        keyClass: Class<K>
    ): Repository<T, K> {
        val key = Pair(entityClass, keyClass)

        val repository = repositories.computeIfAbsent(key) { createRepositoryByBuilder(key) }

        @Suppress("UNCHECKED_CAST")
        return repository as Repository<T, K>
    }

    private fun createRepositoryByBuilder(
        key: Pair<Class<*>, Class<*>>
    ): Repository<*, *> {
        return repositoryBuilders[key]?.invoke()
            ?: throw IllegalStateException("(${key.first}, ${key.second}) 에 대한 Repository 구현체 정책이 등록되지 않았습니다.")
    }
}
