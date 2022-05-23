scalaVersion := "2.13.8" // Also supports 2.12.x and 3.x

val http4sVersion = "1.0.0-M32"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-Ypartial-unification")



lazy val root = (project in file("."))
  .settings(
    name := "ConnectedDevs",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion
    )
  )
