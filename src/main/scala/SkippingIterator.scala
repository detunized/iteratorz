// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class SkippingIterator[A](source: Iterator[A], take: Int, skip: Int) extends Iterator[A] {
  require(take > 0)
  require(skip > 0)

  private[this] var left = take

  def hasNext: Boolean = source.hasNext

  def next(): A = {
    val n = source.next()

    assert(left > 0)
    left -= 1

    if (left == 0) {
      var toSkip = skip
      while (toSkip > 0 && source.hasNext) {
        source.next()
        toSkip -= 1
      }
      left = take
    }

    n
  }
}
