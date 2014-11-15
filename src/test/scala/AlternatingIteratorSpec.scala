package net.detunized.iteratorz

class AlternatingIteratorSpec extends IteratorSpec[Int, AlternatingIterator[Int]] {
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

  private[this] def mk(a: Seq[T], b: Seq[T]) = new AlternatingIterator(a.iterator, b.iterator)
  private[this] def check(a: Seq[T], b: Seq[T], expected: Seq[T]) = super.check(mk(a, b), expected)

  protected def mkEmpty = mk(Seq.empty[T], Seq.empty[T])
}
