package net.detunized.iteratorz

class UnpackingIteratorSpec extends IteratorSpec[String] {
  type S = (T, Int)

  "unpacks single element" in {
    check(mk("A" -> 1), "A")
    check(mk("A" -> 2), "A", "A")
    check(mk("A" -> 3), "A", "A", "A")
  }

  "unpacks multiple elements" in {
    check(mk("A" -> 1, "B" -> 2), "A", "B", "B")
    check(mk("A" -> 2, "B" -> 3, "C" -> 4), "A", "A", "B", "B", "B", "C", "C", "C", "C")
  }

  "rejects zero counts" in {
    check(mk("A" -> 0))
    check(mk("A" -> 0, "B" -> 0))
    check(mk("A" -> 0, "B" -> 0, "C" -> 0))
  }

  "rejects negative counts" in {
    check(mk("A" -> -1))
    check(mk("A" -> -1, "B" -> -2))
    check(mk("A" -> -1, "B" -> -2, "C" -> -3))
  }

  "unpacks mixed sequence" in {
    check(mk("A" -> 1, "B" -> 0, "C" -> 2, "D" -> -1), "A", "C", "C")
    check(mk("A" -> 0, "B" -> 2, "C" -> 2, "D" -> -1), "B", "B", "C", "C")
    check(mk("A" -> 2, "B" -> -1, "C" -> 0, "D" -> 1), "A", "A", "D")
  }

  protected[this] def mkEmpty = mk()
  private[this] def mk(s: S*) = new UnpackingIterator(s.iterator)
}
