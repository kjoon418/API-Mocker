package framework.action

interface ActionScanner {
    fun scan(basePackages: List<String>): Actions
}
