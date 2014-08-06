# Iteratorz

[![Build Status](https://travis-ci.org/detunized/iteratorz.svg?branch=master)](https://travis-ci.org/detunized/iteratorz)

**NOTE:** Please be aware that this project has just started and it's a research
and learning project for me. Not much is implemented just yet and nothing here
is production quality.

## Types of iterators

### Traversing iterator (returns the same number of elements):

    a b c d e f |=> c a b e f d

  - remapping iterator: a b c -> c a b
  - shuffling iterator
  - multi-dimensional traversal:
    - matrix row major
    - matrix column major
    - matrix spiral
    - tree depth first
    - tree in-order
    - tree breadth first


### Combining iterator (combines two iterators):

    A B C D E F |
                |=> A a B b C c D d E F
    a b c d     |

  - merging iterator (sorted iterators)
  - merging with predicate
  - alternating
  - alternating with ratio (a a a) + (b b b) -> (a a b a a b a a b)
  - zipping
  - zipping with predicate


### Splitting iterator (returns two iterators):

*NOTE: Not trivial to implement on infinite iterators. Maybe not even possible.*

                |=> a c d
    a b c d e f |
                |=> b e f


### Grouping iterator (returns sequences):

    a b c d e f |=> [a c] [b d e] [f]

  - grouping with predicate
  - grouping by key
  - matrix rows
  - matrix columns


### Flattening iterator (takes sequences):

    [a c] [b d e] [f] |=> a b c d e f

  - flatten with depth


### Regrouping iterator (takes and returns sequences):

    [a b c] [d e] [f] |=> [a e] [b c d f]

  - transposing iterator


### Collapsing iterator:

    a b c d e f |=> g h i

  - removing duplicates iterator


### Expanding iterator:

    a b c |=> d e f g h i

  - repeating iterator
