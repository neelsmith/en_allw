
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.orca._
import edu.holycross.shot.greek._
import edu.holycross.shot.gsphone._
import org.homermultitext.edmodel._

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")


val orca = OrcaSource.fromFile("data/critsignorca.tsv")

// create a new, subset Corpus with URN twiddling, e.g.,
// val scholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msA:")
//
// analyze a corpus and create a sequence of TokenAnalysis objects:
//val tokens = TeiReader.fromCorpus(corpus)
