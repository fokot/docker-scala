enablePlugins(DockerPlugin)

name := "docker-scala"

version := "1.2"

scalaVersion := "2.11.8"

organization := "fokot"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4.1210",
  "com.typesafe.slick" % "slick_2.11" % "3.2.0-M1",
  "com.github.finagle" %% "finch-playjson" % "0.11.0-M3",
  "com.whisk" %% "docker-testkit-specs2" % "0.9.0-M10" % Test,
  "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.0-M10" % Test,
  "org.specs2" %% "specs2" % "3.7" % Test
)

mainClass in Compile := Some("fokot.docker.scala.main.Main")

//test in assembly := {}

imageNames in docker := Seq(
  // Sets the latest tag
  ImageName(s"${organization.value}/${name.value}:latest"),

  // Sets a name with a tag that contains the project version
  ImageName(
    namespace = Some(organization.value),
    repository = name.value,
    tag = Some("v" + version.value)
  )
)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
    expose(8080)
  }
}

    