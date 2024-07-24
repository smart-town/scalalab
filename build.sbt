lazy val root = (project in file("."))
	.settings(
		name := "learn-scala",
		version := "1.0",
		scalaVersion := "3.4.0",
	)
enablePlugins(PackPlugin)


val http4sVersion = "1.0.0-M40"
val AkkaVersion = "2.9.4"
resolvers += "Akka library repository".at("https://repo.akka.io/maven")

libraryDependencies ++= Seq(
	"org.typelevel" %% "cats-effect" % "3.5.4",
	"org.http4s" %% "http4s-ember-client" % http4sVersion,
	"org.http4s" %% "http4s-ember-server" % http4sVersion,
	"org.http4s" %% "http4s-dsl"          % http4sVersion,

	"com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
	"com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,

	"com.typesafe.akka" %% "akka-stream" % AkkaVersion,
	"com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,

	"org.scala-lang.modules" %% "scala-swing" % "3.0.0"
)
