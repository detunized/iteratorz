package net.detunized.iteratorz

import scala.collection.mutable.ArrayBuffer

class RemappingIterator[A](source: Iterator[A], from: String, to: String)
  extends PoolingIterator[A](source, from.size)(detail.remap(_, from, to))
  with TraversingIterator[A] {

  // TODO: Verify that `from` and `to` form a valid mapping.
  // TODO: Make sure `remap` doesn't recalculate `fromToIndex` every time.
}

private object detail {
  def remap[A](buffer: ArrayBuffer[A], from: String, to: String): Unit = {
    val fromToIndex = from.zipWithIndex.toMap
    val remapped = ArrayBuffer.tabulate(to.size)(i => buffer(fromToIndex(to(i))))
    buffer.clear()
    remapped.copyToBuffer(buffer)
  }

  def remap[A](buffer: ArrayBuffer[A], m: Map[Int, Int]): Unit = {
    ArrayBuffer.tabulate(buffer.size)(i => buffer(m(i))).copyToBuffer(buffer)
  }
}
