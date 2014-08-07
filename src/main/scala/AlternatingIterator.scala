package net.detunized.iteratorz

class AlternatingIterator[A](i1: Iterator[A], i2: Iterator[A])
  extends ZippingIterator[A](i1, i2)((_, _) => true)
