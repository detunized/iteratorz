package net.detunized.iteratorz

class UnpackingIteratorSpec extends IteratorSpec[String] {
  type S = (T, Int)

  "unpacks single element" in {
    mk("A" -> 1) must expandTo("A")
    mk("A" -> 2) must expandTo("A", "A")
    mk("A" -> 3) must expandTo("A", "A", "A")
  }

  "unpacks multiple elements" in {
    mk("A" -> 1, "B" -> 2) must expandTo("A", "B", "B")
    mk("A" -> 2, "B" -> 3, "C" -> 4) must expandTo("A", "A", "B", "B", "B", "C", "C", "C", "C")
  }

  "rejects zero counts" in {
    mk("A" -> 0) must beEmpty
    mk("A" -> 0, "B" -> 0) must beEmpty
    mk("A" -> 0, "B" -> 0, "C" -> 0) must beEmpty
  }

  "rejects negative counts" in {
    mk("A" -> -1) must beEmpty
    mk("A" -> -1, "B" -> -2) must beEmpty
    mk("A" -> -1, "B" -> -2, "C" -> -3) must beEmpty
  }

  "unpacks mixed sequence" in {
    mk("A" -> 1, "B" -> 0, "C" -> 2, "D" -> -1) must expandTo("A", "C", "C")
    mk("A" -> 0, "B" -> 2, "C" -> 2, "D" -> -1) must expandTo("B", "B", "C", "C")
    mk("A" -> 2, "B" -> -1, "C" -> 0, "D" -> 1) must expandTo("A", "A", "D")
  }

  protected[this] def mkEmpty = mk()

  private[this] def mk(s: S*) =
    new UnpackingIterator(s.iterator)
}
