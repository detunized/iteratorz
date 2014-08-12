package net.detunized.iteratorz

import org.specs2.mutable.Specification

class RepeatingIteratorSpec extends Specification {
  "is empty on an empty iterator" in {
    checkIsEmpty(mk(Seq[Int]())(x => 1))
  }

  "is empty with zero repeat count" in {
    checkIsEmpty(mk(Seq(1, 2, 3))(x => 0))
  }

  "hasNext throws on negative count" in {
    mk(Seq(1, 2, 3))(x => -1).hasNext should throwA[IllegalArgumentException]
  }

  "next throws on negative count" in {
    mk(Seq(1, 2, 3))(x => -1).next() should throwA[IllegalArgumentException]
  }

  "returns the same sequence with repeat count of 1" in {
    val s = Seq(1, 2, 3)
    mk(s)(x => 1).toList shouldEqual s
  }

  "repeats elements twice" in {
    val s = Seq(1, 2, 3)
    mk(s)(x => 2).toList shouldEqual Seq(1, 1, 2, 2, 3, 3)
  }

  "repeats elements variying number of times" in {
    val s = Seq(1, 2, 3, 4)
    mk(s)(x => x).toList shouldEqual Seq(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
  }

  "skips elements in the beginning" in {
    val s = Seq(1, 2, 3, 4, 5)
    mk(s)(x => if (x == 1 || x == 2) 0 else 1).toList shouldEqual Seq(3, 4, 5)
  }

  "skips elements in the middle" in {
    val s = Seq(1, 2, 3, 4, 5)
    mk(s)(x => if (x == 2 || x == 4) 0 else 1).toList shouldEqual Seq(1, 3, 5)
  }

  "skips elements in the end" in {
    val s = Seq(1, 2, 3, 4, 5)
    mk(s)(x => if (x == 4 || x == 5) 0 else 1).toList shouldEqual Seq(1, 2, 3)
  }

  "skips and repeats elements" in {
    val s = Seq(1, 2, 3, 4, 5)
    val m = Map(1 -> 0, 2 -> 3, 3 -> 1, 4 -> 0, 5 -> 2)
    mk(s)(m(_)).toList shouldEqual Seq(2, 2, 2, 3, 5, 5)
  }

  private def mk[A](s: Seq[A])(f: A => Int) = new RepeatingIterator(s.iterator)(f)

  private def checkIsEmpty[A](i: RepeatingIterator[A]) = {
    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }
}
