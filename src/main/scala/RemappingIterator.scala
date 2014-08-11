package net.detunized.iteratorz

import scala.collection.mutable.ArrayBuffer

class RemappingIterator[A](source: Iterator[A], from: String, to: String)
  extends PoolingIterator[A](source, from.size)(detail.remap(_, from, to))
  with TraversingIterator[A] {

  // TODO: Verify that `from` and `to` form a valid mapping.
  // TODO: Make sure `remap` doesn't recalculate `fromToIndex` every time.
}

private object detail {
  def remap[A](buffer: ArrayBuffer[A], from: String, to: String): ArrayBuffer[A] = {
    val fromToIndex = from.zipWithIndex.toMap
    ArrayBuffer.tabulate(to.size)(i => buffer(fromToIndex(to(i))))
  }
}
