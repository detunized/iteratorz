name := "iteratorz"

version := "0.1"

organization := "net.detunized"

scalaVersion := "2.11.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.3.13" % "test"
)

resolvers += Resolver.sonatypeRepo("public")

testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "console")
