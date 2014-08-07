package net.detunized.iteratorz

import org.specs2.mutable.Specification

class AlternatingIteratorSpec extends Specification {
  "AlternatingIterator" should {
    "have no next on empty iterators" in {
      check(Seq.empty, Seq.empty, Seq.empty)
    }

    "return first sequence when second is empty" in {
      check(Seq(1, 2, 3), Seq.empty, Seq(1, 2, 3))
    }

    "return second sequence when first is empty" in {
      check(Seq.empty, Seq(1, 2, 3), Seq(1, 2, 3))
    }

    "return alternating sequence" in {
      check(Seq(1, 3, 5), Seq(2, 4, 6), Seq(1, 2, 3, 4, 5, 6))
    }

    "append first sequence tail" in {
      check(Seq(1, 3, 5, 6), Seq(2, 4), Seq(1, 2, 3, 4, 5, 6))
    }

    "append second sequence tail" in {
      check(Seq(1, 3), Seq(2, 4, 5, 6), Seq(1, 2, 3, 4, 5, 6))
    }
  }

  private def check[A](a: Seq[A], b: Seq[A], expected: Seq[A]) = {
    val i = new AlternatingIterator(a.iterator, b.iterator)

    expected foreach { x =>
      i.hasNext should beTrue
      i.next() shouldEqual x
    }

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }
}
