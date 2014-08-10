package net.detunized.iteratorz

import scala.collection.mutable
import scala.util.Random

object util {
  def shuffleInplace[A](s: mutable.IndexedSeq[A], rng: Random): Unit = {
    def swap(i: Int, j: Int): Unit = {
      val tmp = s(i)
      s(i) = s(j)
      s(j) = tmp
    }

    var i = s.length
    while (i >= 2) {
      swap(i - 1, rng.nextInt(i))
      i -= 1
    }
  }
}
