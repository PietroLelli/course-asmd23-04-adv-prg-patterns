package scala.u04.task2

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

object Task2SequenceCheck extends Properties("Sequence"):
  import scala.u04.task2.Sequences.*
  sequenceAxioms(BasicSequenceADT)
  sequenceAxioms(ScalaListSequenceADT)

  def sequenceAxioms(sADT: SequenceADT): Unit =
    import sADT.*

    def sequenceGen(): Gen[Sequence[Int]] = for
      b <- Gen.prob(0.7)
      size <- Gen.choose(0, 0) // Use the Gen.choose method to determine the size of the sequence
      elements <- Gen.listOfN(size, Gen.choose(0, 100)) // Generate a list of random integers
    yield if b then nil() else elements.foldRight[Sequence[Int]](nil())((e, acc) => cons(e, acc)) // Convert the list to a sequence

    def intGen(): Gen[Int] = Gen.choose(0, 100)
    def mapperGen(): Gen[Int => Int] = Gen.oneOf[Int => Int](_ + 1, _ * 2, x => x * x)
    def mapperFlatMapGen(): Gen[Int => Sequence[Int]] = Gen.oneOf[Int => Sequence[Int]](x => cons(x, cons(x, nil())), x => cons(x + 1, nil()))
    def predicateGen(): Gen[Int => Boolean] = Gen.oneOf[Int => Boolean](_ % 2 == 0, _ > 10, _ < 10)
    def operatorGen(): Gen[(Int, Int) => Int] = Gen.oneOf[(Int, Int) => Int]((x, y) => x+y, (x, y) => x-y, (x, y) =>x*y)

    property("map axiom") =
      forAll(sequenceGen(), mapperGen()): (s, m) =>
        (s, m) match
          case (nil, _) => map(s, m) == nil
          //case (cons(h, t), m) => map(s, m) == cons(m(h), map(t, m))

    property("concat axiom") =
      forAll(sequenceGen(), sequenceGen()): (s1, s2) =>
        (s1, s2) match
          case (nil, s2) => concat(s1, s2) == s2
          case (s1, nil) => concat(s1, s2) == s1
          //case (s1, s2) => concat(s1, s2) == cons(h, concat(t, s2))

    property("filter axiom") =
      forAll(sequenceGen(), predicateGen()): (s, p) =>
        (s, p) match
          case (nil, _) => filter(s, p) == nil
          //case (cons(h, t), p) => (p(h) && filter(s,p) == cons(h, filter(t, p)) || !p(h) && filter(s, p) == filter(t, p))

    property("flatMap axiom") =
      forAll(sequenceGen(), mapperFlatMapGen()): (s, m) =>
        (s, m) match
          case (nil, _) => flatMap(s, m) == nil
          //case (cons(h, t), m) => flatMap(s, m) == concat(m(h), flatMap(t, m))

    property("foldLeft axiom") =
      forAll(sequenceGen(), intGen(), operatorGen()): (s, z, op) =>
        s match
          case nil => foldLeft(s, z, op) == z
          //case (cons(h, t)) => foldLeft(s, z, op) == foldLeft(t, op(h), op)
          
/*    property("reduce axiom") =
      forAll(sequenceGen(), operatorGen()): (s, op) =>
        s match
          case nil =>
          //case (cons(h, t)) => reduce(s, op) == foldLeft(t, h, op)
*/