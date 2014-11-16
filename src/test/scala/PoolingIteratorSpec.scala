package net.detunized.iteratorz

class PoolingIteratorSpec extends IteratorSpec[Int] {
  "throw on zero pool size" in {
    mk()(0) must throwA[IllegalArgumentException]
  }

  "throw on negative pool size" in {
    mk()(-1) must throwA[IllegalArgumentException]
  }

  "return the same sequence with different pool sizes" in {
    (1 to 5) map (mk(1, 2, 3)(_) must expandTo(1, 2, 3))
  }

  // TODO: Test that poolFilled callback is called.

  protected[this] def mkEmpty = mk()(1)

  private[this] def mk(s: T*)(poolSize: Int) =
    new PoolingIterator(s.iterator, poolSize)(x => x)
}
