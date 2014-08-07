package net.detunized.iteratorz

import org.specs2.mutable.Specification

class ZippingIteratorSpec extends Specification {
  "ZippingIterator" should {
    "have no next on empty iterators" in {
      checkTrue(Seq.empty, Seq.empty, Seq.empty)
      checkFalse(Seq.empty, Seq.empty, Seq.empty)
    }

    "return first sequence when second is empty" in {
      val a = Seq(1, 2, 3)
      val b = Seq.empty
      val e = Seq(1, 2, 3)

      checkTrue(a, b, e)
      checkFalse(a, b, e)
    }

    "return second sequence when first is empty" in {
      val a = Seq.empty
      val b = Seq(1, 2, 3)
      val e = Seq(1, 2, 3)

      checkTrue(a, b, e)
      checkFalse(a, b, e)
    }

    "return alternating sequence" in {
      val a = Seq(1, 3, 5)
      val b = Seq(2, 4, 6)

      val x = new ZippingIterator(a.iterator, b.iterator)((_, _) => true)
      println(x.toVector)

      checkTrue(a, b, Seq(1, 2, 3, 4, 5, 6))
      checkFalse(a, b, Seq(2, 1, 4, 3, 6, 5))
    }

    "append first sequence tail" in {
      val a = Seq(1, 3, 5, 6)
      val b = Seq(2, 4)

      checkTrue(a, b, Seq(1, 2, 3, 4, 5, 6))
      checkFalse(a, b, Seq(2, 1, 4, 3, 5, 6))
    }

    "append second sequence tail" in {
      val a = Seq(1, 3)
      val b = Seq(2, 4, 5, 6)

      checkTrue(a, b, Seq(1, 2, 3, 4, 5, 6))
      checkFalse(a, b, Seq(2, 1, 4, 3, 5, 6))
    }
  }

  private def check[A](a: Seq[A], b: Seq[A], expected: Seq[A])(f: (A, A) => Boolean) = {
    val i = new ZippingIterator(a.iterator, b.iterator)(f)

    expected foreach { x =>
      i.hasNext should beTrue
      i.next() shouldEqual x
    }

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }

  private def checkTrue[A](a: Seq[A], b: Seq[A], expected: Seq[A]) =
    check(a, b, expected)((_, _) => true)

  private def checkFalse[A](a: Seq[A], b: Seq[A], expected: Seq[A]) =
    check(a, b, expected)((_, _) => false)
}
