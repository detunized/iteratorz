package net.detunized.iteratorz

class SkippingIteratorSpec extends IteratorSpec[Int] {
  "fails with zero 'take'" in {
    mk()(0, 1) must throwA[IllegalArgumentException]
    mk(1)(0, 1) must throwA[IllegalArgumentException]
    mk(1, 2)(0, 1) must throwA[IllegalArgumentException]
  }

  "fails with negative 'take'" in {
    mk()(-1, 1) must throwA[IllegalArgumentException]
    mk(1)(-1, 1) must throwA[IllegalArgumentException]
    mk(1, 2)(-1, 1) must throwA[IllegalArgumentException]
  }

  "fails with zero 'skip'" in {
    mk()(1, 0) must throwA[IllegalArgumentException]
    mk(1)(1, 0) must throwA[IllegalArgumentException]
    mk(1, 2)(1, 0) must throwA[IllegalArgumentException]
  }

  "fails with negative 'skip'" in {
    mk()(1, -1) must throwA[IllegalArgumentException]
    mk(1)(1, -1) must throwA[IllegalArgumentException]
    mk(1, 2)(1, -1) must throwA[IllegalArgumentException]
  }

  "takes all" in {
    mk(1)(1, 1) must expandTo(1)
    mk(1, 2)(2, 1) must expandTo(1, 2)
    mk(1, 2, 3)(3, 1) must expandTo(1, 2, 3)
  }

  "doesn't take too much" in {
    mk(1)(10, 1) must expandTo(1)
    mk(1, 2)(20, 1) must expandTo(1, 2)
    mk(1, 2, 3)(30, 1) must expandTo(1, 2, 3)
  }

  "skips to the end" in {
    mk(1, 2)(1, 1) must expandTo(1)
    mk(1, 2, 3)(1, 2) must expandTo(1)
    mk(1, 2, 3, 4)(1, 3) must expandTo(1)
  }

  "doesn't skip too much" in {
    mk(1, 2)(1, 10) must expandTo(1)
    mk(1, 2, 3)(1, 20) must expandTo(1)
    mk(1, 2, 3, 4)(1, 30) must expandTo(1)
  }

  "takes/skips every other" in {
    mk(1, 2)(1, 1) must expandTo(1)
    mk(1, 2, 3)(1, 1) must expandTo(1, 3)
    mk(1, 2, 3, 4)(1, 1) must expandTo(1, 3)
    mk(1, 2, 3, 4, 5)(1, 1) must expandTo(1, 3, 5)
    mk(1, 2, 3, 4, 5, 6)(1, 1) must expandTo(1, 3, 5)
  }

  "takes/skips every two" in {
    mk(1, 2)(2, 2) must expandTo(1, 2)
    mk(1, 2, 3)(2, 2) must expandTo(1, 2)
    mk(1, 2, 3, 4)(2, 2) must expandTo(1, 2)
    mk(1, 2, 3, 4, 5)(2, 2) must expandTo(1, 2, 5)
    mk(1, 2, 3, 4, 5, 6)(2, 2) must expandTo(1, 2, 5, 6)
  }

  "takes and skips" in {
    mk(1, 2, 3, 4, 5, 6, 7)(2, 1) must expandTo(1, 2, 4, 5, 7)
    mk(1, 2, 3, 4, 5, 6, 7)(1, 2) must expandTo(1, 4, 7)
    mk(1, 2, 3, 4, 5, 6, 7)(3, 2) must expandTo(1, 2, 3, 6, 7)
    mk(1, 2, 3, 4, 5, 6, 7)(2, 3) must expandTo(1, 2, 6, 7)
    mk(1, 2, 3, 4, 5, 6, 7)(3, 3) must expandTo(1, 2, 3, 7)
  }

  protected[this] def mkEmpty = mk()(1, 1)

  private[this] def mk(s: T*)(take: Int, skip: Int) =
    new SkippingIterator(s.iterator, take, skip)
}
