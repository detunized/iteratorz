package net.detunized.iteratorz

class RemappingIteratorSpec extends IteratorSpec[Int] {
  "throw on blank from parameter" in {
    mk()("", "?") must throwA[IllegalArgumentException]
  }

  // TODO: Test more failures!

  "return the same sequence with identity mapping" in {
    mk(1, 2, 3)("A" -> "A") must expandTo(1, 2, 3)
    mk(1, 2, 3, 4)("AB" -> "AB") must expandTo(1, 2, 3, 4)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "ABC") must expandTo(1, 2, 3, 4, 5, 6)
  }

  "swap elements" in {
    mk(1, 2, 3, 4)("AB" -> "BA") must expandTo(2, 1, 4, 3)
    mk(1, 2, 3)("ABC" -> "BCA") must expandTo(2, 3, 1)
    mk(1, 2, 3, 4)("ABCD" -> "DCBA") must expandTo(4, 3, 2, 1)
  }

  "remove elements" in {
    mk(1, 2, 3, 4)("AB" -> "A") must expandTo(1, 3)
    mk(1, 2, 3, 4)("AB" -> "B") must expandTo(2, 4)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "A") must expandTo(1, 4)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "B") must expandTo(2, 5)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "C") must expandTo(3, 6)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "BC") must expandTo(2, 3, 5, 6)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "AC") must expandTo(1, 3, 4, 6)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "BC") must expandTo(2, 3, 5, 6)
  }

  "duplicate elements" in {
    mk(1, 2, 3)("A" -> "AA") must expandTo(1, 1, 2, 2, 3, 3)
    mk(1, 2, 3)("A" -> "AAA") must expandTo(1, 1, 1, 2, 2, 2, 3, 3, 3)
    mk(1, 2, 3, 4)("AB" -> "AABB") must expandTo(1, 1, 2, 2, 3, 3, 4, 4)
    mk(1, 2, 3, 4)("AB" -> "AAABBB") must expandTo(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "AABC") must expandTo(1, 1, 2, 3, 4, 4, 5, 6)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "ABBC") must expandTo(1, 2, 2, 3, 4, 5, 5, 6)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "ABCC") must expandTo(1, 2, 3, 3, 4, 5, 6, 6)
  }

  "swap, remove and duplicate" in {
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "CBB") must expandTo(3, 2, 2, 6, 5, 5)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "BAA") must expandTo(2, 1, 1, 5, 4, 4)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "CCA") must expandTo(3, 3, 1, 6, 6, 4)
    mk(1, 2, 3, 4, 5, 6)("ABC" -> "CCCA") must expandTo(3, 3, 3, 1, 6, 6, 6, 4)
  }

  protected[this] def mkEmpty = mk()("?", "?")

  private[this] def mk(s: T*)(mapping: (String, String)) =
    new RemappingIterator(s.iterator, mapping._1, mapping._2)
}
