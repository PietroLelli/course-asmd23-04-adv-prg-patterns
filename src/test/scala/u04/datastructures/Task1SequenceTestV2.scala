package scala.u04.datastructures

import org.scalatest.BeforeAndAfterAll
import org.scalatest.Inspectors.forAll
import org.scalatest.funsuite.AnyFunSuite
import u04.datastructures.Sequences.*
import Sequence.*
import org.scalatest.matchers.should.Matchers.shouldBe
import scala.util.Random

class Task1SequenceTestV2 extends AnyFunSuite with BeforeAndAfterAll:
  val rand: Random = Random()
  var randomValues: List[(Int, String)] = List()
  val numberOfTests = 100
  val stringLength = 10

  override def beforeAll(): Unit =
    for (_ <- 0 to numberOfTests) do
      val randomInt = Random.nextInt(100)
      val randomString = Random.nextString(stringLength)
      randomValues = randomValues :+ (randomInt, randomString)

  test("of is a correct factory"):
    forAll(randomValues): (i, s) =>
      of(i, s) shouldBe of(i, s).filter(e => e == s)

    forAll(randomValues): (i, s) =>
      of(i, s).filter(e => e != s) shouldBe Nil()

    forAll(randomValues): (i, s) =>
      Cons(s, of(i, s)) shouldBe of(i + 1, s)

    forAll(randomValues.map((i, s) => s)): s =>
      of(0, s) shouldBe Nil()
