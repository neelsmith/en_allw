import java.text.Normalizer
import scala.io.Source

val index = Source.fromFile("enAllw-toIliad.tsv").getLines.toVector.drop(1)
val indexColumns = index.map(_.split("\t")).filterNot(_(2).contains("blank"))
val noPuncEnAllw = indexColumns.map(r => (r(0),r(1),r(2).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
val normalizedEnAllw = noPuncEnAllw.map(c => (c._1,c._2,Normalizer.normalize(c._3, Normalizer.Form.NFC)))
val enAllwWords = normalizedEnAllw.map(r => (r._1,r._2,r._3.split("[ ]+")))

val oct = Source.fromFile("data/iliad-oct.tsv").getLines.toVector
val noPuncOct = oct.map(_.split("\t")).map(c => (c(0),c(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
val normalizedOct = noPuncOct.map(c => (c._1,Normalizer.normalize(c._2, Normalizer.Form.NFC)))
val octWords = normalizedOct.map(l => (l._1,l._2.split("[ ]+")))

enAllwWords.map(c => difference(c._3,octWords))

def difference (enAllwLine: Array[String], octText: Vector[(String, Array[String])]) = {

val enAllwVec = enAllwLine.toVector
val octVec = octText.map(_._2.toVector)
octVec.map(lineComparison(enAllwVec,_))

}

def lineComparison (origLine: Vector[String], regLine: Vector[String]) = {
  (origLine intersect regLine)
}
