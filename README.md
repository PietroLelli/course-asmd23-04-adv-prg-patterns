# Lab04 - Advanced Programming

## Task 1 - Test-Operate

To begin, I tested the following ScalaCheck parameters in the file: test/scala/u04/datastructures/Task1SequenceCheck.scala. 
- withMinSuccessfulTests(int): Sets the minimum number of successful tests before a property is considered passed.
- WithMaxDiscardRatio(float): Sets the maximum ratio of discarded tests to successful tests before ScalaCheck gives up and discards the property.
- withMinSize(int): Sets the minimum size to be used for the generated values.
- withMaxSize(int): Sets the maximum size to be used for the generated values.
- withWorkers(int): Sets the number of worker threads to be used during testing.

Next, I developed parameterized tests using ScalaTest, equivalent to those previously developed with ScalaCheck. 
These are available in two versions in the files: 
- test/scala/u04/datastructures/Task1SequenceTestV1.scala
- test/scala/u04/datastructures/Task1SequenceTestV2.scala.

In version 2 through the *beforeAll()* function, a random list of pairs (Int, String) is initialized. The number of tests to be run and the length of the generated strings can be set using the variables *numberOfTests* and *stringLength*. 
The test then makes use of the forAll and shouldBe functions provided by ScalaTest.

In version 1, however, I tried to do the same thing without using the forAll function, thus performing random initialization and testing within a for loop.

### Conclusions

ScalaCheck offers built-in generators absent in ScalaTest and allows more flexible test configuration.
The use of *forAll()*, available in both ScalaTest and ScalaCHeck simplifies the execution of assertions on multiple generated values. 
Certainly ScalaCheck is better for configuration and parameter generation in tests.

## Task 2 - ADT-Verifier
For this task, I created an Abstract Data Type for representing a Sequence, with some operations, then created two different implementations and tested them using ScalaCheck.

The ADT implementation of the Sequence is located at the following path: *scala/u04/task2/Sequences.scala*.

First, using a DSL, the Sequence type specification is described, specifically: type, constructors, operations and axioms.

The following operations are defined: filter, concat, map, flatmap, fold and reduce.

The specification is then transformed into a Scala trait, defining all the functionality that a Sequence offers. Next, two implementations are made, one using the Cons/Nil approach, the other using Scale List.

### Cons/Nil implementation

In this implementation, called BascSequenceADT, a private enum defines a Sequence using Cons/Nil, and then, using a opaque alias, the type that uses this implementation is defined, making it invisible from the outside.

Then, all methods are implemented, following the definition of the ADT axioms and using the private Cons/Nil implementation.

### Implementation of Scala List

This implementation is realized by simply defining Scala.List as an opaque type of Sequence, and then implementing all methods by calling the corresponding ones already provided by List.

With this approach, in both implementations, two constructors are available to construct a Sequence, but this makes this data type unusable, for example in a match, because it is missing an unappl. A possible solution to this problem would be to add a getCons getter that works as an unapply and provides an Option[(A, Sequence[A])] that can be used as an extractor.


### Testing

The goal of this part is to check that both implementations work by writing a test for each axiom defined in the ADT specification, using ScalaCheck.

The code can be found at the path: *test/scala/u04/task2/Task2SequenceCheck.scala*.

To demonstrate that both implementations satisfy all tests, a function is created that tests a SequenceADT and then invoked twice with the two different implementations.

Inside the function, following the axioms defined in the ADT specification, all tests make assertions and verify that each Sequence operation works as expected.

Built-in generators are used for the test parameters, in particular to generate random Sequence, mappers and filters.
