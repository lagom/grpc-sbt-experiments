// protoc related plugin and dependencies must be loaded before the Lagom plugin.
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.8")
libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.5.47"

addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.4.0-SNAPSHOT")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
