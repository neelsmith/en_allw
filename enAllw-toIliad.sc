import scala.io.Source
val scholToIl = Source.fromFile("/Users/melodywauke/Desktop/scholToIl.tsv").getLines.toVector.map(_.split("\t"))
val scholia = Source.fromFile("scholia.tsv").getLines.toVector.map(_.split("\t"))
val scholUrns = scholia.map(_(0).dropRight(8))
val scholText = scholia.map(_(1))
val iliadText = Source.fromFile("data/diplomaticIliadLines.cex").getLines.toVector.map(_.split("#"))
val editedDiplo = iliadText.map(c => Array(c(0).replaceAll("_diplomatic",""),c(1)))


def matchLines (urn: String, index: Vector[Array[String]])  = {
index.filter(_(0) == urn)
}

val matchedUrns = scholUrns.map(matchLines(_,scholToIl)).flatten
val iliadUrns = matchedUrns.map(_(1))



val matchedIlText = iliadUrns.map(matchLines(_,editedDiplo)).flatten
val enAllwIlText = matchedIlText.map(_(1))

val regEx = ".*ν .λλ.".r

val stripPuncSchol = scholText.map(_.replaceAll("[.,⁑··‡:⁚a-zA-Z0-9]", ""))
val stripRegEx =  stripPuncSchol.map(regEx.replaceAllIn(_, ""))

val zip1 = scholUrns zip stripRegEx
val zip2 = zip1 zip iliadUrns
val zip3 = zip2 zip enAllwIlText

for (z <- zip3) {

  println(z._1._1._1 + "\t" + z._1._1._2 + "\t" + z._1._2 + "\t" + z._2 )
}
