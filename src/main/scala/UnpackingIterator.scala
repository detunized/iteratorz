// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class UnpackingIterator[A](source: Iterator[(A, Int)]) extends Iterator[A] {
  private[this] val filtered = source filter (_._2 > 0)
  private[this] var current: A = _
  private[this] var count = 0

  override def hasNext: Boolean =
    count > 0 || filtered.hasNext

  override def next(): A = {
    if (count > 0) {
      count -= 1
    } else {
      val a = filtered.next()
      current = a._1
      count = a._2 - 1
    }

    current
  }
}
