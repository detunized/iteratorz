// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

class RepeatingIterator[A](self: Iterator[A])(f: A => Int) extends Iterator[A] {
  private[this] var left = 0
  private[this] var current: A = _

  advance()

  override def hasNext: Boolean = left > 0

  override def next(): A = {
    if (left == 0)
      Iterator.empty.next()

    val result = current

    left -= 1
    if (left == 0)
      advance()

    result
  }

  private[this] def advance(): Unit = {
    assert(left == 0)

    while (left == 0 && self.hasNext) {
      current = self.next()
      left = f(current)
      require(left >= 0)
    }
  }
}
