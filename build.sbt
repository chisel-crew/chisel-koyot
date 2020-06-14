// Top level build file for Chisel projects with Koyot Connector

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("neurodyne-systems", "koyot-client")
)

lazy val commonSettings = Seq(
// Refine scalac params from tpolecat
  scalacOptions --= Seq(
    "-Xfatal-warnings"
  ),
  scalacOptions ++= Seq(
    "-Xsource:2.11",
    "-language:reflectiveCalls",
    "-language:postfixOps"
  )
)

lazy val zioDeps = libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % Version.zio
)

lazy val koyotDeps = libraryDependencies ++= Seq(
  "koyot-client" %% "koyot-client" % Version.koyot
)

lazy val root = (project in file("."))
  .settings(
    organization := "org.demo",
    name := "demo",
    version := "0.0.1",
    scalaVersion := "2.12.11",
    maxErrors := 3,
    commonSettings,
    zioDeps,
    koyotDeps
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile it:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
