package net.detunized.iteratorz

import org.specs2.mutable.Specification

class PackingIteratorSpec extends Specification {
  "have no next on an empty iterator" in {
    val i = mk(Seq[Int]())

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }

  "pack single elements" in {
    check(Seq("A"), Seq(("A", 1)))
    check(Seq("A", "B"), Seq(("A", 1), ("B", 1)))
    check(Seq("A", "B", "C"), Seq(("A", 1), ("B", 1), ("C", 1)))
  }

  "pack repeating elements" in {
    check(Seq("A", "A"), Seq(("A", 2)))
    check(Seq("A", "A", "A"), Seq(("A", 3)))
  }

  "pack mixed single and repeated elements" in {
    check(Seq("A", "A", "B"), Seq(("A", 2), ("B", 1)))
    check(Seq("A", "B", "B"), Seq(("A", 1), ("B", 2)))
    check(Seq("A", "B", "B", "C", "C", "C"), Seq(("A", 1), ("B", 2), ("C", 3)))
    check(Seq("A", "A", "A", "B", "B", "C"), Seq(("A", 3), ("B", 2), ("C", 1)))
    check(Seq("A", "A", "B", "C", "C", "C"), Seq(("A", 2), ("B", 1), ("C", 3)))
  }

  private def mk[A](s: Seq[A]) = new PackingIterator(s.iterator)
  private def check[A](s: Seq[A], expected: Seq[A]) = {
    val i = mk(s)

    expected foreach { x =>
      i.hasNext should beTrue
      i.next() shouldEqual x
    }

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }
}
