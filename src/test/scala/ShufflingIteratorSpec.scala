package net.detunized.iteratorz

class ShufflingIteratorSpec extends IteratorSpec[Int] {
  "throw on zero window size" in {
    mk()(0) must throwA[IllegalArgumentException]
  }

  "throw on negative window size" in {
    mk()(-1) must throwA[IllegalArgumentException]
  }

  "should return the same sequence with window of 1" in {
    mk(1, 2, 3)(1) must expandTo(1, 2, 3)
  }

  // TODO: Figure out how to test shuffling in a deterministic way!
  //       Maybe use a fake random generator.

  protected[this] def mkEmpty = mk()(1)

  private[this] def mk(s: T*)(windowSize: Int) =
    new ShufflingIterator(s.iterator, windowSize)
}
