
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.4.0-SNAPSHOT")

// using this exact two lines, the order of sbt plugin and library dependency declarations becomes irrelevant.
// See https://github.com/scalapb/ScalaPB/issues/150#issuecomment-313403726
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.11" exclude ("com.trueaccord.scalapb", "protoc-bridge_2.10"))
libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin-shaded" % "0.6.0"


