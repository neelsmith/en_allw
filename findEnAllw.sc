import scala.io.Source

val fileName = "scholia-byzortho.tsv"

val oNormal = Source.fromFile(fileName).getLines.toVector

val comments = oNormal.map(_.split("\t")).filter(_(0).contains("comment"))

val regEx = ".*ν .λλ. .*".r

val enAllwSchol = comments.filter(c => regEx.pattern.matcher(c(1)).matches)

println("COPY FROM BELOW!")

for (s <- enAllwSchol) {

println(s(0) + "\t" + s(1))

}
