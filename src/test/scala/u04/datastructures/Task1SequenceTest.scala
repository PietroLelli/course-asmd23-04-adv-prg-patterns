package scala.u04.datastructures

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.*
import u04.datastructures.Sequences.Sequence
import u04.datastructures.Sequences.Sequence.*
import scala.util.Random

class Task1SequenceTest extends AnyFunSuite:
  for(i <- 1 to 100)
    val randomInt = Random.nextInt(100)
    val randomString = Random.nextString(1)
    test("test n." + i) {
      Sequence.of(randomInt, randomString) shouldBe Sequence.of(randomInt, randomString).filter(e => e == randomString)
      Sequence.of(randomInt, randomString).filter(e => e != randomString) shouldBe Nil()
      Cons(randomString, Sequence.of(randomInt, randomString)) shouldBe Sequence.of(randomInt+1, randomString)
      Sequence.of(0, randomString) shouldBe Nil()
    }