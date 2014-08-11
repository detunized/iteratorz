package net.detunized.iteratorz

import org.specs2.mutable.Specification

class PoolingIteratorSpec extends Specification {
  "have no next on an empty iterator" in {
    checkEmpty(1)
  }

  "throw on zero pool size" in {
    checkEmpty(0) should throwA[IllegalArgumentException]
  }

  "throw on negative pool size" in {
    checkEmpty(-1) should throwA[IllegalArgumentException]
  }

  "return the same sequence with different pool sizes" in {
    val s = Seq(1, 2, 3)
    check(s, 1)
    check(s, 2)
    check(s, 3)
    check(s, 4)
    check(s, 5)
  }

  def check[A](s: Seq[A], poolSize: Int) = {
    val i = new PoolingIterator(s.iterator, poolSize)(x => x)

    // Should be the same as source sequence
    s foreach { x =>
      i.hasNext should beTrue
      i.next() shouldEqual x
    }

    i.hasNext should beFalse
    i.next() should throwA[NoSuchElementException]
  }

  def checkEmpty(poolSize: Int) = check(Seq[Int](), poolSize)
}
