
lazy val testContainersScalaVersion = "0.40.11"
lazy val testContainersWarp10Version = "1.0.6"
lazy val akkaVersion = "2.6.20"

credentials += Credentials("GnuPG Key ID", "gpg", "B11C53C05D413713BDD3660FA7B8F38C536F1DF2", "ignored")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.clever-cloud",
      scalaVersion := "2.13.10",
      version := "2.0.1",
    )),
    name := "testcontainers-scala-warp10",
    licenses := List("MIT" -> new URL("https://mit-license.org/")),
    homepage := Some(url("https://github.com/CleverCloud/testcontainers-scala-warp10")),
    resolvers += Resolver.mavenLocal,
    libraryDependencies ++= Seq(
      "com.dimafeng" %% "testcontainers-scala-core" % testContainersScalaVersion,
      "com.clever-cloud" % "testcontainers-warp10" % testContainersWarp10Version,

      "net.java.dev.jna" % "jna" % "5.12.1" % Test,
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "com.typesafe.akka" %% "akka-protobuf-v3" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersScalaVersion % Test,
      "org.slf4j" % "slf4j-jdk14" % "1.7.25" % Test,
      "io.moia" %% "scala-http-client" % "5.1.0" % Test,
    ),
    publishMavenStyle := true,
    Test / publishArtifact := false,
    pomIncludeRepository := { _ => false },
    ThisBuild / publishTo := {
      val nexus = "https://s01.oss.sonatype.org/"
      if(isSnapshot.value)
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
    usePgpKeyHex("B11C53C05D413713BDD3660FA7B8F38C536F1DF2"),
  )
