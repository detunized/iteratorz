// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import scala.collection.mutable.ArrayBuffer

class GroupingByKeyIterator[A, B](source: Iterator[A])
                                 (keyFunction: A => B) extends Iterator[IndexedSeq[A]] {
  private[this] val bufferedSource = source.buffered
  private[this] val group = ArrayBuffer[A]()

  override def hasNext: Boolean = bufferedSource.hasNext

  override def next(): IndexedSeq[A] = {
    group.clear()
    val first = bufferedSource.next()
    val firstKey = keyFunction(first)
    group += first
    while (bufferedSource.hasNext && keyFunction(bufferedSource.head) == firstKey)
      group += bufferedSource.next()

    group.toIndexedSeq
  }
}
