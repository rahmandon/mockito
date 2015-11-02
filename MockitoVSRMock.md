I was asked to provide some comparison with [RMock](http://rmock.sourceforge.net/documentation/xdoc.html) library. I was never an RMock user so the comparison may not be perfect. Don't hesitate to give feedback.

Also, bear in mind that this is just a syntax-wise comparison. Mockito is actually a spying framework disguised into a mocking framework. This means that there is a big difference in the style of testing. You can read more about it on Mockito pages or on my [blog](http://monkeyisland.pl/category/mockito).

### Verifying the method was called ###

```
//RMock:
Runnable runnable = (Runnable)mock(Runnable.class);
runnable.run();
startVerification();
//run
```

```
//Mockito:
Runnable runnable = mock(Runnable.class);
//run
verify(runnable).run();
```

  * RMock seems to be very similar to EasyMock, it also uses Record/Playback pattern
  * RMock has additional testing features that can be used for state testing. E.g: assertThat syntax similar to [hamcrest](http://code.google.com/p/hamcrest). Mockito uses hamcrest.

### Setting return value ###

```
//RMock:
mock.getStuff();
modify().returnValue(stuff);
startVerification();
//run
```

```
//Mockito:
when(mock.getStuff()).thenReturn(stuff);
//run
```

### Called at least once ###

```
//RMock
runnable.run()
modify().multiplicity(expect.atLeastOnce());
startVerification();
//run
```

```
//Mockito:
//run
verify(runnable, atLeastOnce()).run();
```

### Flexible argument matching ###

```
//RMock:
mock.twoArguments("anything", someObj);
modify().args(is.ANYTHING, is.AS_RECORDED);
startVerification();
//run
```

```
//Mockito:
//run
verify(mock).twoArguments(anyString(), eq(someObj));
```

### Void method to throw ###

```
//RMock:
runnable.run();
modify().throwException(exc);
startVerification();
//run
```

```
//Mockito:
doThrow(exc).when(runnable).run();
//run
```