
import scala.io.Source


val cutOffInWords = 6
val regEx = ".*ν .λλ.".r

val tsvFile = "scholia.tsv"

val lines = Source.fromFile(tsvFile).getLines.toVector


val lineCounts = for (l <- lines) yield  {
  val columns = l.split("\t")
  val urn = columns(0)
  val text = columns(1).replaceAll("[.,⁑·:a-zA-Z0-9]", "")


  val stripped =  regEx.replaceAllIn(text, "")

  val strippedWords = stripped.split("[ ]+")
  strippedWords.size
  /*
  if (strippedWords.size <= cutOffInWords) {
     strippedWords.size + " words")
    stripped
  } else {
    ""
  }*/


  /*
  val words = text.split(" ")
  println (words.mkString("--") + "\n\n")
  */
}

//println(longLines.mkString("\n"))


val linesWithCounts = lines zip lineCounts

val shortScholia = linesWithCounts.filter( _._2 <=  cutOffInWords).map(_._1)


val longScholia = linesWithCounts.filter( _._2 >  cutOffInWords).map(_._1)
