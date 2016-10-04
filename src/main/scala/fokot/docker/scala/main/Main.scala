package fokot.docker.scala.main

import io.finch._
import com.twitter.finagle.Http

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.Try


object Main extends App {

  val api: Endpoint[String] = get("hello") { Ok("Hello, World!") }

  val server = Http.server.serve(":8080", api.toServiceAs[Text.Plain])

  Try(Await.ready(Promise().future, 1 day))
  server.close()
  println("main finished")
}



