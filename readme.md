[![Build Status](https://travis-ci.org/fokot/docker-scala.svg?branch=master)](https://travis-ci.org/fokot/docker-scala)

# Docker-scala


## Testing with docker containers
Testing scala application with docker dependencies. Also in Travis-ci.

Docker integration is done by [docker-it-scala](https://github.com/whisklabs/docker-it-scala) 
and [Spotify's docker-client ](https://github.com/spotify/docker-client)

Look at DAOTest which has Postgres as docker dependency. Postgres database is started before tests and shut down after.

## Building docker docker image form application
To build docker image [sbt-docker](https://github.com/marcuslonnberg/sbt-docker) is used.
It starts docker container on port 8080 witch runs for 1 hour. To test it do

  
    sbt docker
    docker run -p 80:8080 default/docker-scala
    open http://localhost/hello

Test in assembly were disabled (idk why they don't run but run in `sbt test`).

## Project's dependencies graph
Show dependencies with `sbt dependencyTree` or `sbt dependencyBrowseGraph.
[Sbt-dependency-graph](https://github.com/jrudolph/sbt-dependency-graph) plugin was was used.