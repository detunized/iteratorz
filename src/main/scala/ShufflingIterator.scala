// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import scala.util.Random

class ShufflingIterator[A](source: Iterator[A], windowSize: Int, rng: Random = new Random())
  extends PoolingIterator[A](source, windowSize)(rng.shuffle)
