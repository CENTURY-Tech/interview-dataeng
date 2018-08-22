// sbt-scalariform
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

// Dependencies
val akkaVersion = "2.5.13"
val akkaHttpVersion = "10.1.2"
val specs2Version = "4.2.0"
val testDependencies = Seq (
  "com.typesafe.akka"          %% "akka-testkit"                   % akkaVersion     % "test,it",
  "com.typesafe.akka"          %% "akka-http-testkit"              % akkaHttpVersion % "test,it",
  "org.scalacheck"             %% "scalacheck"                     % "1.14.0"        % "test,it",
  "org.specs2"                 %% "specs2-core"                    % specs2Version   % "test,it",
  "org.specs2"                 %% "specs2-mock"                    % specs2Version   % "test,it",
  "org.specs2"                 %% "specs2-matcher-extra"           % specs2Version   % "test,it",
  "io.ctek.common"             %% "common-scala-test"              % "0.2.1"         % "test,it"
)

val rootDependencies = Seq(
  "ch.qos.logback"             %  "logback-classic"              % "1.2.3",
  "net.logstash.logback"       %  "logstash-logback-encoder"     % "4.11",
  "com.typesafe"               %  "config"                       % "1.3.3",
  "com.github.nscala-time"     %% "nscala-time"                  % "2.20.0",
  "com.typesafe.scala-logging" %% "scala-logging"                % "3.9.0",
  "com.typesafe.akka"          %% "akka-actor"                   % akkaVersion,
  "com.typesafe.akka"          %% "akka-slf4j"                   % akkaVersion,
  "com.typesafe.akka"          %% "akka-http"                    % akkaHttpVersion,
  "de.heikoseeberger"          %% "akka-http-argonaut"           % "1.21.0",
  "io.argonaut"                %% "argonaut"                     % "6.2.1",
  "com.iheart"                 %% "ficus"                        % "1.4.3",
  "commons-daemon"             %  "commons-daemon"               % "1.1.0",
  "com.datastax.cassandra"     %  "cassandra-driver-core"        % "3.5.0",
  "com.datastax.cassandra"     %  "cassandra-driver-mapping"     % "3.5.0",
  "org.mongodb.scala"          %% "mongo-scala-bson"             % "2.3.0",
  "org.typelevel"              %% "cats"                         % "0.9.0",
  "org.scalanlp"               %% "breeze"                       % "0.13.2",
  "org.scalanlp"               %% "breeze-natives"               % "0.13.2",
  "com.roundeights"            %% "hasher"                       % "1.2.0",
  "io.ctek.services"           %% "analytics-rlp"                % "0.2.23"
)

val dependencies =
  rootDependencies ++
  testDependencies

// Settings
//
val buildSettings = Seq(
  name := "interview-dataeng",
  organization := "io.ctek.interview",
  scalaVersion := "2.12.6",
  scalaBinaryVersion := "2.12"
)

val forkedJvmOption = Seq(
  "-server",
  "-Dfile.encoding=UTF8",
  "-Duser.timezone=GMT",
  "-Xss1m",
  "-Xms1048m",
  "-Xmx1048m",
  "-XX:+CMSClassUnloadingEnabled",
  "-XX:+DoEscapeAnalysis",
  "-XX:+UseConcMarkSweepGC",
  "-XX:+UseParNewGC",
  "-XX:+UseCodeCacheFlushing",
  "-XX:+UseCompressedOops"
)

val compileSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:_",
    "-unchecked",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Ywarn-unused-import",
    "-Ypartial-unification"
  )
)

val formatting =
  FormattingPreferences()
    .setPreference(AlignParameters, false)
    .setPreference(AlignSingleLineCaseStatements, false)
    .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 40)
    .setPreference(CompactControlReadability, false)
    .setPreference(CompactStringConcatenation, false)
    .setPreference(DanglingCloseParenthesis, Force)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(FormatXml, true)
    .setPreference(IndentLocalDefs, false)
    .setPreference(IndentPackageBlocks, true)
    .setPreference(IndentSpaces, 2)
    .setPreference(IndentWithTabs, false)
    .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
    .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
    .setPreference(PreserveSpaceBeforeArguments, false)
    .setPreference(RewriteArrowSymbols, false)
    .setPreference(SingleCasePatternOnNewline, false)
    .setPreference(SpaceBeforeColon, false)
    .setPreference(SpaceInsideBrackets, false)
    .setPreference(SpaceInsideParentheses, false)
    .setPreference(SpacesAroundMultiImports, false)
    .setPreference(SpacesWithinPatternBinders, true)

// release settings
val mainProjectRef = LocalProject("main")

val pluginsSettings =
  compileSettings ++
  buildSettings ++
  scalariformItSettings

val settings = Seq(
  libraryDependencies ++= dependencies,
  fork in run := true,
  fork in Test := true,
  fork in testOnly := true,
  connectInput in run := true,
  javaOptions in run ++= forkedJvmOption,
  javaOptions in Test ++= forkedJvmOption,
  mainClass in Compile := Option("io.ctek.interview.dataeng.AppRunner"),
  mainClass in run := Option("io.ctek.interview.dataeng.AppRunner"),
  // formatting
  //
  ScalariformKeys.preferences := formatting,
  // this makes provided dependencies work when running and uses the correct main class
  //
  run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (run), runner in (Compile, run)).evaluated
)

lazy val main =
  project
    .in(file("."))
    .configs(IntegrationTest)
    .settings(
      pluginsSettings ++
      Defaults.itSettings ++
      settings:_*
    )
    .settings(
      compile in Compile := (compile in Compile).value
    )
