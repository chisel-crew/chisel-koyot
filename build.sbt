resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val commonSettings = Seq(
// Refine scalac params from tpolecat
  scalacOptions --= Seq(
    "-Xfatal-warnings"
  ),
  scalacOptions ++= Seq(
    "-Xsource:2.11",
    "-language:reflectiveCalls",
    "-language:postfixOps",
    "-unchecked",
    "-deprecation"
  )
)

lazy val zioDeps = libraryDependencies ++= Seq(
  "dev.zio" %% "zio"         % Version.zio,
  "dev.zio" %% "zio-streams" % Version.zio
)

lazy val chiselDeps = libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % Version.chisel,
  "edu.berkeley.cs" %% "firrtl"  % Version.firrtl
)

lazy val rpcDeps = libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "io.grpc"              % "grpc-netty"            % Version.grpc
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
    chiselDeps,
    rpcDeps,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

PB.targets in Compile := Seq(
  scalapb.gen(grpc = true)          -> (sourceManaged in Compile).value,
  scalapb.zio_grpc.ZioCodeGenerator -> (sourceManaged in Compile).value
)

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile it:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")

scalafixDependencies in ThisBuild += "com.nequissimus" %% "sort-imports" % "0.5.2"
