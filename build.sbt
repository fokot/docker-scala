name := "docker-scala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4.1210",
  "com.typesafe.slick" % "slick_2.11" % "3.2.0-M1",
  "com.whisk" %% "docker-testkit-specs2" % "0.9.0-M10" % Test,
  "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.0-M10" % Test,
  "org.specs2" %% "specs2" % "3.7" % Test
)
    