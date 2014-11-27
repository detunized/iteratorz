// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class RepeatingIteratorSpec extends IteratorSpec[Int] {
  "is empty on an empty iterator" in {
    mk()(_ => 1) must beEmpty
  }

  "is empty with zero repeat count" in {
    mk(1, 2, 3)(_ => 0) must beEmpty
  }

  "hasNext throws on negative count" in {
    mk(1, 2, 3)(_ => -1).hasNext must throwA[IllegalArgumentException]
  }

  "next throws on negative count" in {
    mk(1, 2, 3)(_ => -1).next() must throwA[IllegalArgumentException]
  }

  "returns the same sequence with repeat count of 1" in {
    mk(1, 2, 3)(_ => 1) must expandTo(1, 2, 3)
  }

  "repeats elements twice" in {
    mk(1, 2, 3)(_ => 2) must expandTo(1, 1, 2, 2, 3, 3)
  }

  "repeats elements variying number of times" in {
    mk(1, 2, 3, 4)(x => x) must expandTo(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
  }

  "skips elements in the beginning" in {
    mk(1, 2, 3, 4, 5)(x => if (x == 1 || x == 2) 0 else 1) must expandTo(3, 4, 5)
  }

  "skips elements in the middle" in {
    mk(1, 2, 3, 4, 5)(x => if (x == 2 || x == 4) 0 else 1) must expandTo(1, 3, 5)
  }

  "skips elements in the end" in {
    mk(1, 2, 3, 4, 5)(x => if (x == 4 || x == 5) 0 else 1) must expandTo(1, 2, 3)
  }

  "skips and repeats elements" in {
    mk(1, 2, 3, 4, 5)(Map(1 -> 0, 2 -> 3, 3 -> 1, 4 -> 0, 5 -> 2)) must expandTo(2, 2, 2, 3, 5, 5)
  }

  protected[this] def mkEmpty = mk()(_ => 1)

  private[this] def mk(s: T*)(f: T => Int) =
    new RepeatingIterator(s.iterator)(f)
}
