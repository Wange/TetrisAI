package test

import scala.util._

import util.Util._
import ai.genetic._

object Main {

	def main(args : Array[String]) : Unit = {
		val imut = Seq.fill(1000)(Random.nextInt(2))
		val mut  = scala.collection.mutable.Seq.fill(1000)(Random.nextInt(2))
		val chromosome = new Chromosome
		
		Random.setSeed(1)
		
		val (_, iTime) = time { Seq.range(0, 10000).foreach(_ => mutI(imut)) }
		val (_, mTime) = time { Seq.range(0, 10000).foreach(_ => mutM(mut))  }
		val (_, cTime) = time { Seq.range(0, 10000).foreach(_ => chromosome.mutate) }
		
		println("Took " + iTime/1000000 + " millis for imutable")
		println("Took " + mTime/1000000 + " millis for mutable" )
		println("Took " + cTime/1000000 + " millis for chromosome" )
	}

	def mutI(s : Seq[Int]): Seq[Int] = {
		s.map { g => (Random.nextDouble < 0.1) ? (Math.abs(g-1)) | g}
	}
	
	def mutM(s : scala.collection.mutable.Seq[Int]): scala.collection.mutable.Seq[Int] = {
		var i = 0
		while(i < s.length) {
			if(Random.nextDouble < 0.1) {
				s(i) = Math.abs(s(i)-1)
			}
			i = i + 1
		}
		s
	}
}