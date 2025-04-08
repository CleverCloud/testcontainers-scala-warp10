lazy val testContainersScalaVersion = "0.43.0"
lazy val testContainersWarp10Version = "2.0.0"
lazy val akkaVersion = "2.8.8"

ThisBuild / semanticdbVersion := "4.12.1"

credentials += Credentials("GnuPG Key ID", "gpg", "B11C53C05D413713BDD3660FA7B8F38C536F1DF2", "ignored")

lazy val root = (project in file(".")).settings(
  organization := "com.clever-cloud",
  scalaVersion := "2.13.16",
  version := "2.1.2",
  crossScalaVersions := Seq(scalaVersion.value, "3.6.2"),
  name := "testcontainers-scala-warp10",
  licenses := List("MIT" -> new URI("https://mit-license.org/").toURL),
  homepage := Some(url("https://github.com/CleverCloud/testcontainers-scala-warp10")),
  resolvers += Resolver.mavenLocal,
  resolvers += Resolver.mavenCentral,
  libraryDependencies ++= Seq(
    "com.dimafeng" %% "testcontainers-scala-core" % testContainersScalaVersion,
    "com.clever-cloud" % "testcontainers-warp10" % testContainersWarp10Version,
    "net.java.dev.jna" % "jna" % "5.17.0" % Test,
    "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    ("com.typesafe.akka" %% "akka-protobuf-v3" % akkaVersion % Test).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-stream-typed" % akkaVersion % Test).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-stream" % akkaVersion % Test).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test).cross(CrossVersion.for3Use2_13),
    "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersScalaVersion % Test,
    "org.slf4j" % "slf4j-jdk14" % "2.0.17" % Test,
    ("io.moia" %% "scala-http-client" % "5.2.0" % Test).cross(CrossVersion.for3Use2_13)
  ),
  publishMavenStyle := true,
  Test / publishArtifact := false,
  pomIncludeRepository := { _ => false },
  ThisBuild / publishTo := {
    val nexus = "https://s01.oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/CleverCloud/testcontainers-scala-warp10"),
      "scm:git:git@github.com:CleverCloud/testcontainers-scala-warp10"
    )
  ),
  developers := List(
    Developer("judu", "Julien Durillon", "julien.durillon@clever-cloud.com", url("https://github.com/judu"))
  ),
  Test / fork := true,
  usePgpKeyHex("A7B8F38C536F1DF2")
)
