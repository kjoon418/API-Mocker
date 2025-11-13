package framework

import framework.config.FrameworkConfig

fun runWithFramework() {
    val server = FrameworkConfig.server

    server.run()
}
