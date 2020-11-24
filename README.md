# REST API in Scala

PoC, whose purpose is to create a REST API in Scala. The project exhibits some crucial features described below:

- Define endpoints in a typeful, functional, and streaming way using http4s & tapir.
- The project integrates ZIO. ZIO is a zero-dependency Scala library for asynchronous and concurrent programming.

## Environment installation

### SDKs and build tools

- Install sdkman from [SDK Install](https://sdkman.io/install). sdkman is a tool for managing parallel versions of multiple Software Development Kits on most Unix based systems. It provides a convenient Command Line Interface (CLI) and API for installing, switching, removing and listing Candidates.

- Using sdk, install jdk, sbt and scala 2.13 as follows:

```batch
sdk install java 11.0.2-open
sdk install sbt 1.3.13
sdk install scala 2.13.2
```

- Set up the installations for the current use: 

```batch
sdk use java 11.0.2-open
sdk use sbt 1.3.13
sdk use scala 2.13.2
```

### IDE

- Install VS Code from [here](https://code.visualstudio.com/)
- Install Metals for VS Code from [here](https://scalameta.org/metals/docs/editors/vscode.html)

### Docker

- Install docker from [here](https://docs.docker.com/get-docker/)

## Environment configuration

### IDE 

- Clone the repository from [here](ssh://git@tools.adidas-group.com:7999/~trianale/ys-backend-api-scala.git)
- Open the repository from a [command](https://code.visualstudio.com/docs/setup/mac) line using VS Code:

```batch
code .
```

Note: Metals should configure the project, fetching the dependencies using bloop.

### Docker

- If you are running docker on [Windows](https://docs.docker.com/docker-for-windows/) or [Mac](https://docs.docker.com/docker-for-mac/), ensure that the memory is setting up to 8GB

## Build Docker images

### GraalVM

```batch
./docker-build-graalvm-base.sh
./docker-build-graalvm.sh
```

### JVM


```batch
./docker-build-jvm.sh
```

|Image|Size|Build Time|
|---|---|---|
|GraalVM Native|33 MB|6 min|
|JVM Amazon Coretto Alpine|356 MB|6 min|

