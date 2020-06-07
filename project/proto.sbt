val zioGrpcVersion = "0.3.0+7-50ac111b-SNAPSHOT"

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.31")

libraryDependencies += "com.thesamet.scalapb"          %% "compilerplugin"   % "0.10.3"
libraryDependencies += "com.thesamet.scalapb.zio-grpc" %% "zio-grpc-codegen" % zioGrpcVersion
