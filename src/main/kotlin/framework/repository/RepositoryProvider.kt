package framework.repository

object RepositoryProvider {
    private lateinit var repositoryFactory: RepositoryFactory

    fun init(repositoryFactory: RepositoryFactory) {
        this.repositoryFactory = repositoryFactory
    }

    fun <T : Entity<K>, K> get(
        entityClass: Class<T>,
        keyClass: Class<K>
    ): Repository<T, K> {
        return repositoryFactory.get(entityClass, keyClass)
    }
}
