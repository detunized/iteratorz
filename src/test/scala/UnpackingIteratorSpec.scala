package net.detunized.iteratorz

class UnpackingIteratorSpec extends IteratorSpec[String, UnpackingIterator[String]] {
  // S -> D
  type S = (String, Int)
  type D = String

  "unpacks single element" in {
    check(Seq("A" -> 1), Seq("A"))
    check(Seq("A" -> 2), Seq("A", "A"))
    check(Seq("A" -> 3), Seq("A", "A", "A"))
  }

  "unpacks multiple elements" in {
    check(Seq("A" -> 1, "B" -> 2), Seq("A", "B", "B"))
    check(Seq("A" -> 2, "B" -> 3, "C" -> 4), Seq("A", "A", "B", "B", "B", "C", "C", "C", "C"))
  }

  "rejects zero counts" in {
    check(Seq("A" -> 0), Seq[String]())
    check(Seq("A" -> 0, "B" -> 0), Seq[String]())
    check(Seq("A" -> 0, "B" -> 0, "C" -> 0), Seq[String]())
  }

  "rejects negative counts" in {
    check(Seq("A" -> -1), Seq[String]())
    check(Seq("A" -> -1, "B" -> -2), Seq[String]())
    check(Seq("A" -> -1, "B" -> -2, "C" -> -3), Seq[String]())
  }

  "unpacks mixed sequence" in {
    check(Seq("A" -> 1, "B" -> 0, "C" -> 2, "D" -> -1), Seq("A", "C", "C"))
    check(Seq("A" -> 0, "B" -> 2, "C" -> 2, "D" -> -1), Seq("B", "B", "C", "C"))
    check(Seq("A" -> 2, "B" -> -1, "C" -> 0, "D" -> 1), Seq("A", "A", "D"))
  }

  private[this] def mk(s: Seq[S]) = new UnpackingIterator(s.iterator)
  private[this] def check(s: Seq[S], expected: Seq[D]) = super.check(mk(s), expected)

  protected def mkEmpty = mk(Seq.empty[S])
}
