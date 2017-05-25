import sbt.Keys.libraryDependencies
import grpc.sbt.sample.Versions

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test


lazy val `helloscala` = (project in file("."))
  .settings(
    organization in ThisBuild := "com.example",
    version in ThisBuild := "1.0-SNAPSHOT",
    // the Scala version that will be used for cross-compiled libraries
    scalaVersion in ThisBuild := "2.11.8"
  )
  .aggregate(`helloscala-grpc-server`, `helloscala-lagom-client`)

lazy val `helloscala-grpc-server` = (project in file("helloscala-grpc-server"))
  .enablePlugins(ProtocPlugin)
  .settings(grpcCommon)
  .settings(scalapbSettings("helloscala-grpc-server", forServer = true))

lazy val `helloscala-lagom-client` = (project in file("helloscala-lagom-client"))
  .enablePlugins(ProtocPlugin)
  .settings(grpcCommon)
  .settings(scalapbSettings("helloscala-lagom-client"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslClient,
      lagomScaladslApi,
      lagomLogback
    )
  )


// ------------------------------------------------------------------------------------------

// TODO: generate grpc for java in any platform. See https://gitter.im/trueaccord/ScalaPB?at=59230464fa63ba2f766aba1b
def scalapbSettings(projectFolder: String, forServer: Boolean = false) = {
  Seq(
    PB.targets in Compile := Seq(
      PB.gens.java -> (sourceManaged in Compile).value,
      scalapb.gen(
        javaConversions = true,
        grpc = forServer
      ) -> (sourceManaged in Compile).value
    ),
    PB.protoSources in Compile := Seq(file(s"$projectFolder/src/main/protobuf"))
  )
}

lazy val grpcCommon = Seq(
  libraryDependencies ++= Seq(
    "com.trueaccord.scalapb" %% "scalapb-runtime" % Versions.scalapbVersion,
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % Versions.scalapbVersion,
    "io.grpc" % "grpc-netty" % "1.2.0"
  )
)
