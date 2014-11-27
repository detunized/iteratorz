// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class GroupingByKeyIteratorSpec extends IteratorSpec[IndexedSeq[Int]] {
  type S = Int

  "group single elements" in {
    def f(e: S) = e

    mk(1)(f) must expandTo(s(1))
    mk(1, 2)(f) must expandTo(s(1), s(2))
    mk(1, 2, 3)(f) must expandTo(s(1), s(2), s(3))
  }

  "group all elements into a single group" in {
    def f(e: S) = 0

    mk(1)(f) must expandTo(s(1))
    mk(1, 2)(f) must expandTo(s(1, 2))
    mk(1, 2, 3)(f) must expandTo(s(1, 2, 3))
  }

  "group same elements" in {
    def f(e: S) = e

    mk(1)(f) must expandTo(s(1))
    mk(1, 1)(f) must expandTo(s(1, 1))
    mk(1, 1, 2)(f) must expandTo(s(1, 1), s(2))
    mk(1, 1, 2, 2)(f) must expandTo(s(1, 1), s(2, 2))
    mk(1, 1, 2, 2, 3)(f) must expandTo(s(1, 1), s(2, 2), s(3))
    mk(1, 1, 2, 2, 3, 3)(f) must expandTo(s(1, 1), s(2, 2), s(3, 3))
  }

  "group by key" in {
    def f(e: S) = (e - 1) / 2

    mk(1, 2)(f) must expandTo(s(1, 2))
    mk(1, 2, 2, 3)(f) must expandTo(s(1, 2, 2), s(3))
    mk(1, 2, 2, 3, 3, 3, 4)(f) must expandTo(s(1, 2, 2), s(3, 3, 3, 4))
  }

  "return a copy of the internal buffer, not a reference" in {
    val i = mk(1, 2)(e => e)
    val a = i.next()
    val b = i.next()
    a mustEqual s(1)
    b mustEqual s(2)
  }

  protected[this] def mkEmpty = mk()(e => e)

  private[this] def mk[K](s: S*)(f: S => K) =
    new GroupingByKeyIterator(s.iterator)(f)

  private[this] def s(s: S*) = s.toIndexedSeq
}
