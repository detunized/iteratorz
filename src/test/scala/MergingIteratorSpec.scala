package net.detunized.iteratorz

import org.specs2.mutable.Specification

class MergingIteratorSpec extends Specification {
  "MergingIterator" should {
    "have no next on empty iterators" in {
      check[Int](Seq.empty, Seq.empty)
    }

    "return first sequence when second is empty" in {
      check(Seq(1, 2, 3), Seq.empty)
    }

    "return second sequence when first is empty" in {
      check(Seq.empty, Seq(1, 2, 3))
    }

    "return first sequence then second" in {
      check(Seq(1, 1, 1), Seq(2, 2, 2))
    }

    "return second sequence then first" in {
      check(Seq(2, 2, 2), Seq(1, 1, 1))
    }
  }

  private def check[A](a: Seq[A], b: Seq[A])(implicit ord: Ordering[A]) = {
    val i = new MergingIterator(a.iterator, b.iterator)

    (a ++ b).sorted foreach { x =>
      i.hasNext should beTrue
      i.next() shouldEqual x
    }

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }
}
