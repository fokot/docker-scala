package fokot.docker.scala.model

import scala.concurrent.Future
import fokot.scala.second.model.{Cat, Color}
import fokot.scala.second.model.Color._
import slick.jdbc.PostgresProfile.api._


class Cats(tag: Tag) extends Table[Cat](tag, "cat") {

  implicit val colorMapper = MappedColumnType.base[Color, String](
    e => e.toString,
    s => Color.withName(s)
  )

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def color = column[Color]("color")
  def * = (id, name, color) <> (Cat.tupled, Cat.unapply)
}

class DAO(db: Database) extends TableQuery(new Cats(_)) {

  import scala.concurrent.ExecutionContext.Implicits.global

  def findById(id: Long): Future[Option[Cat]] = {
    db.run(this.filter(_.id === id).result).map(_.headOption)
  }

  def create(cat: Cat): Future[Cat] = {
    db.run(this returning this.map(_.id) into ((c, ids) => c.copy(id = ids)) += cat)
  }

  def deleteById(id: Long): Future[Int] = {
    db.run(this.filter(_.id === id).delete)
  }

  def findAll(): Future[Seq[Cat]] = {
    db.run(this.result)
  }
}
