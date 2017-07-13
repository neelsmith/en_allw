import java.text.Normalizer
import scala.io.Source
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.orca._
import edu.holycross.shot.greek._
import edu.holycross.shot.gsphone._
import org.homermultitext.edmodel._

val index = Source.fromFile("enAllw-toIliad.tsv").getLines.toVector.drop(1)
val indexColumns = index.map(_.split("\t")).filterNot(_(2).contains("blank"))
val noPuncEnAllw = indexColumns.map(r => (r(0),r(1),r(2).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
val normalizedEnAllw = noPuncEnAllw.map(c => (c._1,c._2,Normalizer.normalize(c._3, Normalizer.Form.NFC)))
val enAllwWords = normalizedEnAllw.map(r => (r._1,r._2,r._3.split("[ ]+")))

val oct = Source.fromFile("data/iliad-oct.tsv").getLines.toVector
val noPuncOct = oct.map(_.split("\t")).map(c => (c(0),c(1).replaceAll( "[\\{\\}\\\\>,\\[\\]\\.·⁑;:·\\*\\(\\)\\+\\=\\-“”\"‡  ]+"," ")))
val normalizedOct = noPuncOct.map(c => (c._1,Normalizer.normalize(c._2, Normalizer.Form.NFC)))
val octWords = normalizedOct.map(l => (l._1,l._2.split("[ ]+")))

val fuzzyMatch = enAllwWords.map(c => (c._1,c._2,c._3.mkString(" "),intersection(c._3,octWords)))

val noDuplicates = for (m <- fuzzyMatch) yield {

  val schol = m._1
  val urn = m._2
  val text = m._3
  val replaceUrn = urn.replaceAll(".msA","")
  val ctsUrn = CtsUrn(replaceUrn)
  val passages = m._4
  val elimOrig = passages.map(c => (CtsUrn(c._1),c._2)).filterNot(_._1 ~~ exUrn)
  Vector((schol,urn,text)) zip Vector(elimOrig.map(c => (c._1.toString,c._2)))
}

for (e <- noDuplicates) {
    val enAllwSchol = e(0)._1._1
     val origUrn = e(0)._1._2
       val origText = e(0)._1._3
       val regVec = e(0)._2
       regVec.map(c => print(enAllwSchol,origUrn,origText,c))
       println("---\t---\t---\t---\t---")
     }

def print (scholUrn: String, originalUrn: String, originalText: String, regVector: (String,Vector[String])) = {

  println(scholUrn + "\t" + originalUrn + "\t" + originalText + "\t" + regVector._2.mkString(", ") + "\t" + regVector._1)

}

def intersection (enAllwLine: Array[String], octText: Vector[(String, Array[String])]) = {

val enAllwVec = enAllwLine.toVector
val octUrns = octText.map(_._1)
val octVec = octText.map(_._2.toVector)
val intersected = octVec.map(lineComparison(enAllwVec,_))
val interTuple = octUrns zip intersected
val largeOverlap = interTuple.filter(_._2.size > 2)
largeOverlap

}

def lineComparison (origLine: Vector[String], regLine: Vector[String]) = {
  (origLine intersect regLine)
}
