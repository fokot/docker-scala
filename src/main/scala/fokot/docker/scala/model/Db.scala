package fokot.docker.scala.model

import fokot.scala.second.model.Color
import fokot.scala.second.model.Color._
import slick.jdbc.PostgresProfile.api._

object Db {

  implicit val colorMapper = MappedColumnType.base[Color, String](
    e => e.toString,
    s => Color.withName(s)
  )

  class Cats(tag: Tag) extends Table[(Option[Long], String, Color)](tag, "cat") {
    def id = column[Option[Long]]("id", O.PrimaryKey)
    def name = column[String]("name")
    def color = column[Color]("color")
    def * = (id, name, color)
  }

  val cats = TableQuery[Cats]
}
