import sbt.Keys.libraryDependencies
import grpc.sbt.sample.Versions

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test


lazy val `grpc-sbt-experiments` = (project in file("."))
  .settings(
    organization in ThisBuild := "com.example",
    version in ThisBuild := "1.0-SNAPSHOT",
    // the Scala version that will be used for cross-compiled libraries
    scalaVersion in ThisBuild := "2.11.8"
  )
  .aggregate(
    `helloscala-grpc-server`,
    `helloscala-lagom-client`,
    `hellojava-lagom-client`
  )

lazy val `helloscala-grpc-server` = (project in file("helloscala-grpc-server"))
  .enablePlugins(ProtocPlugin)
  .settings(grpcCommon)
  .settings(scalapbSettings("helloscala-grpc-server", forJava = false, forServer = true))

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

lazy val `hellojava-lagom-client` = (project in file("hellojava-lagom-client"))
  .enablePlugins(ProtocPlugin)
  .settings(grpcCommon)
  .settings(scalapbSettings("hellojava-lagom-client", forJava = true))
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslClient,
      "com.lightbend.lagom" %% "lagom-javadsl-integration-client" % "1.4.0-SNAPSHOT",
      lagomJavadslApi,
      lagomLogback
    )
  )


// ------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------

// TODO: generate grpc for java in any platform. See https://gitter.im/trueaccord/ScalaPB?at=59230464fa63ba2f766aba1b
def scalapbSettings(projectFolder: String, forJava: Boolean = false, forServer: Boolean = false) = {

  val protoSources = PB.protoSources in Compile := Seq(file(s"$projectFolder/src/main/protobuf"))

  if (forJava) {
    Seq(
      PB.targets in Compile := {
        Seq(
          scalapb.gen(javaConversions = true, grpc = forServer) -> (sourceManaged in Compile).value,
          PB.gens.java("3.3.1") -> (sourceManaged in Compile).value)
      },
      protoSources
    )
  } else {

    Seq(
      PB.targets in Compile := {
        Seq(
          scalapb.gen(javaConversions = false, grpc = forServer) -> (sourceManaged in Compile).value
        )
      },
      protoSources
    )
  }
}

lazy val grpcCommon = Seq(
  libraryDependencies ++= Seq(
    "com.trueaccord.scalapb" %% "scalapb-runtime" % Versions.scalapbVersion,
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % Versions.scalapbVersion,
    "io.grpc" % "grpc-netty" % "1.4.0"
  )
)
