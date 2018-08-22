// Comment to get more information during initialization
logLevel := Level.Warn

// Resolvers
resolvers += Resolver.typesafeRepo("releases")

// Build Info
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")

// Releases
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")

// Scalariform
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")

// Scoverage
addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "1.5.1")

// Scalastyle
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// dependency
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

// dependency updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")

// adding javaagent
addSbtPlugin("com.lightbend.sbt" % "sbt-javaagent" % "0.1.4")

// benchmarks
addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.3.4")

// Docker
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")
