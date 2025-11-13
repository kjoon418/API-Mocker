package framework.server

import framework.config.ConfigProperty.*
import org.apache.catalina.startup.Tomcat
import org.slf4j.LoggerFactory
import java.io.File

class HttpServer (
    private val servlet: Servlet,

    private val port: Int = ServerPort.value,
    private val baseDirPath: String = ServerBaseDir.value,
    private val servletName: String = ServletName.value
) : Server {
    private val tomcat = Tomcat()

    private val log = LoggerFactory.getLogger(HttpServer::class.java)

    override fun run() {
        initTomcat()
        setupContext()

        tomcat.start()
        log.info("Server Started")

        tomcat.server.await()
    }

    private fun initTomcat() {
        tomcat.setPort(port)
        tomcat.connector
    }

    private fun setupContext() {
        val baseDir = File(baseDirPath).apply { mkdirs() }
        tomcat.setBaseDir(baseDir.absolutePath)

        val context = tomcat.addContext(ROOT_CONTEXT_PATH, baseDir.absolutePath)
        Tomcat.addServlet(context, servletName, servlet)
        context.addServletMappingDecoded(ROOT_DIRECTORY, servletName)
    }

    companion object {
        private const val ROOT_CONTEXT_PATH = ""
        private const val ROOT_DIRECTORY = "/"
    }
}
