// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class ZippingIterator[A](i1: Iterator[A], i2: Iterator[A])
                        (takeFirst: (A, A) => Boolean) extends Iterator[A] {
  private[this] var nextA: A = _
  private[this] var nextDefined = false

  override def hasNext: Boolean = nextDefined || i1.hasNext || i2.hasNext
  override def next(): A = {
    if (nextDefined) {
      nextDefined = false
      nextA
    } else {
      val have1 = i1.hasNext
      val have2 = i2.hasNext
      if (have1 && have2) {
        val next1 = i1.next()
        val next2 = i2.next()
        if (takeFirst(next1, next2)) {
          nextA = next2
          nextDefined = true
          next1
        } else {
          nextA = next1
          nextDefined = true
          next2
        }
      } else if (have1) {
        i1.next()
      } else if (have2) {
        i2.next()
      } else {
        Iterator.empty.next()
      }
    }
  }
}
