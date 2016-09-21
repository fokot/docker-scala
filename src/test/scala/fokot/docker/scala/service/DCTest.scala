package fokot.docker.scala.service

import java.util

import com.google.common.collect.ImmutableMap
import com.spotify.docker.client.{DefaultDockerClient, DockerClient}
import com.spotify.docker.client.messages.{AuthConfig, ContainerConfig, HostConfig, PortBinding}

object DCTest extends App {

  // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
  val docker = DefaultDockerClient.fromEnv().build()

  // Pull an image
  docker.pull("postgres:9.5")
//
//  // Bind container ports to host ports
//  final String[] ports = {"80", "22"};
//  final Map<String, List<PortBinding>> portBindings = new HashMap<String, List<PortBinding>>();
//  for (String port : ports) {
//    List<PortBinding> hostPorts = new ArrayList<PortBinding>();
//    hostPorts.add(PortBinding.of("0.0.0.0", port));
//    portBindings.put(port, hostPorts);
//  }

  // Bind container port 443 to an automatically allocated available host port.
//  List<PortBinding> randomPort = new ArrayList<PortBinding>();
//  randomPort.add(PortBinding.randomPort("0.0.0.0"));
//  portBindings.put("443", randomPort);

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

//  // Inspect container
//  final ContainerInfo info = docker.inspectContainer(id);

  // Start container
  docker.startContainer(id)

//  // Exec command inside running container with attached STDOUT and STDERR
//  final String[] command = {"bash", "-c", "ls"};
//  final String execId = docker.execCreate(
//    id, command, DockerClient.ExecCreateParam.attachStdout(),
//    DockerClient.ExecCreateParam.attachStderr());
//  final LogStream output = docker.execStart(execId);
//  final String execOutput = output.readFully();

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
