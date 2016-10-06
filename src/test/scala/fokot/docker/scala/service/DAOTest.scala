package fokot.docker.scala.service

import com.typesafe.config.ConfigFactory
import com.whisk.docker._
import com.whisk.docker.impl.spotify.DockerKitSpotify
import com.whisk.docker.specs2.DockerTestKit
import fokot.docker.scala.model.DAO
import fokot.scala.second.model.{Cat, Color}
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import slick.jdbc.PostgresProfile.api._

trait DockerPostgresService extends DockerKitSpotify {
  import scala.concurrent.duration._

  val db = ConfigFactory.load().getConfig("postgres")
  val Url = db.getString("url")
  val PostgresAdvertisedPort = "\\d+".r.findFirstIn(Url).get.toInt
  val DbName = Url.substring(Url.lastIndexOf("/") + 1)
  val User = db.getString("user")
  val Password = db.getString("password")

  val postgresContainer = DockerContainer("postgres:9.5")
    .withPorts((5432, Some(PostgresAdvertisedPort)))
    .withEnv(s"POSTGRES_USER=$User", s"POSTGRES_PASSWORD=$Password")
    .withReadyChecker(
      new PostgresReadyChecker(User, Password, Some(PostgresAdvertisedPort)).looped(10, 1 second)
    )

  abstract override def dockerContainers: List[DockerContainer] =
    postgresContainer :: super.dockerContainers
}

class DAOTest extends Specification with DockerPostgresService with DockerTestKit {

  "should save things to db and read them" >> {  implicit ee: ExecutionEnv =>

    val db = Database.forConfig("postgres")
    val dao = new DAO(db)


    db.run(dao.schema.create) must throwAn[Exception].not.await

    dao.create(Cat(0, "Micka", Color.white))
    dao.create(Cat(0, "Cicka", Color.grey))
    dao.create(Cat(0, "Paulina", Color.black))
    dao.findAll.map(_.size)(ee.executionContext) must equalTo(33).await
  }

}