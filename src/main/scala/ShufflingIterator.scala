package net.detunized.iteratorz

import scala.util.Random

class ShufflingIterator[A](source: Iterator[A], windowSize: Int, rng: Random = new Random())
  extends PoolingIterator[A](source, windowSize)(rng.shuffle)
