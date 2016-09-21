package fokot.docker.scala.service

import java.util

import com.google.common.collect.ImmutableMap
import com.spotify.docker.client.DefaultDockerClient
import com.spotify.docker.client.messages.{ContainerConfig, HostConfig, PortBinding}

/* Testing Spotify client */
object DCTest /* extends App */ {

  // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
  val docker = DefaultDockerClient.fromEnv().build()

  // Pull an image
  docker.pull("postgres:9.5")

  val hostConfig = HostConfig.builder().portBindings(ImmutableMap.of("5432/tcp", util.Arrays.asList(PortBinding.of("", "9999")))).build()

  // Create container with exposed ports
  val containerConfig = ContainerConfig.builder()
    .hostConfig(hostConfig)
    .image("postgres:9.5")
    .exposedPorts("5432/tcp")
      .env("name",  "postgres", "POSTGRES_PASSWORD", "a")
//    .cmd("sh", "-c", "while :; do sleep 1; done")
    .build()

  val id = docker.createContainer(containerConfig).id()


  // Start container
  docker.startContainer(id)

  println("spim")
  Thread.sleep(60000)
  println("zobudeny")

  // Kill container
  docker.killContainer(id)

  // Remove container
  docker.removeContainer(id)

  // Close the docker client
  docker.close()

}
