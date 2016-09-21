package fokot.docker.scala.service


import com.typesafe.config.ConfigFactory
import com.whisk.docker._
import com.whisk.docker.impl.spotify.DockerKitSpotify
import com.whisk.docker.specs2.DockerTestKit
import fokot.scala.second.model.Color
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import slick.jdbc.PostgresProfile.api._

trait DockerPostgresService extends DockerKitSpotify {
  import scala.concurrent.duration._
  val db = ConfigFactory.load().getConfig("postgres")
  def PostgresAdvertisedPort = "\\d+".r.findFirstIn(db.getString("url")).get.toInt
  def DbName = db.getString("url").substring(db.getString("url").lastIndexOf("/") + 1)
  val postgresContainer = DockerContainer("postgres:9.5")
    .withPorts((5432, Some(PostgresAdvertisedPort)))
    .withEnv(s"POSTGRES_USER=${db.getString("user")}", s"POSTGRES_PASSWORD=${db.getString("password")}")
    .withReadyChecker(
      new PostgresReadyChecker(DbName, db.getString("user"), db.getString("password")).looped(10, 1 second)
    )

  abstract override def dockerContainers: List[DockerContainer] =
    postgresContainer :: super.dockerContainers
}

class CatRepositorySlickTest extends Specification with DockerPostgresService with DockerTestKit {

  import fokot.docker.scala.model.Db._

  "should save things to db and read them" >> {  implicit ee: ExecutionEnv =>

    val db = Database.forConfig("postgres")

    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
//      cats.schema.drop,
      cats.schema.create,

      // Insert some suppliers
      cats += (Some(1), "Micka", Color.white),
      cats += (Some(2), "Cicka", Color.grey),
      cats += (Some(3), "Paulina", Color.black)
    )
    db.run(setup) must throwAn[Exception].not.await
    db.run(cats.countDistinct.result) must equalTo(3).await
  }

}