import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

enablePlugins(GitVersioning, GraalVMNativeImagePlugin)

val ZioVersion    = "1.0.3"
val Http4sVersion = "0.21.7"
val CirceVersion  = "0.13.0"
val ZioCatsIterop = "2.1.4.0"
val ZioConfig     = "1.0.0-RC26"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(
    organization := "com.ztapir.ys",
    name := "zio-tapir-api",
    version := "1.0.0",
    scalaVersion := "2.13.3",
    maxErrors := 3,
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server"  % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client"  % Http4sVersion,
      "org.http4s" %% "http4s-circe"         % Http4sVersion,
      "org.http4s" %% "http4s-dsl"           % Http4sVersion,
      "io.circe"   %% "circe-generic"        % CirceVersion,
      "io.circe"   %% "circe-generic-extras" % CirceVersion,
      "dev.zio"    %% "zio"                  % ZioVersion,
      "dev.zio"    %% "zio-interop-cats"     % ZioCatsIterop,
      "dev.zio"    %% "zio-config"           % ZioConfig,
      "dev.zio"    %% "zio-test"             % ZioVersion % "test",
      "dev.zio"    %% "zio-test-sbt"         % ZioVersion % "test",
      "dev.zio"    %% "zio-test-magnolia"    % ZioVersion % "test"
    )
  )

Global / cancelable := false

javacOptions ++= Seq("-source", "11", "-target", "11")

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

scalacOptions --= Seq(
  "-target:jvm-11",
  "-Xfatal-warnings"
)

initialize := {
  val _           = initialize.value
  val javaVersion = sys.props("java.specification.version")
  if (javaVersion != "11")
    sys.error("Java 11 is required for this project. Found " + javaVersion + " instead")
}

publishArtifact in (Compile, packageDoc) := false

publishArtifact in packageDoc := false

sources in (Compile, doc) := Seq.empty

graalVMNativeImageOptions ++= Seq(
  "--verbose",
  "--no-server",
  "--no-fallback",
  "allow-incomplete-classpath",
  "--install-exit-handlers",
  "-J-Xmx8G",
  "-J-Xms4G",
  "-H:+ReportUnsupportedElementsAtRuntime",
  "-H:+ReportExceptionStackTraces",
  "-H:+TraceClassInitialization",
  "-H:+PrintClassInitialization",
  "-H:+RemoveSaturatedTypeFlows",
  "--initialize-at-build-time=scala.runtime.Statics$VM"
)

val os = System.getProperty("os.name").toLowerCase()

val graalSpcOpts = os match {
  case linux if linux.contains("linux") => Seq("--static", "-H:UseMuslC=../../bundle/")
  case _                                => Seq.empty
}
graalVMNativeImageOptions ++= graalSpcOpts

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("chk", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")
