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
