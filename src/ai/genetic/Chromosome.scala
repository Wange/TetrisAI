package ai.genetic

import scala.util._

import util.Util._

class Chromosome(val genes : Seq[Int] = Chromosome.initRandom) {
	
	 def mutate = new Chromosome(
			 genes.map { gene => (Random.nextDouble < 0.1) ? (Math.abs(gene-1)) | gene }
		)
}

object Chromosome {
	private def initRandom = Seq.fill(Parameters.nrOfGenes)(Random.nextInt(2))
}