// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import scala.collection.mutable.ArrayBuffer

class BufferingIterator[A](source: Iterator[A], bufferSize: Int = 1024) extends Iterator[A] {
  require(bufferSize > 0)

  private[this] val buffer = new ArrayBuffer[A](bufferSize) // TODO: Boxing!
  private[this] var bufferHead = 0

  def hasNext: Boolean = bufferHead < buffer.size || source.hasNext

  def next(): A = {
    if (bufferHead < buffer.size) {
      val n = buffer(bufferHead)
      bufferHead += 1
      n
    } else {
      clear()
      while (buffer.size < bufferSize && source.hasNext)
        buffer += source.next()

      if (buffer.isEmpty) {
        Iterator.empty.next()
      } else {
        bufferHead = 1
        buffer(0)
      }
    }
  }

  private[this] def clear(): Unit = {
    buffer.clear()
    bufferHead = 0
  }
}
