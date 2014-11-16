package net.detunized.iteratorz

class MergingIteratorSpec extends IteratorSpec[Int] {
  "return first sequence when second is empty" in {
    mk(1, 2, 3)() must expandTo(1, 2, 3)
  }

  "return second sequence when first is empty" in {
    mk()(1, 2, 3) must expandTo(1, 2, 3)
  }

  "return first sequence then second" in {
    mk(1, 1, 1)(2, 2, 2) must expandTo(1, 1, 1, 2, 2, 2)
  }

  "return second sequence then first" in {
    mk(2, 2, 2)(1, 1, 1) must expandTo(1, 1, 1, 2, 2, 2)
  }

  protected[this] def mkEmpty = mk()()

  private[this] def mk(s1: T*)(s2: T*) =
    new MergingIterator(s1.iterator, s2.iterator)
}
