name := "funcy"

organization := "funcy"

version := "0.3"

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

scalaVersion := "2.11.3"

crossScalaVersions := Seq("2.10.4", "2.11.3")

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-test" % "2.3.5",
  "org.jsoup" % "jsoup" % "1.8.1"
)
   
scalacOptions ++= Seq("-unchecked", "-deprecation", "-language:_")