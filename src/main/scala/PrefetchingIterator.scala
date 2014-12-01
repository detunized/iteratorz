// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import java.util.concurrent.ArrayBlockingQueue

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PrefetchingIterator[A](source: Iterator[A], prefetchSize: Int = 1024) extends Iterator[A] {
  require(prefetchSize > 0)

  private[this] val buffer = new ArrayBlockingQueue[A](prefetchSize)

  @volatile
  private[this] var sourceExhausted = source.isEmpty // TODO: This could be slow!

  private[this] val prefetch = Future[Unit] {
    while (source.hasNext)
      buffer.put(source.next())

    sourceExhausted = true
  }

  def hasNext: Boolean = !(sourceExhausted && buffer.isEmpty)

  def next(): A = {
    if (hasNext)
      buffer.take()
    else
      Iterator.empty.next()
  }
}
