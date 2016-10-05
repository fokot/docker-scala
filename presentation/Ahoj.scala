import java.io.File
import scala.sys.process._

object Ahoj extends App {

  val counter = ("cat counter" !!).trim.toInt
  println(s"Ahoj $counter")
  s"echo ${counter + 1}" #> new File("counter") !
}
