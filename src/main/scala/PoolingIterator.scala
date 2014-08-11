package net.detunized.iteratorz

import scala.collection.mutable.ArrayBuffer

class PoolingIterator[A](source: Iterator[A], poolSize: Int)
                        (poolFilled: ArrayBuffer[A] => ArrayBuffer[A]) extends Iterator[A] {
  require(poolSize > 0)
  protected[this] var pool = new ArrayBuffer[A](poolSize)

  override def hasNext: Boolean = pool.nonEmpty || source.hasNext

  override def next(): A = {
    if (pool.isEmpty) {
      do {
        pool += source.next()
      } while (pool.size < poolSize && source.hasNext)

      pool = poolFilled(pool)
    }

    // TODO: This is not efficient with ArrayBuffer!
    pool.remove(0)
  }
}
