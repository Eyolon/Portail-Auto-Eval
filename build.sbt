name := """portail-auto-eval"""

version := "0.2.0-SNAPSHOT"

scriptClasspath := Seq("*")

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

javacOptions += "-g"

libraryDependencies ++= Seq(
  javaJpa,
  javaJdbc,
  cache,
  javaWs,
  filters,
  "org.json" % "json" % "5.0.5",
  "io.janusproject.guava" % "guava" % "19.0.0",
  "log4j" % "log4j" % "1.2.17",
  "org.postgresql" % "postgresql" % "9.4.1208",
  "org.hibernate" % "hibernate-core" % "5.1.0.Final",
  "org.hibernate" % "hibernate-java8" % "5.1.0.Final",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "org.hibernate" % "hibernate-hikaricp" % "5.1.0.Final",
  "org.jdom" % "jdom" % "2.0.2",
  "net.sourceforge.jtds" % "jtds" % "1.3.1",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

herokuAppName in Compile := "portail-auto-eval" 

PlayKeys.externalizeResources := false
routesGenerator := StaticRoutesGenerator

EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes
