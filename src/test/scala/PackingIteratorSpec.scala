// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class PackingIteratorSpec extends IteratorSpec[(String, Int)] {
  private[this] type S = String

  "pack single elements" in {
    mk("A") must expandTo("A" -> 1)
    mk("A", "B") must expandTo("A" -> 1, "B" -> 1)
    mk("A", "B", "C") must expandTo("A" -> 1, "B" -> 1, "C" -> 1)
  }

  "pack repeating elements" in {
    mk("A", "A") must expandTo("A" -> 2)
    mk("A", "A", "A") must expandTo("A" -> 3)
  }

  "pack mixed single and repeated elements" in {
    mk("A", "A", "B") must expandTo("A" -> 2, "B" -> 1)
    mk("A", "B", "B") must expandTo("A" -> 1, "B" -> 2)
    mk("A", "B", "B", "C", "C", "C") must expandTo("A" -> 1, "B" -> 2, "C" -> 3)
    mk("A", "A", "A", "B", "B", "C") must expandTo("A" -> 3, "B" -> 2, "C" -> 1)
    mk("A", "A", "B", "C", "C", "C") must expandTo("A" -> 2, "B" -> 1, "C" -> 3)
  }

  protected[this] def mkEmpty = mk()

  private[this] def mk(s: S*) =
    new PackingIterator(s.iterator)
}
