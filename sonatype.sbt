// To sync with Maven central, you need to supply the following information:
publishMavenStyle := true

// Open-source license of your choice
licenses := Seq("MIT" -> url("https://mit-license.org/"))

// Where is the source code hosted: GitHub or GitLab?
import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("clevercloud", "testcontainers-scala-warp10", "julien.durillon@clever-cloud.com"))
