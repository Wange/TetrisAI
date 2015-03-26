package util

object Util {

	def time[R](op: => R): (R,Long) = {
		val t0 = System.nanoTime
		val result = op
		val t1 = System.nanoTime
		val time = t1 - t0
		(result, time)
	}
	
	implicit def autoMakeIfTrue(b: => Boolean) = new MakeIfTrue(b)
}

class IfTrue[A](b: => Boolean, t: => A) { def |(f: => A) = if (b) t else f }
class MakeIfTrue(b: => Boolean) { def ?[A](t: => A) = new IfTrue[A](b,t) }
