// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import scala.collection.mutable.ArrayBuffer

class GroupingIterator[A](source: Iterator[A])
                         (shouldAdd: (A, IndexedSeq[A]) => Boolean) extends Iterator[IndexedSeq[A]] {
  private[this] val bufferedSource = source.buffered
  private[this] val group = ArrayBuffer[A]()

  override def hasNext: Boolean = bufferedSource.hasNext

  override def next(): IndexedSeq[A] = {
    group.clear()
    group += bufferedSource.next()
    while (bufferedSource.hasNext && shouldAdd(bufferedSource.head, group))
      group += bufferedSource.next()

    group.toIndexedSeq
  }
}
