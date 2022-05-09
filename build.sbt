name := "l13hw"

version := "0.1"

scalaVersion := "2.12.15"
lazy val sparkVersion = "3.2.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql"   % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion
)

libraryDependencies += "com.johnsnowlabs.nlp" %% "spark-nlp" % "3.4.3" % Test
libraryDependencies += "org.jsoup" % "jsoup" % "1.14.3"
libraryDependencies += "com.typesafe" % "config" % "1.4.2"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.8.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)