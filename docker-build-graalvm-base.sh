#!/bin/sh
docker build . -t ztapir/graalvm-ce:20.3.0-java11 -f Dockerfile.prebuild.graalvm
echo
echo
echo "Base image built with tag ztapir/graalvm-ce:20.3.0-java11"