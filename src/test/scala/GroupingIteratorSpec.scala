package net.detunized.iteratorz

class GroupingIteratorSpec extends IteratorSpec[IndexedSeq[Int]] {
  type S = Int

  "group single elements" in {
    def f(e: S, group: T) = false

    mk(1)(f) must expandTo(s(1))
    mk(1, 2)(f) must expandTo(s(1), s(2))
    mk(1, 2, 3)(f) must expandTo(s(1), s(2), s(3))
  }

  "group all elements into a single group" in {
    def f(e: S, group: T) = true

    mk(1)(f) must expandTo(s(1))
    mk(1, 2)(f) must expandTo(s(1, 2))
    mk(1, 2, 3)(f) must expandTo(s(1, 2, 3))
  }

  "group same elements" in {
    def f(e: S, group: T) = e == group(0)

    mk(1)(f) must expandTo(s(1))
    mk(1, 1)(f) must expandTo(s(1, 1))
    mk(1, 1, 2)(f) must expandTo(s(1, 1), s(2))
    mk(1, 1, 2, 2)(f) must expandTo(s(1, 1), s(2, 2))
    mk(1, 1, 2, 2, 3)(f) must expandTo(s(1, 1), s(2, 2), s(3))
    mk(1, 1, 2, 2, 3, 3)(f) must expandTo(s(1, 1), s(2, 2), s(3, 3))
  }

  "group by size" in {
    def f(e: S, group: T) = group.size < group(0)

    mk(1, 2)(f) must expandTo(s(1), s(2))
    mk(1, 2, 2, 3)(f) must expandTo(s(1), s(2, 2), s(3))
    mk(1, 2, 2, 3, 3, 3, 4)(f) must expandTo(s(1), s(2, 2), s(3, 3, 3), s(4))
  }

  "current group is never empty" in {
    def f(e: S, group: T) = {
      group.size must beGreaterThanOrEqualTo(1)
      true
    }

    mk()(f).toSeq
    mk(1)(f).toSeq
    mk(1, 2)(f).toSeq
    mk(1, 2, 3)(f).toSeq

    ok
  }

  "return a copy of the internal buffer, not a reference" in {
    val i = mk(1, 2)((_, _) => false)
    val a = i.next()
    val b = i.next()
    a mustEqual s(1)
    b mustEqual s(2)
  }

  protected[this] def mkEmpty = mk()((_, _) => false)

  private[this] def mk(s: S*)(f: (S, T) => Boolean) =
    new GroupingIterator(s.iterator)(f)

  private[this] def s(s: S*) = s.toIndexedSeq
}
