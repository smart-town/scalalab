lazy val root = (project in file("."))
	.settings(
		name := "learn-scala",
		version := "1.0",
		scalaVersion := "3.4.0",
	)
enablePlugins(PackPlugin)
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
val http4sVersion = "1.0.0-M40"
libraryDependencies ++= Seq(
	"org.http4s" %% "http4s-ember-client" % http4sVersion,
	"org.http4s" %% "http4s-ember-server" % http4sVersion,
	"org.http4s" %% "http4s-dsl"          % http4sVersion,
	
)

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"