name := "basic-sample"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/",
  Resolver.url("Edulify Repository", url("http://edulify.github.io/modules/releases/"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  javaCore,
  "funcy"     %% "funcy" % "0.3"   % "test",
  "org.jsoup" %  "jsoup" % "1.8.1" % "test"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-q")