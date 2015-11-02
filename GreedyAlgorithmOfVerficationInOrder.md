# Understanding 'greedy' algorithm #
In Mockito verification inOrder mode is 'greedy'. Example:

```
mock.foo();
mock.foo();
mock.bar();
```

```
//greedy algorithm (Mockito way):
inOrder.verify(mock, times(2)).foo(); //pass - I'm greedy - called 2 times, must be times(2)
inOrder.verify(mock, times(1)).bar(); //pass

//non-greedy algorithm allows this:
inOrder.verify(mock, times(1)).foo(); //pass - I'm not greedy, one instance is enough
inOrder.verify(mock, times(1)).bar(); //pass
```

Non-greedy algorithm seems more intuitive: when you read the verification if feels right: **one** instance of foo() was called before **one** instance bar(). However non-greedy algorithm is not consistent with standard verification in Mockito where times(x) is rigid. Also non-greedy may lead to:

  * may lead contradicting assertion statements that pass (e.g. same verifications but with different times(x) pass)
  * may lead inability to verify the exact number of invocations occasionally
  * may lead to bugs

Hence our design choice was to go for 'greedy' algorithm when verifying in order.

# Theoretical example where non-greedy algorithm leads to bugs #

```
//production code:
service.saveEntity();
service.commit();

//test:        
inOrder.verify(service, times(1)).saveEntity(); //this has to happen *once*
inOrder.verify(service, times(1)).commit();
```

Later on the bug is introduced:

```
//production code:
service.saveEntity();
service.saveEntity(); // <<-- dev introduces bug: saving the entity twice leads to runtime exception
service.commit();

//non-greedy algorithm does not detect the bug and the *test passes*
inOrder.verify(service, times(1)).saveEntity(); // <<-- passes in non-greedy mode
inOrder.verify(service).commit();

//greedy algorithm *detects the bug*
inOrder.verify(service, times(1)).saveEntity(); // <<-- fails in greedy mode
inOrder.verify(service).commit();
```

Disclaimer: This example is theoretical. We didn't find a real use case in production code that would asses what is better: greedy or non-greedy algorithm. This seems to be a truly edge case. Decent, KISSy, refactored code should be easy to test without acrobatics.

# More complex examples #

Using atLeast() mode in order.

```
mock.add("A");
mock.add("A");
mock.add("B");
mock.add("A");
```

Explanation of 'greedy' algorithm:

```
  //fails:
  inOrder.verify(mock, atLeast(2)).add("A");
  inOrder.verify(mock, atLeast(1)).add("B");

  //passes:
  inOrder.verify(mock, times(2)).add("A");
  inOrder.verify(mock, atLeast(1)).add("B");

  //atLeast(x) may not fit the greedy paradigm but again...
  //the API should be consistent
```

Another example, with argument matchers:

```
mock.add("10 EURO");
mock.add("10 GBP");
mock.add("20 GBP");
```

```
  //non-greedy - passes:
  inOrder.verify(mock, times(1)).add(startsWith("10"));
  inOrder.verify(mock).add(startsWith("20");

  //non-greedy - also passes:
  inOrder.verify(mock, times(2)).add(startsWith("10"));
  inOrder.verify(mock).add(startsWith("20"));

  //API needs to be 'close' to how Mockito regular verification works.
  //Hence the same verification with different times(x) should not pass
```

```
  //In Mockito only this passes:
  inOrder.verify(mock, times(2)).add(startsWith("10"));
  inOrder.verify(mock).add(startsWith("20"));
```