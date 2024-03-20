package scala.u04.task2

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

object BasicSequenceCheck extends Properties("Sequence"):

  import scala.u04.task2.Sequences.BasicSequenceADT.*

  //type BasicSequence[A] = BasicSequenceADT.Sequence[A]
  //type ListSequence[A] = ScalaListSequenceADT.Sequence[A]

  def sequenceGen(): Gen[Sequence[Int]] = for
    b <- Gen.prob(0.7)
    size <- Gen.choose(0,0) // Use the Gen.choose method to determine the size of the sequence
    elements <- Gen.listOfN(size, Gen.choose(0, 100)) // Generate a list of random integers
  yield if b then nil() else elements.foldRight[Sequence[Int]](nil())((e, acc) => cons(e, acc)) // Convert the list to a sequence

  //Pick a random mapper between the three
  val mapperArbitrary: Arbitrary[Int => Int] = Arbitrary(Gen.oneOf[Int => Int](_ + 1, _ * 2, x => x * x))
  val mapperFlatMapArbitrary: Arbitrary[Int => Sequence[Int]] = Arbitrary(Gen.oneOf[Int => Sequence[Int]](x => cons(x, cons(x, nil())), x => cons(x+1, nil())))
  val predicateArbitrary: Arbitrary[Int => Boolean] = Arbitrary(Gen.oneOf[Int => Boolean](_ % 2 == 0, _ > 10, _ < 10))

  property("map axiom") =
    forAll(sequenceGen(), mapperArbitrary.arbitrary): (s, m) =>
      (s, m) match
        case (nil, _) => map(s, m) == nil
        //case (cons(h, t), m) => map(s, m) == cons(m(h), map(t, m))

  property("filter axiom") =
    forAll(sequenceGen(), predicateArbitrary.arbitrary): (s, p) =>
      (s, p) match
        case (nil, _) => filter(s, p) == nil
        //case (cons(h, t), p) => if p(h) then cons(h, filter(t, p)) else filter(t, p)

  property("flatMap axiom") =
    forAll(sequenceGen(), mapperFlatMapArbitrary.arbitrary): (s, m) =>
      (s, m) match
        case (nil, _) => flatMap(s, m) == nil
        //case (cons(h, t), m) => concat(map(s, m), flatMap(t, m))