package fokot.scala.second.model

object Color extends Enumeration {
  type Color = Value
  val black, white, grey, green, red = Value
}

case class Cat(id: Long, name: String, color: Color.Color)