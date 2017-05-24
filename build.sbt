import sbt.Keys.libraryDependencies

organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

val scalapbVersion = "0.6.0-pre4"

lazy val `helloscala` = (project in file("."))
  .aggregate(`helloscala-api`, `helloscala-impl`)

lazy val `helloscala-api` = (project in file("helloscala-api"))
  .settings(scalapbSettings)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `helloscala-impl` = (project in file("helloscala-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslClient,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`helloscala-api`)


// TODO: genreate grpc for java in any platform. See https://gitter.im/trueaccord/ScalaPB?at=59230464fa63ba2f766aba1b
val scalapbSettings = {
  Seq(
    PB.targets in Compile := Seq(
      PB.gens.java -> (sourceManaged in Compile).value,
      scalapb.gen(
        javaConversions = true,
        flatPackage = false,
        grpc = true,
        singleLineToString = true
      ) -> (sourceManaged in Compile).value
    ),
    libraryDependencies ++= Seq(
      "com.trueaccord.scalapb" %% "scalapb-runtime" % scalapbVersion,
      "com.trueaccord.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf",
      "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % scalapbVersion,
      "io.grpc" % "grpc-netty" % "1.2.0"
    )
  )
}
