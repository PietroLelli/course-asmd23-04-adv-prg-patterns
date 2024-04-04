package scala.u04.task2

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object Task2SequenceCheck extends Properties("Sequence"):
  import scala.u04.task2.Sequences.*
  sequenceAxioms(BasicSequenceADT)
  sequenceAxioms(ScalaListSequenceADT)

  def sequenceAxioms(sADT: SequenceADT): Unit =
    import sADT.*

    def sequenceGen(): Gen[Sequence[Int]] = for
      b <- Gen.prob(0.7)
      size <- Gen.choose(0, 100)
      elements <- Gen.listOfN(size, Gen.choose(0, 100))
    yield if b then nil() else elements.foldRight[Sequence[Int]](nil())((e, acc) => cons(e, acc))

    def intGen(): Gen[Int] = Gen.choose(0, 100)

    def mapperGen(): Gen[Int => Int] = Gen.oneOf[Int => Int](_ + 1, _ * 2, x => x * x)

    def filterGen(): Gen[Int => Boolean] = Gen.oneOf[Int => Boolean](_ % 2 == 0, _ % 2 != 0, _ >= 10)

    def flatMapGen(): Gen[Int => Sequence[String]] = Gen.oneOf[Int => Sequence[String]](_ => cons("a", cons("b", nil())), _ => cons("dummy", nil()), _ => cons("x", cons("z", cons("c", nil()))))

    def operatorGen(): Gen[(Int, Int) => Int] = Gen.oneOf[(Int, Int) => Int]((x, y) => x + y, (x, y) => x - y, (x, y) => x * y)

    property("filter axiom") =
      forAll(sequenceGen(), filterGen()): (s, f) =>
        (getCons(s), f) match
          case (None, _) => filter(nil(), f) == nil()
          case (Some((h, t)), f) => f(h) && filter(s, f) == cons(h, filter(t, f)) || (!f(h) && filter(s, f) == filter(t, f))

    property("map axiom") =
      forAll(sequenceGen(), mapperGen()): (s, m) =>
        (getCons(s), m) match
          case(None, _) => map(s, m) == nil()
          case(Some((h,t)), m) => map(s, m) == cons(m(h), map(t, m))

    property("concat axiom") =
      forAll(sequenceGen(), sequenceGen()): (s1, s2) =>
        (getCons(s1), getCons(s2)) match
          case (None, None) => concat(s1, s2) == nil()
          case (Some((h1, t1)), None) => concat(s1, s2) == cons(h1, t1)
          case (None, Some((h2, t2))) => concat(s1, s2) == cons(h2, t2)
          case (Some((h1, t1)), Some((h2, t2))) => concat(s1,s2) == cons(h1, concat(t1, s2))

    property("flatmap axiom") =
      forAll(sequenceGen(), flatMapGen()): (s, f) =>
        (getCons(s), f) match
          case (None, _) => flatMap(s, f) == nil()
          case (Some((h, t)), f) => flatMap(s, f) == concat(f(h), flatMap(t, f))

    property("foldLeft axiom") =
      forAll(sequenceGen(), intGen(), operatorGen()): (s, z, op) =>
        (getCons(s), z, op) match
          case (None, z, _) => foldLeft(s, z, op) == z
          case (Some((h, t)), z, op) => foldLeft(s, z, op) == foldLeft(t, op(z, h), op)

    property("reduce axiom") =
      forAll(sequenceGen(), operatorGen()): (s, op) =>
        (getCons(s), op) match
          case (Some(h, t), op) => reduce(s, op).contains(foldLeft(t, h, op))
          case (None, _) => reduce(s, op).isEmpty