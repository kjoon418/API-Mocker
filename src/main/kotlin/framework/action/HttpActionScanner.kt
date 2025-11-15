package framework.action

import framework.constants.HttpMethod
import framework.exception.ApplicationConfigFailException
import framework.router.PathTemplate
import framework.router.RouteKey
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder

object HttpActionScanner : ActionScanner {
    override fun scan(basePackages: List<String>): Actions {
        val reflections = buildReflections(basePackages)
        val types = reflections.findTypesOfHttpAction()

        return buildActionRegistry(types)
    }

    private fun buildReflections(basePackages: List<String>): Reflections {
        val contextClassLoader = Thread.currentThread().contextClassLoader

        val reflectionsConfig = ConfigurationBuilder()
            .setClassLoaders(arrayOf(contextClassLoader))
            .setScanners(Scanners.TypesAnnotated, Scanners.SubTypes)
            .apply {
                basePackages.forEach { addUrls(ClasspathHelper.forPackage(it)) }
            }

        return Reflections(reflectionsConfig)
    }

    private fun Reflections.findTypesOfHttpAction(): List<Class<*>> {
        return get(Scanners.TypesAnnotated.with(HttpAction::class.java).asClass<Any>())
            .filter { Action::class.java.isAssignableFrom(it) }
    }

    private fun buildActionRegistry(actionTypes: List<Class<*>>): Actions {
        val actions = Actions()

        for (actionType in actionTypes) {
            val annotation = actionType.httpActionAnnotation
            val actionInstance = instantiateAction(actionType)

            annotation.toRouteKeys()
                .forEach { actions.register(it, actionInstance) }
        }

        return actions
    }

    private val Class<*>.httpActionAnnotation: HttpAction
        get() {
            return getAnnotation(HttpAction::class.java)
                ?: throw ApplicationConfigFailException("$ANNOTATION_MISSING : $this")
        }

    private fun instantiateAction(clazz: Class<*>): Action<*, *> {
        return clazz.getDeclaredConstructor()
            .apply { isAccessible = true }
            .newInstance() as Action<*, *>
    }

    private fun HttpAction.toRouteKeys(): List<RouteKey> {
        return methodsToRegister.map { RouteKey(it, PathTemplate(path)) }
    }

    private val HttpAction.methodsToRegister: Array<HttpMethod> get() {
        if (method.isEmpty()) {
            return HttpMethod.entries.toTypedArray()
        }

        return method
    }

    private const val ANNOTATION_MISSING = "HttpAction 애노태이션이 누락되었습니다"
}
