// Copyright (C) 2014 Dmitry Yakimenko (detunized@gmail.com).
// Licensed under the terms of the MIT license. See LICENCE for details.

package net.detunized.iteratorz

import org.specs2.mutable.Specification

trait IteratorSpec[A] extends Specification {
  protected[this] type T = A
  protected[this] type I = Iterator[A]

  "hasNext must be false on an empty iterator" in {
    mkEmpty.hasNext must beFalse
  }

  "next must throw on an empty iterator" in {
    mkEmpty.next() must throwA[NoSuchElementException]
  }

  protected[this] def beEmpty =
    (it: Iterator[A]) => it.toSeq must be empty

  protected[this] def expandTo(expected: A*) =
    (it: Iterator[A]) => it.toSeq mustEqual expected

  // Must be provided by the subclass
  protected[this] def mkEmpty: I
}
