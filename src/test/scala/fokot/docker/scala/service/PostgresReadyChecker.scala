package fokot.docker.scala.service

import java.sql.DriverManager

import com.whisk.docker.{DockerCommandExecutor, DockerContainerState, DockerReadyChecker}

import scala.concurrent.ExecutionContext
import scala.util.Try

class PostgresReadyChecker(dbname: String, username: String, password: String) extends DockerReadyChecker {

  override def apply(container: DockerContainerState)(implicit docker: DockerCommandExecutor, ec: ExecutionContext) =
    container.getPorts().map(x => x.values.head).map(port =>
      Try {
        Class.forName("org.postgresql.Driver")
        val connection = DriverManager.getConnection(s"jdbc:postgresql://${docker.host}:$port/$dbname", username, password)
        connection != null
      }.getOrElse(false)
    )
}