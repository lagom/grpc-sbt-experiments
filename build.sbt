import com.trueaccord.scalapb.Scalapb
import sbt.Keys.libraryDependencies

organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

val scalapbVersion = "0.6.0-pre4"

lazy val `helloscala` = (project in file("."))
  .settings(common)
  .aggregate(`helloscala-api`)

lazy val `helloscala-api` = (project in file("helloscala-api"))
  .enablePlugins(ProtocPlugin)
  .settings(
    PB.protoSources in Compile := Seq(file("helloscala-api/src/main/protobuf"))
  )
  .settings(common ++ scalapbSettings)


// TODO: generate grpc for java in any platform. See https://gitter.im/trueaccord/ScalaPB?at=59230464fa63ba2f766aba1b
lazy val scalapbSettings = {
  Seq(
    PB.targets in Compile := Seq(
      PB.gens.java -> (sourceManaged in Compile).value,
      scalapb.gen(javaConversions = true) -> (sourceManaged in Compile).value
    )
  )
}
lazy val common = Seq(
  libraryDependencies ++= Seq(
    "com.trueaccord.scalapb" %% "scalapb-runtime" % scalapbVersion,
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % scalapbVersion,
    "io.grpc" % "grpc-netty" % "1.2.0"
  )
)
