// sbt-scalariform
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

// Dependencies
val specs2Version = "4.10.6"
val testDependencies = Seq (
  "org.specs2"                 %% "specs2-core"                    % specs2Version   % "test,it",
  "org.specs2"                 %% "specs2-mock"                    % specs2Version   % "test,it",
  "org.specs2"                 %% "specs2-matcher-extra"           % specs2Version   % "test,it"
)

val rootDependencies = Seq(
  "org.typelevel"              %% "cats-core"                      % "2.3.1",
  "org.typelevel"              %% "cats-effect"                    % "2.3.1"
)

val dependencies =
  rootDependencies ++
  testDependencies

// Settings
//
val buildSettings = Seq(
  name := "interview-dataeng",
  organization := "io.ctek.interview",
  scalaVersion := "2.13.4",
  scalaBinaryVersion := "2.13"
)

val forkedJvmOption = Seq(
  "-server",
  "-Dfile.encoding=UTF8",
  "-Duser.timezone=GMT",
  "-Xss1m",
  "-Xms1024m",
  "-Xmx1024m"
)

val compileSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:_",
    "-unchecked",
    "-Xlint",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Ywarn-unused:imports",
    "-language:higherKinds"
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
