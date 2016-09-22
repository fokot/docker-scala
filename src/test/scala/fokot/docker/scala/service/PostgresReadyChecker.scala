package fokot.docker.scala.service

import java.sql.DriverManager

import com.whisk.docker.{DockerCommandExecutor, DockerContainerState, DockerReadyChecker}

import scala.concurrent.ExecutionContext
import scala.util.Try

class PostgresReadyChecker(user: String, password: String, port: Option[Int] = None) extends DockerReadyChecker {

  override def apply(container: DockerContainerState)(implicit docker: DockerCommandExecutor, ec: ExecutionContext) =
    container.getPorts().map(ports =>
      Try {
        Class.forName("org.postgresql.Driver")
        val url = s"jdbc:postgresql://${docker.host}:${port.getOrElse(ports.values.head)}/"
        Option(DriverManager.getConnection(url, user, password))
          .map(_.close)
          .isDefined
      }.getOrElse(false)
    )
}