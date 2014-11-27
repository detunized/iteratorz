// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class PackingIterator[A](source: Iterator[A]) extends Iterator[(A, Int)] {
  private[this] val bufferedSource = source.buffered

  override def hasNext: Boolean = bufferedSource.hasNext

  override def next(): (A, Int) = {
    val first = bufferedSource.next()
    var count = 1
    while (bufferedSource.hasNext && bufferedSource.head == first) {
      bufferedSource.next()
      count += 1
    }

    (first, count)
  }
}
