// sbt-scalariform
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

// Dependencies
val specs2Version = "4.3.3"
val testDependencies = Seq (
  "org.scalacheck"             %% "scalacheck"                     % "1.14.0"        % "test,it",
  "org.specs2"                 %% "specs2-core"                    % specs2Version   % "test,it",
  "org.specs2"                 %% "specs2-mock"                    % specs2Version   % "test,it",
  "org.specs2"                 %% "specs2-matcher-extra"           % specs2Version   % "test,it"
)

val rootDependencies = Seq(
  "org.typelevel"              %% "cats-core"                      % "1.2.0"
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
