// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

// TODO: Share the code with BufferingIterator.
// TODO: Find a way to test prefetching mechanism.

class PrefetchingIteratorSpec extends IteratorSpec[Int] {
  "fails with zero prefetch size" in {
    mk()(0) must throwA[IllegalArgumentException]
    mk(1)(0) must throwA[IllegalArgumentException]
    mk(1, 2)(0) must throwA[IllegalArgumentException]
  }

  "fails with negative prefetch size" in {
    mk()(-1) must throwA[IllegalArgumentException]
    mk(1)(-1) must throwA[IllegalArgumentException]
    mk(1, 2)(-1) must throwA[IllegalArgumentException]
  }

  "returns same sequence with prefetch size of 1" in {
    mk()(1) must expandTo()
    mk(1)(1) must expandTo(1)
    mk(1, 2)(1) must expandTo(1, 2)
    mk(1, 2, 3)(1) must expandTo(1, 2, 3)
  }

  "returns same sequence with prefetch size of 10" in {
    mk()(10) must expandTo()
    mk(1)(10) must expandTo(1)
    mk(1, 2)(10) must expandTo(1, 2)
    mk(1, 2, 3)(10) must expandTo(1, 2, 3)
  }

  protected[this] def mkEmpty = mk()(1)

  private[this] def mk(s: T*)(prefetchSize: Int) =
    new PrefetchingIterator(s.iterator, prefetchSize)
}
