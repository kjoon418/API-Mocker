package framework.config

sealed class ConfigProperty<T : Any>(
    val path: String,
    private val defaultValue: String? = null,
    private val valueMapper: (rawValue: Any?) -> T
) {
    val value: T
        get() {
            val rawValue = PropertyLoader.get(path)
            val valueToMap = rawValue ?: defaultValue
                ?: throw IllegalStateException("$PROPERTY_NOT_EXIST (path: $path)")

            return valueMapper(valueToMap)
        }

    data object BasePackage : ConfigProperty<List<String>>(
        path = "framework.base-packages",
        valueMapper = MapUtils.mapAsList
    )

    data object CharacterEncoding : ConfigProperty<String>(
        path = "framework.encoding",
        defaultValue = "UTF-8",
        valueMapper = MapUtils.mapAsString
    )

    data object ServerPort : ConfigProperty<Int>(
        path = "server.port",
        defaultValue = "8080",
        valueMapper = MapUtils.mapAsInt
    )

    data object ServerBaseDir : ConfigProperty<String>(
        path = "server.base-dir",
        defaultValue = "tomcat-temp",
        valueMapper = MapUtils.mapAsString
    )

    data object ServletName : ConfigProperty<String>(
        path = "server.servlet-name",
        defaultValue = "FrameworkServlet",
        valueMapper = MapUtils.mapAsString
    )

    data object JwtSecret : ConfigProperty<String>(
        path = "jwt.secret",
        valueMapper = MapUtils.mapAsString
    )

    data object JwtIssuer : ConfigProperty<String>(
        path = "jwt.issuer",
        valueMapper = MapUtils.mapAsString
    )

    data object JwtExpiredMs : ConfigProperty<Long>(
        path = "jwt.expired-ms",
        valueMapper = MapUtils.mapAsLong
    )

    companion object {
        private const val PROPERTY_NOT_EXIST = "해당 프로퍼티 값이 존재하지 않습니다."
    }
}

private object MapUtils {
    val mapAsString: (Any?) -> String = { it?.toString()
        ?: throw IllegalStateException(exceptionMessage(it, String::class.java))
    }

    val mapAsInt: (Any?) -> Int = { rawValue ->
        when (rawValue) {
            is Number -> rawValue.toInt()
            is String -> rawValue.toInt()
            else -> throw NumberFormatException(exceptionMessage(rawValue, Int::class.java))
        }
    }

    val mapAsLong: (Any?) -> Long = { rawValue ->
        when (rawValue) {
            is Number -> rawValue.toLong()
            is String -> rawValue.toLong()
            else -> throw NumberFormatException(exceptionMessage(rawValue, Long::class.java))
        }
    }

    val mapAsList: (Any?) -> List<String> = { rawValue ->
        when (rawValue) {
            is List<*> -> rawValue.map { it.toString() }
            is String -> rawValue.split(',').map { it.trim() }
            else -> throw IllegalStateException(exceptionMessage(rawValue, List::class.java))
        }
    }

    private fun exceptionMessage(value: Any?, typeToMap: Class<*>): String {
        return "${value}를 ${typeToMap.name}으로 변환할 수 없습니다."
    }
}
