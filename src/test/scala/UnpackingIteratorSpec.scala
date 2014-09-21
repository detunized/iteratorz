package net.detunized.iteratorz

import org.specs2.mutable.Specification

class UnpackingIteratorSpec extends Specification {
  "have no next on an empty iterator" in {
    val i = mk(Seq[(String, Int)]())

    i.hasNext must beFalse
    i.next() must throwA[NoSuchElementException]
  }

  "unpacks single element" in {
    check(Seq("A" -> 1), Seq("A"))
    check(Seq("A" -> 2), Seq("A", "A"))
    check(Seq("A" -> 3), Seq("A", "A", "A"))
  }

  "unpacks multiple elements" in {
    check(Seq("A" -> 1, "B" -> 2), Seq("A", "B", "B"))
    check(Seq("A" -> 2, "B" -> 3, "C" -> 4), Seq("A", "A", "B", "B", "B", "C", "C", "C", "C"))
  }

  "rejects zero counts" in {
    check(Seq("A" -> 0), Seq[String]())
    check(Seq("A" -> 0, "B" -> 0), Seq[String]())
    check(Seq("A" -> 0, "B" -> 0, "C" -> 0), Seq[String]())
  }

  "rejects negative counts" in {
    check(Seq("A" -> -1), Seq[String]())
    check(Seq("A" -> -1, "B" -> -2), Seq[String]())
    check(Seq("A" -> -1, "B" -> -2, "C" -> -3), Seq[String]())
  }

  "unpacks mixed sequence" in {
    check(Seq("A" -> 1, "B" -> 0, "C" -> 2, "D" -> -1), Seq("A", "C", "C"))
    check(Seq("A" -> 0, "B" -> 2, "C" -> 2, "D" -> -1), Seq("B", "B", "C", "C"))
    check(Seq("A" -> 2, "B" -> -1, "C" -> 0, "D" -> 1), Seq("A", "A", "D"))
  }

  private def mk[A](s: Seq[(A, Int)]) = new UnpackingIterator(s.iterator)
  private def check[A](s: Seq[(A, Int)], expected: Seq[A]) = {
    val i = mk(s)

    expected foreach { x =>
      i.hasNext must beTrue
      i.next() mustEqual x
    }

    i.hasNext must beFalse
    i.next() must throwA[NoSuchElementException]
  }
}
