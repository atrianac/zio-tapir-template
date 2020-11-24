#!/bin/sh
docker build . -t ztapir-api-graalvm -f Dockerfile.graalvm
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 ztapir-api-graalvm"