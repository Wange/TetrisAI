package ai.genetic

object Parameters {
	// Population parameters
	val generationSize = 1000
	val nrOfGenes = 25
	val numberOfGenerations = 200
	
	// Probabilities
	val mProb = 1/25
	val crossProb = 0.8
	
	// Selection
	val tournamentSize = 2
	val tournamentSelectionParameter = 0.75
	val elitism = 2
}