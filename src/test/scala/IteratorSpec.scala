package net.detunized.iteratorz

import org.specs2.mutable.Specification

trait IteratorSpec[A] extends Specification {
  protected type T = A
  protected type I = Iterator[A]

  "hasNext must be false on an empty iterator" in {
    mkEmpty.hasNext must beFalse
  }

  "next must throw on an empty iterator" in {
    mkEmpty.next() must throwA[NoSuchElementException]
  }

  protected def check(it: I, expected: T*) = {
    it.toSeq mustEqual expected
  }

  // Must be provided by the subclass
  protected def mkEmpty: I
}
