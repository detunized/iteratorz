package net.detunized.iteratorz

class AlternatingIteratorSpec extends IteratorSpec[Int, AlternatingIterator[Int]] {
  "return first sequence when second is empty" in {
    check(mk(Seq(1, 2, 3), Seq.empty), 1, 2, 3)
  }

  "return second sequence when first is empty" in {
    check(mk(Seq.empty, Seq(1, 2, 3)), 1, 2, 3)
  }

  "return alternating sequence" in {
    check(mk(Seq(1, 3, 5), Seq(2, 4, 6)), 1, 2, 3, 4, 5, 6)
  }

  "append first sequence tail" in {
    check(mk(Seq(1, 3, 5, 6), Seq(2, 4)), 1, 2, 3, 4, 5, 6)
  }

  "append second sequence tail" in {
    check(mk(Seq(1, 3), Seq(2, 4, 5, 6)), 1, 2, 3, 4, 5, 6)
  }

  protected def mkEmpty = mk(Seq.empty[T], Seq.empty[T])
  private[this] def mk(a: Seq[T], b: Seq[T]) = new AlternatingIterator(a.iterator, b.iterator)
}
