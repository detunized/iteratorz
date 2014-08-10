package net.detunized.iteratorz

import org.specs2.mutable.Specification

class ShufflingIteratorSpec extends Specification {
  "have no next on an empty iterator" in {
    checkEmpty(1)
  }

  "throw on zero window size" in {
    mkEmpty(0) should throwA[IllegalArgumentException]
  }

  "throw on negative window size" in {
    mkEmpty(-1) should throwA[IllegalArgumentException]
  }

  "should return the same sequence with window of 1" in {
    mk(Seq(1, 2, 3), 1).toList shouldEqual List(1, 2, 3)
  }

  // TODO: Figure out how to test shuffling in a deterministic way!
  //       Maybe use a fake random generator.

  private def mk[A](s: Seq[A], windowSize: Int) = new ShufflingIterator(s.iterator, windowSize)
  private def mkEmpty[A](windowSize: Int) = mk(Seq[Int](), windowSize)

  private def checkEmpty(windowSize: Int) = {
    val i = mkEmpty(windowSize)

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }
}
