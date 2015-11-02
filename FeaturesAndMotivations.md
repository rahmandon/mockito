Java mocking is dominated by expect-run-verify libraries like EasyMock or jMock. Mockito offers simpler and more intuitive approach: you ask questions about interactions **after** execution. Using mockito, you can [verify what you want](http://monkeyisland.pl/2008/02/24/can-i-test-what-i-want-please). Using expect-run-verify libraries you are often forced to look after irrelevant interactions.

[No expect-run-verify](http://monkeyisland.pl/2008/02/01/deathwish/) also means that Mockito mocks are often **ready** without expensive setup upfront. They aim to be transparent and let the developer to focus on testing selected behavior rather than absorb attention.

Mockito has very slim API, almost no time is needed to start mocking. There is only one kind of mock, there is only one way of creating mocks. Just remember that stubbing goes before execution, verifications of interactions go afterwards. You'll soon notice how natural is that kind of mocking when TDD-ing java code.

Mockito has [similar syntax to EasyMock](MockitoVSEasyMock.md), therefore you can refactor safely. Mockito doesn't understand the notion of 'expectation'. There is only stubbing and verifications.

Mockito implements what [Gerard Meszaros](http://xunitpatterns.com/Mocks,%20Fakes,%20Stubs%20and%20Dummies.html) calls a **Test Spy**.

Some other features:

  * Mocks concrete classes as well as interfaces
  * Little annotation syntax sugar - @Mock
  * Verification errors are clean - click on stack trace to see failed verification in test; click on exception's cause to navigate to actual interaction in code. Stack trace is always clean.
  * Allows flexible verification in order (e.g: verify in order what you want, not every single interaction)
  * Supports exact-number-of-times and at-least-once verification
  * Flexible verification or stubbing using argument matchers (anyObject(), anyString() or refEq() for reflection-based equality matching)
  * Allows creating [custom argument matchers](http://mockito.googlecode.com/svn/branches/1.6/javadoc/org/mockito/Matchers.html) or using existing hamcrest matchers

Distribution:

  * Single-jar distribution includes cglib, objenesis and source code
  * Zip distribution contains all jars, javadoc and source code