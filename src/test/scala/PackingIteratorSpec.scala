package net.detunized.iteratorz

class PackingIteratorSpec extends IteratorSpec[(String, Int)] {
  private[this] type S = String

  "pack single elements" in {
    check(mk("A"), "A" -> 1)
    check(mk("A", "B"), "A" -> 1, "B" -> 1)
    check(mk("A", "B", "C"), "A" -> 1, "B" -> 1, "C" -> 1)
  }

  "pack repeating elements" in {
    check(mk("A", "A"), "A" -> 2)
    check(mk("A", "A", "A"), "A" -> 3)
  }

  "pack mixed single and repeated elements" in {
    check(mk("A", "A", "B"), "A" -> 2, "B" -> 1)
    check(mk("A", "B", "B"), "A" -> 1, "B" -> 2)
    check(mk("A", "B", "B", "C", "C", "C"), "A" -> 1, "B" -> 2, "C" -> 3)
    check(mk("A", "A", "A", "B", "B", "C"), "A" -> 3, "B" -> 2, "C" -> 1)
    check(mk("A", "A", "B", "C", "C", "C"), "A" -> 2, "B" -> 1, "C" -> 3)
  }

  protected def mkEmpty = mk()
  private[this] def mk(s: S*) = new PackingIterator(s.iterator)
}
