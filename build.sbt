name := "grpc-sample"

version := "1.0"

scalaVersion := "2.11.11"


//PB.targets in Compile := Seq(
//  PB.gens.java -> (sourceManaged in Compile).value
//)

PB.targets in Compile := Seq(
  PB.gens.java -> (sourceManaged in Compile).value,
  scalapb.gen(javaConversions = true) -> (sourceManaged in Compile).value
)

// Changing where to look for protos to compile (default src/main/protobuf):
//PB.protoSources in Compile := Seq(sourceDirectory.value / "somewhere")


// https://mvnrepository.com/artifact/io.grpc/grpc-netty
//libraryDependencies ++= Seq(
//  "io.grpc" % "grpc-netty" % "1.2.0",
//  "io.grpc" % "grpc-protobuf" % "1.2.0",
//  "io.grpc" % "grpc-stub" % "1.2.0"
//)

libraryDependencies ++= Seq(
  "com.trueaccord.scalapb" % "scalapb-runtime_2.11" % "0.6.0-pre4",
  "com.trueaccord.scalapb" % "scalapb-runtime-grpc_2.11" % "0.6.0-pre4",
  "io.grpc" % "grpc-netty" % "1.2.0"
)
