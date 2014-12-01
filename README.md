# Iteratorz

[![Build Status](https://travis-ci.org/detunized/iteratorz.svg?branch=master)](https://travis-ci.org/detunized/iteratorz)

**NOTE:** Please be aware that this project has just started and it's a research
and learning project for me. Not much is implemented just yet and nothing here
is production quality.

## Types of iterators (in alphabetical order)

### Alternating iterator

    A B C D E F |
                |=> A a B b C c D d E F
    a b c d     |

Alternates between two sequences. Produces single sequence where elements are
interleaved.


### Buffering iterator

    a b c d e f | => a b c d e f

Buffering iterator produces the same sequence. Elements of the underlying sequence
are cached in a fixed size buffer in batches. Whenever the buffer is exhausted it's
filled up again.


### Grouping by key iterator

    a A a b c C C D d d d |=> [a A a] [b] [c C C] [D d d d]

Groups sequential elements by the same key produced by the mapping function.


### Grouping iterator

    a b c d e f |=> [a] [b c] [d e f]

Groups sequential elements by the predicate.


### Merging iterator

    a c f g h |
              |=> a b c d e f g h
    b d e     |

Merges two sorted sequences into a single sorted one.


### Packing iterator

    a a a b c c d d d |=> (a, 3) (b, 1) (c, 2) (d, 3)

Packs the identical sequential elements into pairs of elements and counts (RLE).


### Pooling iterator

    a b c d e f | => a b c d e f

Similar to the buffering iterator. Every time the internal buffer is filled up
the user supplied function is called which can alter the buffered sequence.


### Prefetching iterator

    a b c d e f | => a b c d e f

Prefetching iterator produces the same sequence. It is very similar to the buffering 
iterator with one notable difference: the prefetching is happening in the background
in a parallel thread of execution. This could be useful when iterating over a
network stream of objects for example. 


### Remapping iterator

    Mapping: "xyz" -> "yx"
    a b c d e f | => b a e d

Remaps sequential subsequences according to the supplied substitution pattern.
It's possible to remove, swap and duplicate elements via the mapping.


### Repeating iterator

    a b c | => a a b b b c

Repeats elements the number of times returned by the user function.


### Shuffling iterator

    a b c d e f g | => d c a b e g f

Shuffles elements inside a given window of fixed size. The returned sequence
looks fairly random with window of big enough size.


### Skipping iterator

    a b c d e f g | => a b f g

Keeps some elements and skips some with a give ration in a repeating pattern.


### Unpacking iterator

    (a, 3) (b, 1) (c, 2) (d, 3) |=> a a a b c c d d d

The opposite of the packing iterator. Unpacks pairs of elements and counts
into a single sequence of repeating elements.


### Zipping iterator

    A B C D E F |
                |=> a A B b c C D d E F
    a b c d     |

Zips two sequences into one every time inserting the elements in the order
returned by the user function.
