
lazy val testContainersScalaVersion = "0.39.3"
lazy val testContainersWarp10Version = "1.0.1"
lazy val akkaVersion = "2.6.14"

lazy val root = (project in file(".")).
                                      settings(
                                        inThisBuild(List(
                                          organization := "com.clevercloud",
                                          scalaVersion := "2.13.5",
                                          version := testContainersScalaVersion,
                                        )),
                                        name := "testcontainers-scala-warp10",
                                        resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
                                        libraryDependencies ++= Seq(
                                          "com.dimafeng" %% "testcontainers-scala-core" % testContainersScalaVersion,
                                          "com.clever-cloud" % "testcontainers-warp10" % testContainersWarp10Version,


                                          "net.java.dev.jna" % "jna" % "5.8.0" % Test,
                                          "org.scalatest" %% "scalatest" % "3.2.2" % Test,
                                          "com.typesafe.akka" %% "akka-protobuf-v3" % akkaVersion % Test,
                                          "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion % Test,
                                          "com.typesafe.akka" %% "akka-stream" % akkaVersion % Test,
                                          "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
                                          "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersScalaVersion % Test,
                                          "io.moia" %% "scala-http-client" % "4.3.1" % Test,
                                        ),
                                        Test / fork := true,
                                      )
