

scalaVersion := "2.11.8"


resolvers += Resolver.jcenterRepo
resolvers += "beta" at "http://beta.hpcc.uh.edu/nexus/content/repositories/releases"
resolvers += Resolver.bintrayRepo("neelsmith", "maven")


libraryDependencies ++= Seq(

  "edu.holycross.shot.cite" %% "xcite" % "2.6.0",
  "edu.holycross.shot" %% "scm" % "4.1.1",
  "edu.holycross.shot" %% "ohco2" % "9.1.0",
  "edu.holycross.shot" %% "citeobj" % "3.1.3",
  "edu.holycross.shot" %% "citerelations" % "1.1.1",
  "edu.holycross.shot" %% "citeiip" % "1.0.0",
  "org.homermultitext" %% "hmt-textmodel" % "1.3.0"

)
