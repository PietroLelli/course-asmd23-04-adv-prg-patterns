package scala.u04.datastructures

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.*
import u04.datastructures.Sequences.*
import Sequence.*

class SequenceTest extends AnyFunSuite:

  test("Sequence correctly sums"):
    Cons(10, Cons(20, Cons(30, Nil()))).sum shouldBe 60
    Nil().sum shouldBe 0

  test("Sequence correctly maps"):
    Cons(10, Cons(20, Nil())).map(_ + 1) shouldBe Cons(11, Cons(21, Nil()))
    Cons(10, Cons(20, Nil())).map(_.toString) shouldBe Cons("10", Cons("20", Nil()))
    Nil[Int]().map(_ + 1) shouldBe Nil()

  test("Sequence correctly filters"):
    Cons(10, Cons(20, Nil())).filter(_ <= 10) shouldBe Cons(10, Nil())
    Cons(10, Cons(20, Nil())).filter(_ <= 9) shouldBe Nil()
    Nil[Int]().filter(_ <= 10) shouldBe Nil()

  test("Sequence correctly creates with of"):
    Sequence.of(3, "a") shouldBe Cons("a", Cons("a", Cons("a", Nil())))
    Sequence.of(0, 10) shouldBe Nil()
