name := "Cluster"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.0"