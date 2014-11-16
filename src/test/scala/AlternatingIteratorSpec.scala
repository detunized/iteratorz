package net.detunized.iteratorz

class AlternatingIteratorSpec extends IteratorSpec[Int] {
  "return first sequence when second is empty" in {
    mk(1, 2, 3)() must expandTo(1, 2, 3)
  }

  "return second sequence when first is empty" in {
    mk()(1, 2, 3) must expandTo(1, 2, 3)
  }

  "return alternating sequence" in {
    mk(1, 3, 5)(2, 4, 6) must expandTo(1, 2, 3, 4, 5, 6)
  }

  "append first sequence tail" in {
    mk(1, 3, 5, 6)(2, 4) must expandTo(1, 2, 3, 4, 5, 6)
  }

  "append second sequence tail" in {
    mk(1, 3)(2, 4, 5, 6) must expandTo(1, 2, 3, 4, 5, 6)
  }

  protected[this] def mkEmpty = mk()()

  private[this] def mk(s1: T*)(s2: T*) =
    new AlternatingIterator(s1.iterator, s2.iterator)
}
