package net.detunized.iteratorz

class ZippingIteratorSpec extends IteratorSpec[Int] {
  "return first sequence when second is empty" in {
    mkTrue(1, 2, 3)() must expandTo(1, 2, 3)
    mkFalse(1, 2, 3)() must expandTo(1, 2, 3)
  }

  "return second sequence when first is empty" in {
    mkTrue()(1, 2, 3) must expandTo(1, 2, 3)
    mkFalse()(1, 2, 3) must expandTo(1, 2, 3)
  }

  "return alternating sequence" in {
    mkTrue(1, 3, 5)(2, 4, 6) must expandTo(1, 2, 3, 4, 5, 6)
    mkFalse(1, 3, 5)(2, 4, 6) must expandTo(2, 1, 4, 3, 6, 5)
  }

  "append first sequence tail" in {
    mkTrue(1, 3, 5, 6)(2, 4) must expandTo(1, 2, 3, 4, 5, 6)
    mkFalse(1, 3, 5, 6)(2, 4) must expandTo(2, 1, 4, 3, 5, 6)
  }

  "append second sequence tail" in {
    mkTrue(1, 3)(2, 4, 5, 6) must expandTo(1, 2, 3, 4, 5, 6)
    mkFalse(1, 3)(2, 4, 5, 6) must expandTo(2, 1, 4, 3, 5, 6)
  }

  protected[this] def mkEmpty =
    mk()()((_, _) => false)

  private[this] def mk(s1: T*)(s2: T*)(f: (T, T) => Boolean) =
    new ZippingIterator(s1.iterator, s2.iterator)(f)

  private[this] def mkTrue(s1: T*)(s2: T*) =
    mk(s1:_*)(s2:_*)((_, _) => true)

  private[this] def mkFalse(s1: T*)(s2: T*) =
    mk(s1:_*)(s2:_*)((_, _) => false)
}
