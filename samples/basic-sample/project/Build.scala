import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "basic-sample"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "funcy" % "funcy_2.9.1" % "0.1" % "test",
      "org.jsoup" % "jsoup" % "1.6.2" % "test"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
