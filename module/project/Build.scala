import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "funcy"
    val appVersion      = "0.1"

    val appDependencies = Seq(
      "play" % "play-test_2.9.1" % "2.0.1", 
      "org.jsoup" % "jsoup" % "1.6.2"  
    )

    val main = PlayProject(appName, appVersion, appDependencies).settings(
    )

}
