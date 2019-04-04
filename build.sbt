ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.12.8"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.8"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1"

lazy val root = (project in file("."))
  .settings(
    name := "dynamic-expr-compilation"
  )