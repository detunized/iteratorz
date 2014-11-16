package net.detunized.iteratorz

class MergingIteratorSpec extends IteratorSpec[Int] {
  "return first sequence when second is empty" in {
    check(mk(Seq(1, 2, 3), Seq.empty[Int]), 1, 2, 3)
  }

  "return second sequence when first is empty" in {
    check(mk(Seq.empty[Int], Seq(1, 2, 3)), 1, 2, 3)
  }

  "return first sequence then second" in {
    check(mk(Seq(1, 1, 1), Seq(2, 2, 2)), 1, 1, 1, 2, 2, 2)
  }

  "return second sequence then first" in {
    check(mk(Seq(2, 2, 2), Seq(1, 1, 1)), 1, 1, 1, 2, 2, 2)
  }

  protected[this] def mkEmpty = mk(Seq.empty[T], Seq.empty[T])
  private[this] def mk(a: Seq[T], b: Seq[T]) = new MergingIterator(a.iterator, b.iterator)
}
