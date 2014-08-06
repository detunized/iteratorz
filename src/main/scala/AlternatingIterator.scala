package net.detunized.iteratorz

class AlternatingIterator[A](i1: Iterator[A], i2: Iterator[A]) extends CombiningIterator[A] {
  private[this] var first = true

  override def hasNext: Boolean = i1.hasNext || i2.hasNext
  override def next(): A = {
    if (first && i1.hasNext) {
      first = false
      i1.next()
    } else if (!first && i2.hasNext) {
      first = true
      i2.next()
    } else if (i1.hasNext) {
      i1.next()
    } else if (i2.hasNext) {
      i2.next()
    } else {
      Iterator.empty.next()
    }
  }
}
