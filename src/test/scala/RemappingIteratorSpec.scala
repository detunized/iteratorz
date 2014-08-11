package net.detunized.iteratorz

import org.specs2.mutable.Specification

class RemappingIteratorSpec extends Specification {
  "have no next on an empty iterator" in {
    val i = mkEmpty("?", "?")

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }

  "throw on blank from parameter" in {
    mkEmpty("", "?") should throwA[IllegalArgumentException]
  }

  // TODO: Test more failures!

  "return the same sequence with identity mapping" in {
    checkSame(Seq(1, 2, 3), "A", "A")
    checkSame(Seq(1, 2, 3, 4), "AB", "AB")
    checkSame(Seq(1, 2, 3, 4, 5, 6), "ABC", "ABC")
  }

  "swap elements" in {
    check(Seq(1, 2, 3, 4), "AB", "BA", Seq(2, 1, 4, 3))
    check(Seq(1, 2, 3), "ABC", "BCA", Seq(2, 3, 1))
    check(Seq(1, 2, 3, 4), "ABCD", "DCBA", Seq(4, 3, 2, 1))
  }

  "remove elements" in {
    check(Seq(1, 2, 3, 4), "AB", "A", Seq(1, 3))
    check(Seq(1, 2, 3, 4), "AB", "B", Seq(2, 4))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "A", Seq(1, 4))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "B", Seq(2, 5))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "C", Seq(3, 6))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "BC", Seq(2, 3, 5, 6))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "AC", Seq(1, 3, 4, 6))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "BC", Seq(2, 3, 5, 6))
  }

  "duplicate elements" in {
    check(Seq(1, 2, 3), "A", "AA", Seq(1, 1, 2, 2, 3, 3))
    check(Seq(1, 2, 3), "A", "AAA", Seq(1, 1, 1, 2, 2, 2, 3, 3, 3))
    check(Seq(1, 2, 3, 4), "AB", "AABB", Seq(1, 1, 2, 2, 3, 3, 4, 4))
    check(Seq(1, 2, 3, 4), "AB", "AAABBB", Seq(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "AABC", Seq(1, 1, 2, 3, 4, 4, 5, 6))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "ABBC", Seq(1, 2, 2, 3, 4, 5, 5, 6))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "ABCC", Seq(1, 2, 3, 3, 4, 5, 6, 6))
  }

  "swap, remove and duplicate" in {
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "CBB", Seq(3, 2, 2, 6, 5, 5))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "BAA", Seq(2, 1, 1, 5, 4, 4))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "CCA", Seq(3, 3, 1, 6, 6, 4))
    check(Seq(1, 2, 3, 4, 5, 6), "ABC", "CCCA", Seq(3, 3, 3, 1, 6, 6, 6, 4))
  }

  private def mk[A](s: Seq[A], from: String, to: String) = new RemappingIterator(s.iterator, from, to)
  private def mkEmpty[A](from: String, to: String) = mk(Seq[Int](), from, to)

  private def check[A](s: Seq[A], from: String, to: String, expected: Seq[A]) = {
    val i = mk(s, from, to)

    expected foreach { x =>
      i.hasNext should beTrue
      i.next() shouldEqual x
    }

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }

  private def checkSame[A](s: Seq[A], from: String, to: String) =
    check(s, from, to, s)
}
