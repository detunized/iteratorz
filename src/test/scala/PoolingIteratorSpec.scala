package net.detunized.iteratorz

class PoolingIteratorSpec extends IteratorSpec[Int] {
  "throw on zero pool size" in {
    mk(0) should throwA[IllegalArgumentException]
  }

  "throw on negative pool size" in {
    mk(-1) should throwA[IllegalArgumentException]
  }

  "return the same sequence with different pool sizes" in {
    val s = Seq(1, 2, 3)
    check(mk(1, s:_*), s:_*)
    check(mk(2, s:_*), s:_*)
    check(mk(3, s:_*), s:_*)
    check(mk(4, s:_*), s:_*)
    check(mk(5, s:_*), s:_*)
  }

  // TODO: Test that poolFilled callback is called.

  protected[this] def mkEmpty = mk(1)
  private[this] def mk(poolSize: Int, s: T*) = new PoolingIterator(s.iterator, poolSize)(x => x)
}
