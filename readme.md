[![Build Status](https://travis-ci.org/fokot/docker-scala.svg?branch=master)](https://travis-ci.org/fokot/docker-scala)

# Docker-scala

Testing scala application with docker dependencies. Also in Travis-ci.

Docker integration is done by [docker-it-scala](https://github.com/whisklabs/docker-it-scala) and [Spotify's docker-client ](https://github.com/spotify/docker-client)

Look at DAOTest which has Postgres as docker dependency. Postgres database is started before tests and shut down after.
