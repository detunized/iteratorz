// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import scala.math.Ordering

class MergingIterator[A](i1: Iterator[A], i2: Iterator[A])(implicit ord: Ordering[A]) extends Iterator[A] {
  private[this] val i1b = i1.buffered
  private[this] val i2b = i2.buffered

  override def hasNext: Boolean = i1b.hasNext || i2b.hasNext

  override def next(): A = {
    if (i1b.hasNext) {
      if (i2b.hasNext) {
        if (ord.lteq(i1b.head, i2b.head)) {
          i1b.next()
        } else {
          i2b.next()
        }
      } else {
        i1b.next()
      }
    } else if (i2b.hasNext) {
      i2b.next()
    } else {
      Iterator.empty.next()
    }
  }
}
